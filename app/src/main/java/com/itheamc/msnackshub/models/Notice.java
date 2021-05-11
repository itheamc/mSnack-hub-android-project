package com.itheamc.msnackshub.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class Notice {
    private int _id;
    private String _title;
    private String _desc;
    private String _image_url;

    // Constructor
    public Notice() {
    }

    // Constructor with parameters
    public Notice(int _id, String _title, String _desc, String _image_url) {
        this._id = _id;
        this._title = _title;
        this._desc = _desc;
        this._image_url = _image_url;
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

    public String get_image_url() {
        return _image_url;
    }

    public void set_image_url(String _image_url) {
        this._image_url = _image_url;
    }


    // Overriding toString() method
    @Override
    public String toString() {
        return "Notice{" +
                "_id=" + _id +
                ", _title='" + _title + '\'' +
                ", _desc='" + _desc + '\'' +
                ", _image_url='" + _image_url + '\'' +
                '}';
    }

    // Overriding equals() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return get_id() == notice.get_id() &&
                get_title().equals(notice.get_title()) &&
                get_desc().equals(notice.get_desc()) &&
                Objects.equals(get_image_url(), notice.get_image_url());
    }


    // DiffUtil.ItemCallback<Notice> object
    public static DiffUtil.ItemCallback<Notice> noticeItemCallback = new DiffUtil.ItemCallback<Notice>() {
        @Override
        public boolean areItemsTheSame(@NonNull Notice oldItem, @NonNull Notice newItem) {
            return newItem.equals(oldItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notice oldItem, @NonNull Notice newItem) {
            return newItem.get_id() == oldItem.get_id() &&
                    newItem.get_title().equals(oldItem.get_title()) &&
                    newItem.get_desc().equals(oldItem.get_desc()) &&
                    newItem.get_image_url().equals(oldItem.get_image_url());
        }
    };


}
