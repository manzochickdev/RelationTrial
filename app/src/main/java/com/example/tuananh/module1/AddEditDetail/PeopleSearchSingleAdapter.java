package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.People.MainAdapter;

import java.util.ArrayList;

public class PeopleSearchSingleAdapter extends MainAdapter {
    ArrayList<Model> models;
    Context context;

    public PeopleSearchSingleAdapter(ArrayList<Model> models, Context context) {
        super(models, context);
        this.models = models;
        this.context = context;
    }


}
