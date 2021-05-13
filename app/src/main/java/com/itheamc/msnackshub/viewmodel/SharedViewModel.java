package com.itheamc.msnackshub.viewmodel;


import androidx.lifecycle.ViewModel;

import com.itheamc.msnackshub.models.Category;
import com.itheamc.msnackshub.models.HomeItem;
import com.itheamc.msnackshub.models.Hotel;
import com.itheamc.msnackshub.models.Notice;
import com.itheamc.msnackshub.models.Order;
import com.itheamc.msnackshub.models.Product;
import com.itheamc.msnackshub.models.Results;
import com.itheamc.msnackshub.models.Review;
import com.itheamc.msnackshub.models.Slider;
import com.itheamc.msnackshub.models.User;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private User user;
    private List<Product> products;
    private List<Hotel> hotels;
    private List<Category> categories;
    private List<Notice> notices;
    private List<Results> results;
    private List<Slider> sliders;
    private List<Review> reviews;
    private List<HomeItem> homeItems;
    private List<Order> orders;
    private double latitude;
    private double longitude;


    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products = products;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        if (this.hotels == null) {
            this.hotels = new ArrayList<>();
        }
        this.hotels = hotels;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }
        this.categories = categories;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        if (this.notices == null) {
            this.notices = new ArrayList<>();
        }
        this.notices = notices;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }
        this.results = results;
    }

    public List<Slider> getSliders() {
        return sliders;
    }

    public void setSliders(List<Slider> sliders) {
        if (this.sliders == null) {
            this.sliders = new ArrayList<>();
        }
        this.sliders = sliders;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        if (this.reviews == null) {
            this.reviews = new ArrayList<>();
        }
        this.reviews = reviews;
    }

    public List<HomeItem> getHomeItems() {
        return homeItems;
    }

    public void setHomeItems(List<HomeItem> homeItems) {
        if (this.homeItems == null) {
            this.homeItems = new ArrayList<>();
        }
        this.homeItems = homeItems;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
        this.orders = orders;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
