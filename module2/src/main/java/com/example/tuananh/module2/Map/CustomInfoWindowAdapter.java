package com.example.tuananh.module2.Map;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module2.Model;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.LayoutProfileImageCircleBinding;

import java.util.ArrayList;

public class CustomInfoWindowAdapter extends RecyclerView.Adapter<CustomInfoWindowAdapter.ViewHolder> {
    ArrayList<Integer> models;
    Context context;

    public CustomInfoWindowAdapter(ArrayList<Integer> models,Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_profile_image_circle,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.layoutProfileImageCircleBinding.setId(models.get(i));
        viewHolder.layoutProfileImageCircleBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutProfileImageCircleBinding layoutProfileImageCircleBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutProfileImageCircleBinding = DataBindingUtil.bind(itemView);
        }
    }
}
