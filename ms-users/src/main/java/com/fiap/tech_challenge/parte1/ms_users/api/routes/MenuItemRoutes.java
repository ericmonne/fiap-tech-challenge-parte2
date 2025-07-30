package com.fiap.tech_challenge.parte1.ms_users.api.routes;

public class MenuItemRoutes {

    private MenuItemRoutes() {
    }

    public static final String MENU_ITEMS_BASE = "/menu-items";
    public static final String ID = "/{id}";
    public static final String ALL = "/all";
    public static final String MENU_ITEMS_WITH_ID = MENU_ITEMS_BASE + ID;
    public static final String CHANGE_AVAILABILITY = "/change-availability";
    public static final String ID_AND_CHANGE_AVAILABILITY = ID + CHANGE_AVAILABILITY;

}
