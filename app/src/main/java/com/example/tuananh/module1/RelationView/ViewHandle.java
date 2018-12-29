package com.example.tuananh.module1.RelationView;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.tuananh.module1.BR;
import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;

import java.security.cert.CertPathValidatorException;
import java.util.ArrayList;

public class ViewHandle extends BaseObservable {
    ArrayList<Model> models;
    Context context;
    IModule3 iModule3;
    boolean isBase = true;
    int root=-1;

    public ViewHandle(Context context, IModule3 iModule3) {
        this.context = context;
        this.iModule3 = iModule3;
    }

    @Bindable
    public boolean isBase() {
        return isBase;
    }
    @Bindable
    public int getRoot() {
        return root;
    }

    public ArrayList<Model> getModels() {
        return models;
    }

    public Context getContext() {
        return context;
    }

    public IModule3 getiModule3() {
        return iModule3;
    }

    public void onModelListener(int id){
        handleRelative(id);
    }

    private void handleRelative(int id) {
        this.root = id;
        iModule3.handleUpdate(id);
        notifyPropertyChanged(BR.root);
    }
}
