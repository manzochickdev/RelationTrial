package com.example.tuananh.module2.Nearby;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module2.IModule21;
import com.example.tuananh.module2.Model;
import com.example.tuananh.module2.ModelAddress;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.LayoutPeopleNearbyBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Model> models;
    LatLng latLng;
    Context context;
    int isSelected=-1;


    public Adapter(ArrayList<Model> models, LatLng latLng, Context context) {
        this.models = models;
        this.latLng = latLng;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_people_nearby,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.layoutPeopleNearbyBinding.setModel(models.get(i));
        viewHolder.layoutPeopleNearbyBinding.setIsSelected(isSelected==i);
        viewHolder.layoutPeopleNearbyBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(isSelected);
                isSelected = i;
                viewHolder.layoutPeopleNearbyBinding.setIsSelected(isSelected==i);

            }
        });
        viewHolder.layoutPeopleNearbyBinding.tvDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OK", "Module 2 + adapter.java onClick: Direction");
            }
        });
        viewHolder.layoutPeopleNearbyBinding.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IModule21 iModule21 = (IModule21) context;
                iModule21.getDetailInfo(models.get(i).getId());
            }
        });
        viewHolder.layoutPeopleNearbyBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPeopleNearbyBinding layoutPeopleNearbyBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPeopleNearbyBinding = DataBindingUtil.bind(itemView);
        }
    }
}
