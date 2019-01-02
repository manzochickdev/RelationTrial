package com.example.tuananh.module1.Model;

import android.content.Context;

import com.example.tuananh.module1.DatabaseHandle;

import java.util.ArrayList;

public class PeopleSearch {
    private static PeopleSearch peopleSearch;
    ArrayList<Model> models;
    ArrayList<Model> temp;

    public PeopleSearch(Context context) {
        models = DatabaseHandle.getInstance(context).showPeople();
        temp = new ArrayList<>();
    }

    public static PeopleSearch getInstance(Context context){
        if (peopleSearch==null){
            peopleSearch = new PeopleSearch(context);
        }
        return peopleSearch;
    }

    public ArrayList<Model> onSearchListener(String query,int limit){
        temp.clear();
        if  (query==null || query.length()==0){
            temp.addAll(models);
        }
        else {
            for (Model m : models){
                if (m.getName().toLowerCase().contains(query.toLowerCase())){
                    if (temp.size()<limit){
                        temp.add(m);
                    }
                }
            }
        }
        return temp;
    }

    public static void notifyDataChange(Context context){
        peopleSearch = new PeopleSearch(context);
    }

    public ArrayList<Model> getModels() {
        return models;
    }
}
