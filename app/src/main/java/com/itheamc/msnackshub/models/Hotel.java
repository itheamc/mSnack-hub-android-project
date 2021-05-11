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
