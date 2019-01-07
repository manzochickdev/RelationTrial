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
import com.example.tuananh.module2.databinding.LayoutCustomInfoPlusBinding;
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
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (i){
            case 0 :
                LayoutProfileImageCircleBinding layoutProfileImageCircleBinding = DataBindingUtil.inflate(inflater,R.layout.layout_profile_image_circle,viewGroup,false);
                return new ViewHolder(layoutProfileImageCircleBinding);

            case 1:
                LayoutCustomInfoPlusBinding layoutCustomInfoPlusBinding = DataBindingUtil.inflate(inflater,R.layout.layout_custom_info_plus,viewGroup,false);
                return new ViewHolder(layoutCustomInfoPlusBinding);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()){
            case 0:
                viewHolder.layoutProfileImageCircleBinding.setId(models.get(i));
                viewHolder.layoutProfileImageCircleBinding.executePendingBindings();
                break;
            case 1:
                int plus = models.size()-2;
                viewHolder.layoutCustomInfoPlusBinding.setText("+"+plus);
                viewHolder.layoutCustomInfoPlusBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (models.size()<=3) return models.size();
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==2){
            if (models.size()<=3) return 0;
            return 1;
        }
        else return 0;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutProfileImageCircleBinding layoutProfileImageCircleBinding;
        LayoutCustomInfoPlusBinding layoutCustomInfoPlusBinding;
        public ViewHolder(@NonNull LayoutProfileImageCircleBinding layoutProfileImageCircleBinding) {
            super(layoutProfileImageCircleBinding.getRoot());
            this.layoutProfileImageCircleBinding = layoutProfileImageCircleBinding;
        }

        public ViewHolder(@NonNull LayoutCustomInfoPlusBinding layoutCustomInfoPlusBinding) {
            super(layoutCustomInfoPlusBinding.getRoot());
            this.layoutCustomInfoPlusBinding = layoutCustomInfoPlusBinding;
        }
    }
}
