package com.example.tuananh.module1.AddEditDetail;

import android.graphics.Bitmap;

import com.example.tuananh.module1.Model.InfoModel;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module2.ModelAddress;

import java.util.ArrayList;

public interface IMain2Activity {
    void onDataBack(InfoModel infoModel, ArrayList<ModelRela> modelRela, Bitmap bitmap, ModelAddress modelAddress);
    void onPickAddress();
    void onBackListener();

    void onImageBack(Bitmap bitma1p, int mode);
    void saveBitmap(int id,Bitmap bitmap);

    void reload(int id,Boolean isEdit);

    void handleDelete(int id);

    void onSelectListener(RelaViewModel.OnDataHandle onHandler,ModelRela modelRela);
    void onRelationshipBack(int i);
    void onModelBack(Model model);

    void onSelectFinish();

    void onDataBack(InfoModel infoModel, ArrayList<ModelRela> modelRelas, Bitmap bitmap);
}
