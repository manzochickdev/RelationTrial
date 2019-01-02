package com.example.tuananh.module1.Activities;

import android.graphics.Bitmap;

import com.example.tuananh.module1.Model.Model;

public interface IMain3Activity {
    void onImageBack(Bitmap bitmap);
    
    void addModel(Model model);
    void removeModel(Model model);

    void testModel();
}
