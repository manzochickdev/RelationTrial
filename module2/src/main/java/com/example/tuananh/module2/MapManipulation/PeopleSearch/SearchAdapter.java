package com.example.tuananh.module2.MapManipulation.PeopleSearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;


import com.example.tuananh.module2.ModelAddress;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.LayoutPeopleSearchItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<ModelAddress> {
    private Context context;

    public SearchAdapter(@NonNull Context context) {
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_people_search_item,parent,false);
        }
        LayoutPeopleSearchItemBinding layoutPeopleSearchItemBinding = DataBindingUtil.bind(convertView);
        layoutPeopleSearchItemBinding.setModelAddress(getItem(position));
        return layoutPeopleSearchItemBinding.getRoot();
    }

    private Filter peopleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<ModelAddress> suggestions = new ArrayList<>();

            if (constraint==null || constraint.length()==0){
                suggestions.addAll(SearchHandle.getInstance(context).search(null));

            }
            else {
                String query = constraint.toString().toLowerCase().trim();
                ArrayList<ModelAddress> modelAddresses = SearchHandle.getInstance(context).search(query);
                suggestions.addAll(modelAddresses);
            }

            filterResults.values = suggestions;
            filterResults.count = suggestions.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count>0){
                clear();
                addAll((List) results.values);
                notifyDataSetChanged();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ModelAddress)resultValue).getName();
        }
    };
}
