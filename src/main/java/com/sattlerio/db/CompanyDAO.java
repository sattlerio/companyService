package com.sattlerio.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigInteger;

@RegisterMapper(CompanyMapper.class)
public interface CompanyDAO {

    @SqlQuery("SELECT c.company_uuid as company_uuid, c.name as name, c.country_id as country_id, c.address as address, c.zip as zip, c.city as city, c.vat as vat, c.logo as logo, c.phone_number as phone_number, c.email_address as email_address, c.accounting_email as accounting_email, c.technical_email as technical_email, c.language_id as language_id, c.currency_id as currency_id FROM companies c JOIN users u ON u.user_uuid = :user_uuid JOIN rel_user2company rel ON rel.user_id = u.id AND rel.company_id = c.id WHERE c.company_uuid = :company_id")
    Company getComanyInfoByCompanyId(@Bind("user_uuid") String user_uuid, @Bind("company_id") String company_uuid);

    @SqlQuery("SELECT c.id as company_id FROM companies c WHERE company_uuid = :company_uuid")
    Integer getCompanyIdFromCompanyUuid(@Bind("company_uuid") String company_uuid);

    @SqlUpdate("UPDATE companies SET name = :company_name, address = :company_address, zip = :company_zip, city = :company_city, country_id = :company_country_id, language_id = :company_language_id, vat = :company_vat WHERE company_uuid = :company_uuid")
    void updateCompanySettings(@Bind("company_name") String company_name,
                               @Bind("company_address") String company_address,
                               @Bind("company_zip") String company_zip,
                               @Bind("company_city") String city,
                               @Bind("company_vat") String company_vat,
                               @Bind("company_country_id") String company_country_id,
                               @Bind("company_language_id") String company_language_id,
                               @Bind("company_uuid") String company_uuid);
}
