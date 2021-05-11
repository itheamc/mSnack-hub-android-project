package com.itheamc.msnackshub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itheamc.msnackshub.databinding.ProductViewBinding;
import com.itheamc.msnackshub.models.Product;

import static com.itheamc.msnackshub.models.Product.productItemCallback;

public class ProductsAdapter extends ListAdapter<Product, ProductsAdapter.ProductViewHolder> {


    public ProductsAdapter() {
        super(productItemCallback);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProductViewBinding productViewBinding = ProductViewBinding.inflate(inflater, parent, false);
        return new ProductViewHolder(productViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);
        holder.productViewBinding.setProduct(product);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ProductViewBinding productViewBinding;

        public ProductViewHolder(@NonNull ProductViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.productViewBinding = viewBinding;
        }
    }
}
