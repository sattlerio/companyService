package com.sattlerio.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlQuery("SELECT u.user_uuid as uuid, ucp.permission as permission FROM users u JOIN rel_user2company u2c ON u.id = u2c.user_id AND u2c.company_id = :company_id JOIN user_companies_permissions ucp ON u.id = ucp.user_id AND u2c.company_id = ucp.company_id WHERE u.user_uuid = :user_uuid")
    User getUserPermissionById(@Bind("company_id") Integer company_id, @Bind("user_uuid") String user_uuid);
}
