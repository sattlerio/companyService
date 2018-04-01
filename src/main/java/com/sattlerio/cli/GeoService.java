package com.sattlerio.cli;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoService {

    private final static Logger log = LoggerFactory.getLogger(GeoService.class);

    private final String server;
    private final static String countryPath = "/resources/geo/country/";
    private final static String languagePath = "/resources/geo/language/";

    public GeoService(String server) {
        this.server = server;
    }

    public boolean validateCountryById(String currency_id) {
        String host = this.server + this.countryPath + currency_id;

        return this.callGeoService(host);
    }

    public boolean validateLanguageById(String language_id) {
        String host = this.server + this.languagePath + language_id;

        return callGeoService(host);
    }

    private boolean callGeoService(String host) {
        try {
            HttpResponse<JsonNode> respone = Unirest.get(host).asJson();
            Integer status = respone.getStatus();

            String geoStatus = respone.getBody().getObject().getString("status");

            if (status == 200 && geoStatus.equals("OK")) {
                log.info("country id exists return true");
                return true;
            }
            return false;

        } catch (Exception e) {
            log.info("not possible to fetch from geo service");
            log.info(e.getMessage());
            log.info(e.toString());
            return false;
        }
    }
}
