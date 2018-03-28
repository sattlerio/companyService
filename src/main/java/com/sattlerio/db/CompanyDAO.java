package com.sattlerio.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(CompanyMapper.class)
public interface CompanyDAO {

    @SqlQuery("SELECT c.company_uuid as company_uuid, c.name as name, c.country_id as country_id, c.address as address, c.zip as zip, c.city as city, c.vat as vat, c.logo as logo, c.phone_number as phone_number, c.email_address as email_address, c.accounting_email as accounting_email, c.technical_email as technical_email, c.language_id as language_id, c.currency_id as currency_id FROM companies c JOIN users u ON u.user_uuid = :user_uuid JOIN rel_user2company rel ON rel.user_id = u.id AND rel.company_id = c.id WHERE c.company_uuid = :company_id")
    Company getComanyInfoByCompanyId(@Bind("user_uuid") String user_uuid, @Bind("company_id") String company_uuid);
}
