package com.example.tuananh.module1.RelationView;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutRelationItemBinding;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    ModelView modelView;
    int position;

    public SubAdapter(ModelView modelView, int position) {
        this.modelView = modelView;
        this.position = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(modelView.viewHandle.getContext());
        LayoutRelationItemBinding layoutRelationItemBinding = DataBindingUtil.inflate(inflater, R.layout.layout_relation_item,viewGroup,false);
        return new ViewHolder(layoutRelationItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.layoutRelationItemBinding.setVm(modelView.viewHandle);
        viewHolder.layoutRelationItemBinding.setModel(modelView.models.get(i));
        viewHolder.layoutRelationItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return modelView.models.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRelationItemBinding layoutRelationItemBinding;

        public ViewHolder(@NonNull LayoutRelationItemBinding layoutRelationItemBinding) {
            super(layoutRelationItemBinding.getRoot());
            this.layoutRelationItemBinding = layoutRelationItemBinding;
        }
    }
}
