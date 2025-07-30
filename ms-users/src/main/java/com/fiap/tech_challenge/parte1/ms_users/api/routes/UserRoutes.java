package com.fiap.tech_challenge.parte1.ms_users.api.routes;

public class UserRoutes {
    private UserRoutes() {
    }
    public static final String USERS_BASE = "/users";
    public static final String ID = "/{id}";
    public static final String LOGIN = "/login";
    public static final String PASSWORD = "/password";
    public static final String ID_AND_PASSWORD = ID + PASSWORD;
}
