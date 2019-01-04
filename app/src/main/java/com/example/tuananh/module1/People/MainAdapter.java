package com.example.tuananh.module1.People;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.PeopleFragmentRowBinding;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<ArrayList<Model>> list;
    Context context;
    IMainActivity iMainActivity;
    ItemClickListener itemClickListener;
    Model model;
    Boolean clicked=false;

    public MainAdapter(ArrayList<Model> models, Context context) {
        this.list = handleList(models);
        this.context = context;
        this.iMainActivity = (IMainActivity) context;
        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClickListener(Model m) {
                model = m;
                notifyDataSetChanged();
            }
        };
    }

    private ArrayList<ArrayList<Model>> handleList(ArrayList<Model> models) {
        ArrayList<ArrayList<Model>> lists = new ArrayList<>();
        while(models.size()>=3){
            ArrayList<Model> temp = new ArrayList<>(models.subList(0,3));
            lists.add(temp);
            models.removeAll(temp);
        }
        lists.add(models);
        return lists;
    }

    private int findRow(){
        if (model==null) return -1;
        for (ArrayList<Model> models : list){
            Boolean b=false;
            for (Model m : models){
                if (m.getId()==model.getId()){
                    b=true;
                }
            }
            if (b) return list.indexOf(models);
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.people_fragment_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SubAdapter subAdapter = new SubAdapter(list.get(i),context,itemClickListener);
        viewHolder.peopleFragmentRowBinding.rvPeopleItem.setAdapter(subAdapter);
        viewHolder.peopleFragmentRowBinding.rvPeopleItem.setLayoutManager(new GridLayoutManager(context,3, LinearLayoutManager.VERTICAL,false));
        if (i==findRow()){
            if (!clicked){
                this.clicked=true;
                viewHolder.peopleFragmentRowBinding.setIsVisible(true);
                viewHolder.peopleFragmentRowBinding.layoutInfoSummary.setModel(model);
                viewHolder.peopleFragmentRowBinding.layoutInfoSummary.setIMainActivity((IMainActivity) context);
                viewHolder.peopleFragmentRowBinding.layoutInfoSummary.setIsView(true);
            }
            else {
                viewHolder.peopleFragmentRowBinding.setIsVisible(false);
                this.clicked=false;
            }

        }
        else viewHolder.peopleFragmentRowBinding.setIsVisible(false);

        viewHolder.peopleFragmentRowBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onItemClickListener(Model model);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        PeopleFragmentRowBinding peopleFragmentRowBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            peopleFragmentRowBinding = DataBindingUtil.bind(itemView);
        }
    }
}
