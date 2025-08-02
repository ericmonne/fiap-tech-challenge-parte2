package com.fiap.tech_challenge.parte1.ms_users.api.routes;

public class RestaurantRoutes {
    private RestaurantRoutes() {}
    public static final String RESTAURANTS_BASE = "/restaurants";
    public static final String RESTAURANT_ID = "/{restaurantId}";
    public static final String ID_AND_MENU_ITEMS = RESTAURANT_ID + "/menu-items";

}
