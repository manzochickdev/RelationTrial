package com.example.tuananh.module1.RelationView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.People.IMainActivity;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutRelationBinding;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<ModelView> models;
    Context context;
    IModule3 iModule3;
    int pos=-1;
    int id=-1;

    public MainAdapter(ArrayList<ModelView> models, Context context,IModule3 iModule3) {
        this.models = models;
        this.context = context;
        this.iModule3 = iModule3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_relation,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.layoutRelationBinding.setVisible(pos==i);
        if (id!=-1){
            viewHolder.layoutRelationBinding.layoutInfoSummary.setIsView(false);
            viewHolder.layoutRelationBinding.layoutInfoSummary.setModel(DatabaseHandle.getInstance(context).getPerson(id));
            viewHolder.layoutRelationBinding.layoutInfoSummary.setIMainActivity((IMainActivity) context);
        }

        SubAdapter subAdapter = new SubAdapter(models.get(i),i);
        viewHolder.layoutRelationBinding.rvPeopleItem.setAdapter(subAdapter);
        viewHolder.layoutRelationBinding.rvPeopleItem.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        viewHolder.layoutRelationBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void showSummaryInfo(int id,int pos) {
        this.pos=pos;
        this.id=id;
        notifyDataSetChanged();
    }
    public void discardShowSummaryInfo(){
        if (pos!=-1 && id!=-1){
            this.pos=-1;
            this.id=-1;
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRelationBinding layoutRelationBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutRelationBinding = DataBindingUtil.bind(itemView);
        }
    }
}
