package com.example.tuananh.module2.MapManipulation;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.tuananh.module2.BR;
import com.example.tuananh.module2.IModule2;

public class ViewHandle extends BaseObservable {
    boolean isSearch;
    IModule2 iModule2;
    Context context;
    int mode=2;

    public ViewHandle(boolean isSearch, Context context,IModule2 iModule2) {
        this.isSearch = isSearch;
        this.context = context;
        this.iModule2 = iModule2;
    }

    public Context getContext() {
        return context;
    }

    @Bindable
    public int getMode() {
        return mode;
    }

    @Bindable
    public boolean isSearch() {
        return isSearch;
    }
    public void onSearchMode(){
        this.isSearch = !this.isSearch;
        notifyPropertyChanged(BR.search);
    }

    public void onModeChange(int mode){
        this.mode = mode;
//        switch (mode){
//            case 0:
//                break;
//            case 2:
//                iModule2.getCurrentLocation();
//                break;
//            case 3:
//                iModule2.getPinnedLocation();
//                break;
//        }
        iModule2.onModeHandle(mode);
        notifyPropertyChanged(BR.mode);

    }


}
