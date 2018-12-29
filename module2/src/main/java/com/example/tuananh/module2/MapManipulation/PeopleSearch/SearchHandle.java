package com.example.tuananh.module2.MapManipulation.PeopleSearch;

import android.content.Context;

import com.example.tuananh.module2.DatabaseHandle;
import com.example.tuananh.module2.Model;
import com.example.tuananh.module2.ModelAddress;

import java.util.ArrayList;

public class SearchHandle {
    private static SearchHandle searchHandle;
    private ArrayList<ModelAddress> models;
    private ArrayList<ModelAddress> temp;

    public SearchHandle(Context context) {
        DatabaseHandle databaseHandle = DatabaseHandle.getInstance(context);
        models = databaseHandle.getFullAddress();
        for (ModelAddress m : models){
            String name = databaseHandle.getName(m.id);
            m.name = name;
        }
        temp = new ArrayList<>();
    }

    static public SearchHandle getInstance(Context context){
        if (searchHandle==null){
            searchHandle = new SearchHandle(context);
        }
        return searchHandle;
    }




    ArrayList<ModelAddress> search(String query){
        temp.clear();
        if (query.equals("") || query==null){
            temp.addAll(models);
        }
        else {
            for (ModelAddress m : models){
                if (m.getName().toLowerCase().contains(query.toLowerCase())){
                    temp.add(m);
                }
            }
        }
        return temp;
    }
}
