package com.itheamc.msnackshub.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class Slider {
    private int _id;
    private String _title;
    private String _desc;
    private String _url;

    // Constructor
    public Slider() {
    }

    // Constructor with parameters
    public Slider(int _id, String _title, String _desc, String _url) {
        this._id = _id;
        this._title = _title;
        this._desc = _desc;
        this._url = _url;
    }

    // Getters and Setters
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    // Overriding toString() method
    @Override
    public String toString() {
        return "Slider{" +
                "_id=" + _id +
                ", _title='" + _title + '\'' +
                ", _desc='" + _desc + '\'' +
                ", _url='" + _url + '\'' +
                '}';
    }

    // Overriding equals() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slider slider = (Slider) o;
        return get_id() == slider.get_id() &&
                get_title().equals(slider.get_title()) &&
                get_desc().equals(slider.get_desc()) &&
                Objects.equals(get_url(), slider.get_url());
    }


    // DiffUtil.ItemCallback object
    public static DiffUtil.ItemCallback<Slider> sliderItemCallback = new DiffUtil.ItemCallback<Slider>() {
        @Override
        public boolean areItemsTheSame(@NonNull Slider oldItem, @NonNull Slider newItem) {
            return newItem.equals(oldItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Slider oldItem, @NonNull Slider newItem) {
            return newItem.get_id() == oldItem.get_id() &&
                    newItem.get_title().equals(oldItem.get_title()) &&
                    newItem.get_desc().equals(oldItem.get_desc()) &&
                    newItem.get_url().equals(oldItem.get_url());
        }
    };

}
