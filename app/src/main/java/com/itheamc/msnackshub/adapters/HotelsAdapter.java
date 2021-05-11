package com.itheamc.msnackshub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itheamc.msnackshub.databinding.HotelViewBinding;
import com.itheamc.msnackshub.models.Hotel;

import static com.itheamc.msnackshub.models.Hotel.hotelItemCallback;

public class HotelsAdapter extends ListAdapter<Hotel, HotelsAdapter.HotelViewHolder> {

    public HotelsAdapter() {
        super(hotelItemCallback);
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HotelViewBinding hotelViewBinding = HotelViewBinding.inflate(inflater, parent, false);
        return new HotelViewHolder(hotelViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = getItem(position);
        holder.hotelViewBinding.setHotel(hotel);
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        private final HotelViewBinding hotelViewBinding;

        public HotelViewHolder(@NonNull HotelViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.hotelViewBinding = viewBinding;
        }
    }
}
