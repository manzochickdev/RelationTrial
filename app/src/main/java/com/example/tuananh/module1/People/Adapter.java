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
import com.example.tuananh.module1.databinding.LayoutPeopleItemBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<ArrayList<Model>> list;
    Context context;
    IMainActivity iMainActivity;
    int rootX=-1;
    int rootY=-1;
    boolean isRootChange;

    public Adapter(ArrayList<Model> models, Context context) {
        this.context = context;
        list = handleList(models);
        iMainActivity = (IMainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_people_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.layoutPeopleItemBinding.setModels(list.get(i));
        viewHolder.layoutPeopleItemBinding.setIsVisible(rootY==i);
        viewHolder.layoutPeopleItemBinding.layoutInfoSummary.setIsView(true);
        viewHolder.layoutPeopleItemBinding.layoutInfoSummary.setIMainActivity((IMainActivity) context);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                rootY=i;

                switch (v.getId()){
                    case R.id.container :
                        int id = list.get(i).get(0).getId();
                        viewHolder.layoutPeopleItemBinding.layoutInfoSummary.setModel(list.get(i).get(0));
                        if (rootX==id){
                            rootY=-1;
                            rootX=-1;
                        }
                        else rootX=id;
                        break;
                    case R.id.container1:
                        int id1 = list.get(i).get(1).getId();
                        viewHolder.layoutPeopleItemBinding.layoutInfoSummary.setModel(list.get(i).get(1));
                        if (rootX==id1){
                            rootY=-1;
                            rootX=-1;
                        }
                        else rootX=id1;
                        break;
                    case R.id.container2:
                        int id2 = list.get(i).get(2).getId();
                        viewHolder.layoutPeopleItemBinding.layoutInfoSummary.setModel(list.get(i).get(2));
                        if (rootX==id2){
                            rootY=-1;
                            rootX=-1;
                        }
                        else rootX=id2;
                        break;
                }
                notifyDataSetChanged();
            }
        };
        viewHolder.layoutPeopleItemBinding.container.setOnClickListener(onClickListener);
        viewHolder.layoutPeopleItemBinding.container1.setOnClickListener(onClickListener);
        viewHolder.layoutPeopleItemBinding.container2.setOnClickListener(onClickListener);

        viewHolder.layoutPeopleItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    ArrayList<ArrayList<Model>> handleList(ArrayList<Model> models){
        ArrayList<ArrayList<Model>> lists = new ArrayList<>();
        while(models.size()>=3){
            ArrayList<Model> temp = new ArrayList<>(models.subList(0,3));
            lists.add(temp);
            models.removeAll(temp);
        }
        lists.add(models);
        return lists;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPeopleItemBinding layoutPeopleItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPeopleItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
