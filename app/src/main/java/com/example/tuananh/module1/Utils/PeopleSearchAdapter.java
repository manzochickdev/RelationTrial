package com.example.tuananh.module1.Utils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Activities.IMain3Activity;
import com.example.tuananh.module1.AddEditDetail.IMain2Activity;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutPeopleSearchItem1Binding;

import java.util.ArrayList;

public class PeopleSearchAdapter extends RecyclerView.Adapter<PeopleSearchAdapter.ViewHolder> {
    Context context;
    ArrayList<Model> models;
    int clicked;
    int mode;
    ArrayList<Integer> integers;

    public PeopleSearchAdapter(Context context, ArrayList<Model> models,int mode,ArrayList<Integer> integers) {
        this.context = context;
        this.models = models;
        this.mode = mode;
        if (integers==null) {
            this.integers = new ArrayList<>();
        }
        else this.integers = integers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_people_search_item_1,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Model model = models.get(i);
        viewHolder.layoutPeopleSearchItemBinding.setModel(model);
        if (mode==0) {viewHolder.layoutPeopleSearchItemBinding.setClicked(clicked==model.getId());}
        else {
            if (integers.contains(model.getId())) {
                viewHolder.layoutPeopleSearchItemBinding.setClicked(true);
            }
            else  viewHolder.layoutPeopleSearchItemBinding.setClicked(false);
        }
        viewHolder.layoutPeopleSearchItemBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode==0){
                    clicked = models.get(i).getId();
                    ((IMain2Activity)context).onModelBack(model);
                }
                else {
                    if (viewHolder.layoutPeopleSearchItemBinding.getClicked()){
                        viewHolder.layoutPeopleSearchItemBinding.setClicked(false);
                        integers.remove(integers.indexOf(model.getId()));
                        ((IMain3Activity) context).removeModel(model);
                    }
                    else {
                        viewHolder.layoutPeopleSearchItemBinding.setClicked(true);
                        integers.add(model.getId());
                        ((IMain3Activity) context).addModel(model);
                    }
                }
                notifyDataSetChanged();
            }
        });
        viewHolder.layoutPeopleSearchItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPeopleSearchItem1Binding layoutPeopleSearchItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPeopleSearchItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
