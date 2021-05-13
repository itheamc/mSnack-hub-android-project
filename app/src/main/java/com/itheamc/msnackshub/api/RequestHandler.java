package com.itheamc.msnackshub.api;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;

import com.itheamc.msnackshub.callbacks.RequestCallbacks;
import com.itheamc.msnackshub.models.Category;
import com.itheamc.msnackshub.models.Hotel;
import com.itheamc.msnackshub.models.Order;
import com.itheamc.msnackshub.models.Product;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class RequestHandler {
    private static RequestHandler instance;
    private final OkHttpClient httpClient;
    private final RequestCallbacks requestCallbacks;
    private final Handler callbackHandler;

    // Constructor
    public RequestHandler(@NonNull RequestCallbacks requestCallbacks) {
        this.requestCallbacks = requestCallbacks;
        this.httpClient = new OkHttpClient.Builder().build();
        this.callbackHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    }

    // Getter for the instance of the class RequestHandler
    public static RequestHandler getInstance(@NonNull RequestCallbacks requestCallbacks) {
        if (instance == null) {
            instance = new RequestHandler(requestCallbacks);
        }

        return instance;
    }


    /**
     * _______________________________________________________________________________________
     * These are the functions to handle api request
     * These functions are responsible to make the valid api call and get the data back from the
     * server
     * ---------------------------------------------------------------------------------------
     */

    /**
     * CATEGORY REQUEST HANDLER
     * It will handle the get request to retrieve the list of categories
     */
    public void requestCategories() {
        httpClient.newCall(Routes.categoryRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                notifyCategoryRequestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                notifyCategoryRequestSuccess();
            }
        });
    }


    /**
     * PRODUCT REQUEST HANDLER
     * It will handle the get request to retrieve the list of products
     */
    public void requestProducts() {
        httpClient.newCall(Routes.productRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                notifyProductRequestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }


    /**
     * HOTELS REQUEST HANDLER
     * It will handle the get request to retrieve the list of hotels
     */
    public void requestHotels() {
        httpClient.newCall(Routes.hotelRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                notifyHotelRequestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    /**
     * ORDERS REQUEST HANDLER
     * It will handle the get request to retrieve the list of products
     */
    public void requestOrders() {
        httpClient.newCall(Routes.orderRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                notifyOrderRequestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    /**
     * ______________________________________________________________________________________
     * These are the notifier functions that will notify whenever request completed or failed
     * --------------------------------------------------------------------------------------
     */

    /**
    Category Request Notifier
     */
    private void notifyCategoryRequestSuccess(List<Category> categories) {
        callbackHandler.post(() -> {
            requestCallbacks.onCategoriesFetched(categories);
        });
    }

    private void notifyCategoryRequestFailure(String message) {
        callbackHandler.post(() -> {
           requestCallbacks.onRequestFailure(Routes.CATEGORY, message);
        });
    }

    /**
    Product Request Notifier
     */
    private void notifyProductRequestSuccess(List<Product> products) {
        callbackHandler.post(() -> {
            requestCallbacks.onProductsFetched(products);
        });
    }

    private void notifyProductRequestFailure(String message) {
        callbackHandler.post(() -> {
           requestCallbacks.onRequestFailure(Routes.PRODUCT, message);
        });
    }

    /**
    Hotel Request Notifier
     */
    private void notifyHotelRequestSuccess(List<Hotel> hotels) {
        callbackHandler.post(() -> {
            requestCallbacks.onHotelsFetched(hotels);
        });
    }

    private void notifyHotelRequestFailure(String message) {
        callbackHandler.post(() -> {
           requestCallbacks.onRequestFailure(Routes.HOTEL, message);
        });
    }

    /**
    Order Request Notifier
     */
    private void notifyOrderRequestSuccess(List<Order> orders) {
        callbackHandler.post(() -> {
            requestCallbacks.onOrdersFetched(orders);
        });
    }

    private void notifyOrderRequestFailure(String message) {
        callbackHandler.post(() -> {
           requestCallbacks.onRequestFailure(Routes.ORDER, message);
        });
    }




}
