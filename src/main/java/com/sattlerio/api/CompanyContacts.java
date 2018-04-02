package com.sattlerio.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

public class CompanyContacts {

    @NotEmpty
    private String phone_number;

    @NotEmpty
    @Email
    private String email_address;

    @Email
    private Optional<String> accounting_email;

    @Email
    private Optional<String> technical_email;

    @JsonCreator
    public CompanyContacts(@JsonProperty("phone_number") String phone_number,
                           @JsonProperty("email_address") String email_address) {
        this.phone_number = phone_number;
        this.email_address = email_address;
    }

    public String getTechnical_email() {
        if (this.technical_email == null) {
            return this.email_address;
        }
        return technical_email.get();
    }

    public String getAccounting_email() {
        if (this.accounting_email == null) {
            return this.email_address;
        }
        return accounting_email.get();
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
