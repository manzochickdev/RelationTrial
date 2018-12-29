package com.example.tuananh.module1.RelationView;

import com.example.tuananh.module1.Model.Model;

import java.util.ArrayList;

public class ModelView {
    public ArrayList<Model> models;
    public ViewHandle viewHandle;
    public int root = -1;

    public ModelView(ArrayList<Model> models, ViewHandle viewHandle) {
        this.models = models;
        this.viewHandle = viewHandle;
    }
}
