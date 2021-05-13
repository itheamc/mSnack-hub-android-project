package com.itheamc.msnackshub.api;

import androidx.annotation.NonNull;

import com.itheamc.msnackshub.models.Category;
import com.itheamc.msnackshub.models.Hotel;
import com.itheamc.msnackshub.models.Order;
import com.itheamc.msnackshub.models.Product;
import com.itheamc.msnackshub.models.Review;
import com.itheamc.msnackshub.utils.NotifyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class DataHandler {
    private static final String TAG = "DataHandler";

    /**
     * ----------------------------------------------------------------------
     * Categories handler
     * @param response - it is the response object got from the api request
     *-----------------------------------------------------------------------
     */
    public static List<Category> handleCategories(@NonNull Response response) {
        List<Category> categories = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray("categories");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);

                categories.add(new Category(
                        jo.getString("_id"),
                        jo.getString("_title"),
                        jo.getInt("_total_products")
                ));
            }
        } catch (JSONException | IOException e) {
            NotifyUtils.logError(TAG, "handleHotels", e);

        }

        return categories;
    }


    /**
     * ----------------------------------------------------------------------
     * Products handler
     * @param response - it is the response object got from the api request
     *-----------------------------------------------------------------------
     */
    public static List<Product> handleProducts(@NonNull Response response) {
        List<Product> products = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray("products");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String id = jo.getString("_id");
                String name = jo.getString("_name");
                String desc = jo.getString("_desc");
                String sellerId = jo.getString("_seller_id");
                String image = jo.getString("_image");
                Double price = jo.getDouble("_price");
                int total_viewed = jo.getInt("_total_viewed");
                int total_sold = jo.getInt("_total_sold");
                boolean is_available = jo.getBoolean("_is_available");
                String listed_on = jo.getString("_listed_on");
                JSONArray reviews = jo.getJSONArray("_reviews");
                List<Review> reviewList = new ArrayList<>();

                for (int j = 0; j < reviews.length(); j++) {
                    JSONObject jo1 = reviews.getJSONObject(j);
                    reviewList.add(new Review(
                            jo1.getString("_user_id"),
                            jo1.getDouble("_rating"),
                            jo1.getString("_review")
                    ));
                }


                products.add(new Product(
                        id,
                        name,
                        desc,
                        sellerId,
                        image,
                        price,
                        total_viewed,
                        total_sold,
                        is_available,
                        reviewList,
                        listed_on
                        ));
            }
        } catch (JSONException | IOException e) {
            NotifyUtils.logError(TAG, "handleHotels", e);
        }

        return products;
    }

    /**
     * ----------------------------------------------------------------------
     * Hotels handler
     * @param response - it is the response object got from the api request
     *-----------------------------------------------------------------------
     */
    public static List<Hotel> handleHotels(@NonNull Response response) {
        List<Hotel> hotels = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray("hotels");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String id = jo.getString("_id");
                String name = jo.getString("_name");
                String address = jo.getString("_address");
                String owner = jo.getString("_owner");
                String image = jo.getString("_image");
                Double lat = jo.getDouble("_latitude");
                Double lon = jo.getDouble("_longitude");
                String listed_on = jo.getString("_listed_on");
                String established_on = jo.getString("_established_on");
                boolean _is_active = jo.getBoolean("_is_active");
                String api_key = jo.getString("_api_key");

                hotels.add(new Hotel(
                        id,
                        name,
                        address,
                        owner,
                        image,
                        lat,
                        lon,
                        listed_on,
                        established_on,
                        _is_active,
                        api_key
                ));
            }

        } catch (JSONException | IOException e) {
            NotifyUtils.logError(TAG, "handleHotels", e);
        }

        return hotels;
    }

    /**
     * ----------------------------------------------------------------------
     * Orders handler
     * @param response - it is the response object got from the api request
     *-----------------------------------------------------------------------
     */
    public static List<Order> handleOrders(@NonNull Response response) {
        List<Order> orders = new ArrayList<>();

        return orders;
    }
}
