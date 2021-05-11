package com.itheamc.msnackshub.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itheamc.msnackshub.databinding.CategoryViewBinding;
import com.itheamc.msnackshub.models.Category;

import static com.itheamc.msnackshub.models.Category.categoryItemCallback;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter() {
        super(categoryItemCallback);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CategoryViewBinding categoryViewBinding = CategoryViewBinding.inflate(inflater, parent, false);
        return new CategoryViewHolder(categoryViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = getItem(position);
        holder.categoryViewBinding.setCategory(category);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final CategoryViewBinding categoryViewBinding;

        public CategoryViewHolder(@NonNull CategoryViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.categoryViewBinding = viewBinding;
        }
    }
}
