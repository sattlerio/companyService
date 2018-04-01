package com.sattlerio.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

public class CompanySettings {

    @NotEmpty
    private String name;

    private String vat;

    @NotEmpty
    private String address;

    @NotEmpty
    private String zip;

    @NotEmpty
    private String city;

    @NotEmpty
    private String country_id;

    @NotEmpty
    private String language_id;

    @JsonCreator
    public CompanySettings(@JsonProperty("name") String name,
                           @JsonProperty("address") String address,
                           @JsonProperty("zip") String zip,
                           @JsonProperty("city") String city,
                           @JsonProperty("country_id") String country_id,
                           @JsonProperty("language_id") String language_id,
                           @JsonProperty("vat") String vat) {
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.country_id = country_id;
        this.city = city;
        this.language_id = language_id;
        this.vat = vat;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public String getName() {
        return name;
    }

    public String getVat() {
        return vat;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }
}

