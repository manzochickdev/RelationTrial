package com.example.tuananh.module1.RelationView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.PeopleSearch;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutPeopleSearchItem1Binding;

import java.util.ArrayList;
import java.util.List;


public class PeopleSearchAutocompleteAdapter extends ArrayAdapter<Model> {
    private Context context;

    public PeopleSearchAutocompleteAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return peopleFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_people_search_item_1,parent,false);
        }
        LayoutPeopleSearchItem1Binding layoutPeopleSearchItem1Binding = DataBindingUtil.bind(convertView);
        layoutPeopleSearchItem1Binding.setModel(getItem(position));
        return convertView;
    }

    private Filter peopleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            List<Model> models = new ArrayList<>();
            if (charSequence==null || charSequence.length()==0){
                models.addAll(PeopleSearch.getInstance(context).onSearchListener(null,10));
            }
            else {
                String query = charSequence.toString().trim();
                ArrayList<Model> m = PeopleSearch.getInstance(context).onSearchListener(query,10);
                models.addAll(m);
            }

            filterResults.values=models;
            filterResults.count=models.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.count>0){
                clear();
                addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Model) resultValue).getName();
        }
    };
}
