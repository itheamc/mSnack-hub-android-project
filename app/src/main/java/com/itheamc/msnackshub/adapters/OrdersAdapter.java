package com.itheamc.msnackshub.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itheamc.msnackshub.models.Order;

import static com.itheamc.msnackshub.models.Order.orderItemCallback;

public class OrdersAdapter extends ListAdapter<Order, OrdersAdapter.OrderViewHolder> {

    public OrdersAdapter() {
        super(orderItemCallback);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
