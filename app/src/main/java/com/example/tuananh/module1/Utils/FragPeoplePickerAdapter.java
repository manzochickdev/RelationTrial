package com.example.tuananh.module1.Utils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Activities.IMain3Activity;
import com.example.tuananh.module1.AddEditDetail.IMain2Activity;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragPeoplePickerItemBinding;

import java.util.ArrayList;

public class FragPeoplePickerAdapter extends RecyclerView.Adapter<FragPeoplePickerAdapter.ViewHolder> {
    ArrayList<Model> models;
    Context context;
    String mode;
    int sClicked;
    ArrayList<Integer> mClicked;

    public FragPeoplePickerAdapter(ArrayList<Model> models, Context context,String mode) {
        this.models = models;
        this.context = context;
        this.mode = mode;
        if (this.mClicked==null){
            this.mClicked = new ArrayList<>();
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.frag_people_picker_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Model model = models.get(i);
        viewHolder.fragPeoplePickerItemBinding.setModel(model);
        if (mode.equals(Mode.PEOPLE_SEARCH_SINGLE)){
            viewHolder.fragPeoplePickerItemBinding.setIsChecked(sClicked==model.getId());
        }
        else{
            if (mClicked.contains(model.getId())){
                viewHolder.fragPeoplePickerItemBinding.setIsChecked(true);
            }
            else viewHolder.fragPeoplePickerItemBinding.setIsChecked(false);
        }

        viewHolder.fragPeoplePickerItemBinding.peopleItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals(Mode.PEOPLE_SEARCH_SINGLE)){
                    sClicked = model.getId();
                    ((IMain2Activity) context).onModelBack(model);
                }
                else{
                    if (viewHolder.fragPeoplePickerItemBinding.getIsChecked()){
                        viewHolder.fragPeoplePickerItemBinding.setIsChecked(false);
                        mClicked.remove(mClicked.indexOf(model.getId()));
                        ((IMain3Activity) context).removeModel(model);
                    }
                    else {
                        viewHolder.fragPeoplePickerItemBinding.setIsChecked(true);
                        mClicked.add(model.getId());
                        ((IMain3Activity) context).addModel(model);
                    }
                }
                notifyDataSetChanged();
            }
        });

        viewHolder.fragPeoplePickerItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setsClicked(int sClicked) {
        this.sClicked = sClicked;
        notifyDataSetChanged();
    }

    public void setmClicked(ArrayList<Integer> mClicked) {
        this.mClicked = mClicked;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        FragPeoplePickerItemBinding fragPeoplePickerItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fragPeoplePickerItemBinding = DataBindingUtil.bind(itemView);
        }
    }

}
