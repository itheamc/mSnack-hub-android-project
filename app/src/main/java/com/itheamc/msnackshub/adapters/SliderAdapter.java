package com.itheamc.msnackshub.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itheamc.msnackshub.databinding.SliderViewBinding;
import com.itheamc.msnackshub.models.Slider;

import static com.itheamc.msnackshub.models.Slider.sliderItemCallback;

public class SliderAdapter extends ListAdapter<Slider, SliderAdapter.SliderViewHolder> {

    protected SliderAdapter() {
        super(sliderItemCallback);
    }

    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
        SliderViewBinding sliderViewBinding = SliderViewBinding.inflate(inflater, parent, false);
        return new SliderViewHolder(sliderViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.SliderViewHolder holder, int position) {
        Slider slider = getItem(position);
        holder.sliderViewBinding.setSlider(slider);
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        private SliderViewBinding sliderViewBinding;

        public SliderViewHolder(@NonNull SliderViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.sliderViewBinding = viewBinding;
        }
    }

}
