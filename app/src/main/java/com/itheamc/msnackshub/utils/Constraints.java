package com.itheamc.msnackshub.utils;

public class Constraints {

    /*
    Some constraints for the home view type
     */
    public static final int VIEW_TYPE_ITEMS = 10101;
    public static final int VIEW_TYPE_SLIDERS = 10102;
    public static final int VIEW_TYPE_NOTICE = 10103;

    /*
       Some constraints for the home item type
        */
    public static final int ITEM_TYPE_CATEGORY = 11001;
    public static final int ITEM_TYPE_HOTEL = 11002;
    public static final int ITEM_TYPE_POPULAR_PRODUCTS = 11003;
    public static final int ITEM_TYPE_NEW_PRODUCTS = 11004;
    public static final int ITEM_TYPE_RECOMMENDED_PRODUCTS = 11005;


    /*
    Some constraints for the search result type
     */
    public static final int RESULT_VIEW_TYPE_HOTELS = 12001;
    public static final int RESULT_VIEW_TYPE_PRODUCTS = 12002;

    /**
     * Request code
     */
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    public static final int GOOGLE_SIGN_IN_REQUEST_CODE = 102;
    public static final int FACEBOOK_SIGN_IN_REQUEST_CODE = 103;
    public static final int PHONE_SIGN_IN_REQUEST_CODE = 104;

    /**
     * SharedPreference Name
     */
    public static final String SHARED_PREFERENCE_NAME = "mSnacksHub";

    /**
     * SharedPreference Keys
     */
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "_name";
    public static final String USER_PHONE = "_phone";
    public static final String USER_EMAIL = "_email";
    public static final String USER_IMAGE = "_image";
    public static final String USER_LAT = "_lat";
    public static final String USER_LONG = "_long";
    public static final String USER_UID = "_uuid";



}
