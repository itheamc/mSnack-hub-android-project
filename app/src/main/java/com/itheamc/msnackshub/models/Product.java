package com.itheamc.msnackshub.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

public class Product {
    private String _id;
    private String _name;
    private String _desc;
    private String _seller_id;
    private String _image;
    private Double _price;
    private int _total_viewed;
    private int _total_sold;
    private Boolean _is_available;
    private List<Review> _reviews;
    private String _listed_on;


    // Constructor
    public Product() {
    }


    // Constructor with parameters
    public Product(String _id, String _name, String _desc, String _seller_id, String _image, Double _price, int _total_viewed, int _total_sold, Boolean _is_available, List<Review> _reviews, String _listed_on) {
        this._id = _id;
        this._name = _name;
        this._desc = _desc;
        this._seller_id = _seller_id;
        this._image = _image;
        this._price = _price;
        this._total_viewed = _total_viewed;
        this._total_sold = _total_sold;
        this._is_available = _is_available;
        this._reviews = _reviews;
        this._listed_on = _listed_on;
    }

    // Getters and Setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public String get_seller_id() {
        return _seller_id;
    }

    public void set_seller_id(String _seller_id) {
        this._seller_id = _seller_id;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public Double get_price() {
        return _price;
    }

    public void set_price(Double _price) {
        this._price = _price;
    }

    public int get_total_viewed() {
        return _total_viewed;
    }

    public void set_total_viewed(int _total_viewed) {
        this._total_viewed = _total_viewed;
    }

    public int get_total_sold() {
        return _total_sold;
    }

    public void set_total_sold(int _total_sold) {
        this._total_sold = _total_sold;
    }

    public Boolean get_is_available() {
        return _is_available;
    }

    public void set_is_available(Boolean _is_available) {
        this._is_available = _is_available;
    }

    public List<Review> get_reviews() {
        return _reviews;
    }

    public void set_reviews(List<Review> _reviews) {
        this._reviews = _reviews;
    }

    public String get_listed_on() {
        return _listed_on;
    }

    public void set_listed_on(String _listed_on) {
        this._listed_on = _listed_on;
    }

    // Equals Method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return get_total_viewed() == product.get_total_viewed() &&
                get_total_sold() == product.get_total_sold() &&
                get_id().equals(product.get_id()) &&
                get_name().equals(product.get_name()) &&
                Objects.equals(get_desc(), product.get_desc()) &&
                get_seller_id().equals(product.get_seller_id()) &&
                Objects.equals(get_image(), product.get_image()) &&
                get_price().equals(product.get_price()) &&
                Objects.equals(get_is_available(), product.get_is_available()) &&
                Objects.equals(get_reviews(), product.get_reviews()) &&
                Objects.equals(get_listed_on(), product.get_listed_on());
    }



    // Creating DiffUtil.ItemCallback
    public static DiffUtil.ItemCallback<Product> productItemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return newItem.equals(oldItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return false;
        }
    };

}
