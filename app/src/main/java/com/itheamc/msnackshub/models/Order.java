package com.itheamc.msnackshub.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Order {


    // Creating DiffUtil.ItemCallback
    public static DiffUtil.ItemCallback<Order> orderItemCallback = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return false;
        }
    };
}
