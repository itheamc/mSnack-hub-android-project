package com.itheamc.msnackshub.api;

import okhttp3.Request;

public class Routes {
    private static final String BASE_URL = "";
    private static final String CATEGORIES_ROUTE = "";
    private static final String PRODUCTS_ROUTE = "";
    private static final String HOTELS_ROUTE = "";
    private static final String ORDERS_ROUTE = "";

    /**
     * API Request Code
     */
    public static final int CATEGORY = 12121;
    public static final int PRODUCT = 12122;
    public static final int HOTEL = 12123;
    public static final int ORDER = 12124;

    /**
     * GET REQUEST FOR CATEGORIES
     * to get the list of categories
     */
    public static Request categoryRequest = new Request.Builder().url(BASE_URL + PRODUCTS_ROUTE).build();

    /**
     * GET REQUEST FOR PRODUCTS
     * to add the product
     */
    public static Request productRequest = new Request.Builder().url(BASE_URL + CATEGORIES_ROUTE).build();

    /**
     * GET REQUEST FOR HOTELS
     * to get the list of hotels
     */
    public static Request hotelRequest = new Request.Builder().url(BASE_URL + HOTELS_ROUTE).build();

    /**
     * GET REQUEST FOR ORDERS
     * to add the orders
     */
    public static Request orderRequest = new Request.Builder().url(BASE_URL + ORDERS_ROUTE).build();

}
