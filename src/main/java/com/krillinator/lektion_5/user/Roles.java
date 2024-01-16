package com.krillinator.lektion_5.user;

public enum Roles {

    ADMIN("GET" + "POST"),
    USER("GET");

    private final String permissions;

    Roles(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }
}


