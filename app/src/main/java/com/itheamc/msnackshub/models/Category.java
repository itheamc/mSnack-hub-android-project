package com.itheamc.msnackshub.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class Category {
    private String _id;
    private String _title;
    private int _total_products;

    // Constructor
    public Category() {
    }

    // Constructor with parameters
    public Category(String _id, String _title, int _total_products) {
        this._id = _id;
        this._title = _title;
        this._total_products = _total_products;
    }

    // Getters and Setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_total_products() {
        return _total_products;
    }

    public void set_total_products(int _total_products) {
        this._total_products = _total_products;
    }

    // Overriding equals() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return get_total_products() == category.get_total_products() &&
                get_id().equals(category.get_id()) &&
                get_title().equals(category.get_title());
    }


    // Creating DiffUtil.ItemCallback
    public static DiffUtil.ItemCallback<Category> categoryItemCallback = new DiffUtil.ItemCallback<Category>() {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return newItem.equals(oldItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return newItem.get_id().equals(oldItem.get_id()) &&
                    newItem.get_title().equals(oldItem.get_title()) &&
                    newItem.get_total_products() == oldItem.get_total_products();
        }
    };
}
