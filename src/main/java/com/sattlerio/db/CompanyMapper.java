package com.sattlerio.db;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyMapper implements ResultSetMapper<Company>{

    public Company map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Company(
                r.getString("company_uuid"),
                r.getString("name"),
                r.getString("country_id"),
                r.getString("address"),
                r.getString("zip"),
                r.getString("city"),
                r.getString("vat"),
                r.getString("logo"),
                r.getString("phone_number"),
                r.getString("email_address"),
                r.getString("accounting_email"),
                r.getString("technical_email"),
                r.getString("language_id")
        );
    }
}
