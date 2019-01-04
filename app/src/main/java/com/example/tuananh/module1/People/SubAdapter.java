package com.example.tuananh.module1.People;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.PeopleFragmentItemBinding;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    ArrayList<Model> models;
    Context context;
    MainAdapter.ItemClickListener itemClickListener;

    public SubAdapter(ArrayList<Model> models, Context context, MainAdapter.ItemClickListener itemClickListener) {
        this.models = models;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.people_fragment_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.peopleFragmentItemBinding.setModel(models.get(i));
        viewHolder.peopleFragmentItemBinding.peopleItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClickListener(models.get(i));
            }
        });
        viewHolder.peopleFragmentItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        PeopleFragmentItemBinding peopleFragmentItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            peopleFragmentItemBinding = DataBindingUtil.bind(itemView);
        }
    }

}
