package com.example.tuananh.module1.AddEditDetail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.DatePicker;

import com.android.databinding.library.baseAdapters.BR;
import com.example.tuananh.module1.Model.InfoModel;

import java.util.Calendar;

public class InfoViewModel extends BaseObservable {
    InfoModel infoModel;
    Boolean expandVisible,collapseVisible;
    Boolean isAlive = true;
    Context context;

    public InfoViewModel(InfoModel infoModel,Context context,int mode) {
        this.infoModel = infoModel;
        this.context = context;
        handleMode(mode);
    }

    private void handleMode(int mode) {
        if (mode!=0){
            if(infoModel.getDetailInfo().getdDeath()>0){
                isAlive=false;
                notifyPropertyChanged(BR.alive);
            }
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
        if (!isAlive){
            infoModel.getDetailInfo().setdDeath(0);
            notifyPropertyChanged(BR.infoModel);
        }
        notifyPropertyChanged(BR.alive);
    }

    public void getBDay(){
        final Calendar c = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                c.set(i,i1,i2);
                infoModel.getDetailInfo().setdBirth(c.getTimeInMillis());
                notifyPropertyChanged(BR.infoModel);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(context,
                onDateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        if (infoModel.getDetailInfo().getdDeath()>0) dialog.getDatePicker().setMaxDate(infoModel.getDetailInfo().getdDeath());
        dialog.show();
    }

    public void getDDay(){
        final Calendar c = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                c.set(i,i1,i2);
                infoModel.getDetailInfo().setdDeath(c.getTimeInMillis());
                notifyPropertyChanged(BR.infoModel);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(context,
                onDateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        if (infoModel.getDetailInfo().getdBirth()>0) dialog.getDatePicker().setMinDate(infoModel.getDetailInfo().getdBirth());
        dialog.show();
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

    public interface OnDataListener{
        void onBDayBack();
        void onDDayBack();
    }
}
