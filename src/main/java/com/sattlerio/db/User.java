package com.sattlerio.db;

public class User {
    private String uuid;
    private Integer permission;

    public User() {}

    public User(String uuid, Integer permission) {
        this.uuid = uuid;
        this.permission = permission;
    }

    public String getUuid() {
        return uuid;
    }

    public Integer getPermission() {
        return permission;
    }

    public boolean checkPermission(Integer perm) {
        if (this.permission > perm) {
            return false;
        } else {
            return true;
        }
    }
}
