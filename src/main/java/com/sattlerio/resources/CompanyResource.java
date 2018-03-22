package com.sattlerio.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.sattlerio.db.Company;
import com.sattlerio.db.CompanyDAO;
import io.dropwizard.jersey.params.NonEmptyStringParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/company/{company_id}")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyResource {
    private final static Logger log = LoggerFactory.getLogger(CompanyResource.class);
    private final CompanyDAO companyDAO;

    public CompanyResource(CompanyDAO companyDAO){
        this.companyDAO = companyDAO;
    }

    @GET
    @Timed
    @ExceptionMetered
    @Path("/details")
    public Company fetchCompanyById(@PathParam("company_id")NonEmptyStringParam company_id) {
        String requestId = UUID.randomUUID().toString();

        if(!company_id.get().isPresent()) {
            logInfoWithTransactionId("abort transaction because of missing company id", requestId);
            throw new WebApplicationException(400);
        }

        String companyId = company_id.get().get();

        logInfoWithTransactionId("new request to fetch company data", requestId);

        Company company = fetchCompanyData(companyId, requestId);

        return company;

    }

    private void logInfoWithTransactionId(String message, String transactionId) {
        log.info(String.format("%s: %s", transactionId, message));
    }

    private Company fetchCompanyData(String company_id, String requestId) {
        String user_id = "6e558fc3a1b84144ad58557beac121d8";

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

