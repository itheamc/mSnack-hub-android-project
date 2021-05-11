package com.itheamc.msnackshub.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.itheamc.msnackshub.databinding.ItemViewBinding;
import com.itheamc.msnackshub.databinding.NoticeViewBinding;
import com.itheamc.msnackshub.databinding.SlidersViewBinding;
import com.itheamc.msnackshub.models.Category;
import com.itheamc.msnackshub.models.HomeItem;
import com.itheamc.msnackshub.models.Hotel;
import com.itheamc.msnackshub.models.Notice;
import com.itheamc.msnackshub.models.Product;
import com.itheamc.msnackshub.models.Slider;
import com.itheamc.msnackshub.handlers.SliderHandler;

import java.util.List;

import static com.itheamc.msnackshub.models.HomeItem.homeItemItemCallback;
import static com.itheamc.msnackshub.utils.Constraints.ITEM_TYPE_CATEGORY;
import static com.itheamc.msnackshub.utils.Constraints.ITEM_TYPE_HOTEL;
import static com.itheamc.msnackshub.utils.Constraints.VIEW_TYPE_NOTICE;
import static com.itheamc.msnackshub.utils.Constraints.VIEW_TYPE_SLIDERS;

public class HomeAdapter extends ListAdapter<HomeItem, RecyclerView.ViewHolder> {

    public HomeAdapter() {
        super(homeItemItemCallback);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).get_view_type();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_SLIDERS) {
            SlidersViewBinding slidersViewBinding = SlidersViewBinding.inflate(inflater, parent, false);
            return new SliderViewHolder(slidersViewBinding);
        } else if (viewType == VIEW_TYPE_NOTICE) {
            NoticeViewBinding noticeViewBinding = NoticeViewBinding.inflate(inflater, parent, false);
            return new NoticeViewHolder(noticeViewBinding);
        } else {
            ItemViewBinding itemViewBinding = ItemViewBinding.inflate(inflater, parent, false);
            return new ItemViewHolder(itemViewBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeItem homeItem = getItem(position);
        List<?> items = homeItem.getItems();
        int viewType = homeItem.get_view_type();
        int itemType = homeItem.get_item_type();

        // Checking items viewType
        if (viewType == VIEW_TYPE_SLIDERS) {
            SliderAdapter sliderAdapter = new SliderAdapter();
            ViewPager2 viewPager = ((SliderViewHolder) holder).slidersViewBinding.viewPager;
            viewPager.setAdapter(sliderAdapter);
            sliderAdapter.submitList((List<Slider>) items);
            SliderHandler.getInstance().startAutoSlider(viewPager, (List<Slider>) items);

        } else if (viewType == VIEW_TYPE_NOTICE) {
            ((NoticeViewHolder) holder).noticeViewBinding.setNotice((Notice) items.get(0));

        } else {    // If view type == VIEW_TYPE_ITEMS
            ((ItemViewHolder) holder).itemViewBinding.setHomeitem(homeItem);
            if (itemType == ITEM_TYPE_HOTEL) {
                HotelsAdapter hotelsAdapter = new HotelsAdapter();
                ((ItemViewHolder) holder).itemViewBinding.homeItemRecyclerview.setAdapter(hotelsAdapter);
                hotelsAdapter.submitList((List<Hotel>) items);
            } else if (itemType == ITEM_TYPE_CATEGORY) {
                CategoryAdapter categoryAdapter = new CategoryAdapter();
                ((ItemViewHolder) holder).itemViewBinding.homeItemRecyclerview.setAdapter(categoryAdapter);
                categoryAdapter.submitList((List<Category>) items);
            } else {
                ProductsAdapter productsAdapter = new ProductsAdapter();
                ((ItemViewHolder) holder).itemViewBinding.homeItemRecyclerview.setAdapter(productsAdapter);
                productsAdapter.submitList((List<Product>) items);
            }
        }
    }


    // This is a view holder class for for Item View other than sliders and ads
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemViewBinding itemViewBinding;

        public ItemViewHolder(@NonNull ItemViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.itemViewBinding = viewBinding;
        }
    }

    // This is a view holder class for Sliders View
    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        private final SlidersViewBinding slidersViewBinding;

        public SliderViewHolder(@NonNull SlidersViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.slidersViewBinding = viewBinding;
        }
    }

    // This is a view holder class for Ads View
    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        private final NoticeViewBinding noticeViewBinding;

        public NoticeViewHolder(@NonNull NoticeViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.noticeViewBinding = viewBinding;
        }
    }
}
