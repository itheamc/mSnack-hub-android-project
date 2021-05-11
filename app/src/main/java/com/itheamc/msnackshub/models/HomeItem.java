package com.itheamc.msnackshub.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

public class HomeItem {
    private int _id;
    private int _view_type;
    private int _item_type;
    private List<?> items;
    private String _title;

    // Constructor
    public HomeItem() {
    }

    // Constructor with parameters
    public HomeItem(int _id, int _view_type, int _item_type, List<?> items, String _title) {
        this._id = _id;
        this._view_type = _view_type;
        this._item_type = _item_type;
        this.items = items;
        this._title = _title;
    }

    // Getters and Setters
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_view_type() {
        return _view_type;
    }

    public void set_view_type(int _view_type) {
        this._view_type = _view_type;
    }

    public int get_item_type() {
        return _item_type;
    }

    public void set_item_type(int _item_type) {
        this._item_type = _item_type;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }


    // Overriding toString() method for debugging purpose only
    @Override
    public String toString() {
        return "HomeItem{" +
                "_id=" + _id +
                ", _view_type=" + _view_type +
                ", _item_type=" + _item_type +
                ", items=" + items +
                ", _title='" + _title + '\'' +
                '}';
    }

    // Overriding equals() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeItem homeItem = (HomeItem) o;
        return get_id() == homeItem.get_id() &&
                get_view_type() == homeItem.get_view_type() &&
                get_item_type() == homeItem.get_item_type() &&
                Objects.equals(getItems(), homeItem.getItems()) &&
                get_title().equals(homeItem.get_title());
    }


    // Creating DiffUtil.ItemCallback
    public static DiffUtil.ItemCallback<HomeItem> homeItemItemCallback = new DiffUtil.ItemCallback<HomeItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull HomeItem oldItem, @NonNull HomeItem newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull HomeItem oldItem, @NonNull HomeItem newItem) {
            return oldItem.get_id() == newItem.get_id() &&
                    oldItem.get_view_type() == newItem.get_view_type() &&
                    oldItem.get_item_type() == newItem.get_item_type() &&
                    oldItem.get_title().equals(newItem.get_title()) &&
                    oldItem.getItems().equals(newItem.getItems());
        }
    };
}
