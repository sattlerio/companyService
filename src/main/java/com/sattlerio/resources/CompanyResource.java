package com.sattlerio.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.sattlerio.api.CompanySettings;
import com.sattlerio.cli.GeoService;
import com.sattlerio.db.Company;
import com.sattlerio.db.CompanyDAO;
import com.sattlerio.db.User;
import com.sattlerio.db.UserDAO;
import io.dropwizard.jersey.params.NonEmptyStringParam;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyResource {
    private final static Logger log = LoggerFactory.getLogger(CompanyResource.class);
    private final CompanyDAO companyDAO;
    private final UserDAO userDAO;

    public CompanyResource(CompanyDAO companyDAO, UserDAO userDAO){
        this.companyDAO = companyDAO;
        this.userDAO = userDAO;
    }

    @PUT
    @Timed
    @ExceptionMetered
    @Path("/details/{company_id}")
    public Response updateCompanyById(@Context HttpHeaders httpHeaders,
                                      @PathParam("company_id")NonEmptyStringParam company_id,
                                      @Valid CompanySettings companySettings) {
        String requestId = httpHeaders.getHeaderString("x-transactionid");

        logInfoWithTransactionId("got new transaction to update company", requestId);

        String userId = httpHeaders.getHeaderString("x-user-uuid");

        if(userId == null) {
            logInfoWithTransactionId("abort transaction because of missing X-User header", requestId);
            throw new WebApplicationException(401);
        }

        if(!company_id.get().isPresent()) {
            logInfoWithTransactionId("abort transaction because of missing company id", requestId);
            throw new WebApplicationException(400);
        }

        Boolean geoDataCeck = validateGeoData(companySettings.getCountry_id(), companySettings.getLanguage_id());

        if(geoDataCeck != true) {
            logInfoWithTransactionId("abort transaction because country id or company id does not exist", requestId);
            throw new WebApplicationException(404);
        }

        Integer companyDBId = companyDAO.getCompanyIdFromCompanyUuid(company_id.get().get());
        logInfoWithTransactionId(String.valueOf(companyDBId), requestId);

        if (companyDBId == null) {
            logInfoWithTransactionId("abort transaction because company does not exist in DB", requestId);
            throw new WebApplicationException(404);
        }

        User user = userDAO.getUserPermissionById(companyDBId, userId);

        if (user == null) {
            logInfoWithTransactionId("abort transaction, user has no permission in company", requestId);
            throw new WebApplicationException(401);
        }

        if (!user.checkPermission(1)) {
            logInfoWithTransactionId("abort transaction because user has not the required permission", requestId);
            throw new WebApplicationException(401);
        }

        logInfoWithTransactionId("all checks were positive going to update company now", requestId);

        try {
            companyDAO.updateCompanySettings(companySettings.getName(),
                    companySettings.getAddress(),
                    companySettings.getZip(),
                    companySettings.getCity(),
                    companySettings.getVat(),
                    companySettings.getCountry_id(),
                    companySettings.getLanguage_id(),
                    company_id.get().get());

            JSONObject response = new JSONObject();
            response.put("status", "OK");
            response.put("message", "updated company");
            response.put("request_id", requestId);
            response.put("status_code", 200);

            return Response.status(200).entity(response.toString()).build();

        } catch (Exception e) {
            logInfoWithTransactionId("not possible to write into db ", requestId);
            log.info(e.toString());
            log.info(e.getMessage());

            JSONObject response = new JSONObject();
            response.put("status", "ERROR");
            response.put("message", "sorry not possible to write into the database");
            response.put("request_id", requestId);
            response.put("status_code", 500);

            return Response.status(500).entity(response.toString()).build();
        }
    }

    private boolean validateGeoData(String countryId, String languageId) {
        GeoService client = new GeoService("http://0.0.0.0:8080");

        Boolean countryCheck = client.validateCountryById(countryId);
        Boolean languageCheck = client.validateLanguageById(languageId);

        if (languageCheck == true && countryCheck == true) {
            return true;
        }
        return false;
    }

    @GET
    @Timed
    @ExceptionMetered
    @Path("/details/{company_id}")
    public Company fetchCompanyById(@Context HttpHeaders httpHeaders,
                                    @PathParam("company_id")NonEmptyStringParam company_id) {
        String requestId = UUID.randomUUID().toString();

        String userId = httpHeaders.getHeaderString("x-user-uuid");

        if(userId == null) {
            logInfoWithTransactionId("abort transaction because of missing X-USER header", requestId);
            throw new WebApplicationException(400);
        }

        if(!company_id.get().isPresent()) {
            logInfoWithTransactionId("abort transaction because of missing company id", requestId);
            throw new WebApplicationException(400);
        }

        String companyId = company_id.get().get();

        logInfoWithTransactionId("new request to fetch company data", requestId);

        Company company = fetchCompanyData(companyId, requestId, userId);

        return company;

    }

    private void logInfoWithTransactionId(String message, String transactionId) {
        log.info(String.format("%s: %s", transactionId, message));
    }

    private Company fetchCompanyData(String company_id, String requestId, String user_id) {

        if(company_id == null) {
            logInfoWithTransactionId("abort transaction because of missing company parameter", requestId);
            throw new WebApplicationException(400);
        }

        Company company = companyDAO.getComanyInfoByCompanyId(user_id, company_id);

        if(company == null) {
            logInfoWithTransactionId("abort transaction because company does not exist", requestId);
            throw new WebApplicationException(404);
        }

        return company;
    }
}

