package com.example.tuananh.module1.AddEditDetail;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.example.tuananh.module1.Model.InfoModel;

public class InfoViewModel extends BaseObservable {
    InfoModel infoModel;
    Boolean expandVisible,collapseVisible;
    Boolean isAlive = true;

    public InfoViewModel(InfoModel infoModel,int mode) {
        this.infoModel = infoModel;
        handleMode(mode);
    }

    private void handleMode(int mode) {
        if (mode!=0){
            expandVisible = false;
            collapseVisible = false;
        }
        else {
            expandVisible = true;
            collapseVisible=false;
        }
        notifyPropertyChanged(BR.expandVisible);
        notifyPropertyChanged(BR.collapseVisible);

    }

    public void expand(){
        expandVisible = false;
        collapseVisible = true;
        notifyPropertyChanged(BR.expandVisible);
        notifyPropertyChanged(BR.collapseVisible);
    }

    public void collapse(){
        expandVisible = true;
        collapseVisible = false;
        notifyPropertyChanged(BR.expandVisible);
        notifyPropertyChanged(BR.collapseVisible);
    }

    public void onCheckChange(){
        isAlive = !isAlive;
        notifyPropertyChanged(BR.alive);
    }

    @Bindable
    public Boolean getAlive() {
        return isAlive;
    }

    @Bindable
    public InfoModel getInfoModel() {
        return infoModel;
    }

    @Bindable
    public Boolean getExpandVisible() {
        return expandVisible;
    }

    @Bindable
    public Boolean getCollapseVisible() {
        return collapseVisible;
    }

    public void setInfoModel(InfoModel infoModel) {
        this.infoModel = infoModel;
    }
}
