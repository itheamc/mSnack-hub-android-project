package com.itheamc.msnackshub.callbacks;

import com.itheamc.msnackshub.models.Category;
import com.itheamc.msnackshub.models.Hotel;
import com.itheamc.msnackshub.models.Order;
import com.itheamc.msnackshub.models.Product;

import java.util.List;

public interface RequestCallbacks {
    void onCategoriesFetched(List<Category> categories);
    void onProductsFetched(List<Product> products);
    void onHotelsFetched(List<Hotel> hotels);
    void onOrdersFetched(List<Order> orders);
    void onRequestFailure(int api_route, String error);

}
