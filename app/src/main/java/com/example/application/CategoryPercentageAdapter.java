// CategoryPercentageAdapter.java
package com.example.application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryPercentageAdapter extends RecyclerView.Adapter<CategoryPercentageAdapter.ViewHolder> {

    private List<CategoryPercentageItem> categoryPercentageList;

    public CategoryPercentageAdapter(List<CategoryPercentageItem> categoryPercentageList) {
        this.categoryPercentageList = categoryPercentageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_percentage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryPercentageItem item = categoryPercentageList.get(position);

        holder.categoryTextView.setText(item.getCategory());
        holder.percentageTextView.setText(item.getPercentage());
    }

    @Override
    public int getItemCount() {
        return categoryPercentageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView percentageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            percentageTextView = itemView.findViewById(R.id.percentageTextView);
        }
    }
}
