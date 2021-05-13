package com.itheamc.msnackshub.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Hotel {
    private String _id;
    private String _name;
    private String _address;
    private String _owner;
    private String _image;
    private Double _latitude;
    private Double _longitude;
    private String _listed_on;
    private String _established_on;
    private Boolean _is_active;
    private String _api_key;


    // Constructor
    public Hotel(String _id, String _name, String _address, String _owner, String _image, Double _latitude, Double _longitude, String _listed_on, String _established_on, Boolean _is_active, String _api_key) {
        this._id = _id;
        this._name = _name;
        this._address = _address;
        this._owner = _owner;
        this._image = _image;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._listed_on = _listed_on;
        this._established_on = _established_on;
        this._is_active = _is_active;
        this._api_key = _api_key;
    }

    // Creating DiffUtil.ItemCallback
    public static DiffUtil.ItemCallback<Hotel> hotelItemCallback = new DiffUtil.ItemCallback<Hotel>() {
        @Override
        public boolean areItemsTheSame(@NonNull Hotel oldItem, @NonNull Hotel newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Hotel oldItem, @NonNull Hotel newItem) {
            return false;
        }
    };

}
