package com.example.tuananh.module2.MapManipulation.PeopleSearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module2.IModule2;
import com.example.tuananh.module2.ModelAddress;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.LayoutProfileImageCircleBinding;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    String query;
    Context context;
    ArrayList<ModelAddress> modelAddresses;
    IModule2 iModule2;

    public Adapter(String query, Context context,IModule2 iModule2) {
        this.query = query;
        this.context = context;
        this.modelAddresses = SearchHandle.getInstance(context).search(query);
        this.iModule2 = iModule2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_profile_image_circle,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.layoutProfileImageCircleBinding.setModelAddress(modelAddresses.get(i));
        viewHolder.layoutProfileImageCircleBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OK", "onClick: "+modelAddresses.get(i).name);
                iModule2.onAddressBack(modelAddresses.get(i).address,modelAddresses.get(i).latLng);
                iModule2.moveCamera(modelAddresses.get(i).latLng,modelAddresses.get(i).address);
            }
        });
        viewHolder.layoutProfileImageCircleBinding.executePendingBindings();
    }

    @Override

    public int getItemCount() {
        return modelAddresses.size();
    }

    public void onQuery(String query){
        this.query = query;
        this.modelAddresses = SearchHandle.getInstance(context).search(query);
        notifyDataSetChanged();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutProfileImageCircleBinding layoutProfileImageCircleBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutProfileImageCircleBinding = DataBindingUtil.bind(itemView);
        }
    }
}
