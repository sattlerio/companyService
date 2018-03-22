package com.sattlerio.db;

public class Company {
    private String company_uuid;
    private String name;
    private String country_id;
    private String address;
    private String zip;
    private String city;
    private String vat;
    private String logo;
    private String phone_number;
    private String email_address;
    private String accounting_email;
    private String technical_email;

    public Company(String company_uuid, String name, String country_id,
                   String address, String zip, String city, String vat,
                   String logo, String phone_number, String email_address,
                   String accounting_email, String technical_email) {
        this.company_uuid = company_uuid;
        this.name = name;
        this.country_id = country_id;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.vat = vat;
        this.logo = logo;
        this.phone_number = phone_number;
        this.email_address = email_address;
        this.accounting_email = accounting_email;
        this.technical_email = technical_email;
    }

    public Company() {}

    public String getCompany_uuid() {
        return company_uuid;
    }

    public String getName() {
        return name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getVat() {
        return vat;
    }

    public String getLogo() {
        return logo;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getAccounting_email() {
        return accounting_email;
    }

    public String getTechnical_email() {
        return technical_email;
    }
}
