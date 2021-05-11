package com.itheamc.msnackshub.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itheamc.msnackshub.models.Results;

public class ResultsAdapter extends ListAdapter<Results, ResultsAdapter.ResultViewHolder> {

    protected ResultsAdapter(@NonNull DiffUtil.ItemCallback<Results> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
