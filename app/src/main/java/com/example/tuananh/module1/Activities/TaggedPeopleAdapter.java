package com.example.tuananh.module1.Activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutPeopleCircleItemBinding;

import java.util.ArrayList;

public class TaggedPeopleAdapter extends RecyclerView.Adapter<TaggedPeopleAdapter.ViewHolder> {
    Context context;
    ArrayList<Model> models;

    public TaggedPeopleAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutPeopleCircleItemBinding layoutPeopleCircleItemBinding = DataBindingUtil.inflate(inflater,R.layout.layout_people_circle_item,viewGroup,false);

        return new ViewHolder(layoutPeopleCircleItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.layoutPeopleCircleItemBinding.setModel(models.get(i));
        viewHolder.layoutPeopleCircleItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPeopleCircleItemBinding layoutPeopleCircleItemBinding;
        public ViewHolder(@NonNull LayoutPeopleCircleItemBinding layoutPeopleCircleItemBinding) {
            super(layoutPeopleCircleItemBinding.getRoot());
            this.layoutPeopleCircleItemBinding = layoutPeopleCircleItemBinding;
        }
    }
}
