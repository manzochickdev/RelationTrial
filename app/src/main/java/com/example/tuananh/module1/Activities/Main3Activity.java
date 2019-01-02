package com.example.tuananh.module1.Activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tuananh.module1.AddEditDetail.ImagePickerFragment;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.Utils.PeopleSearchFragment;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity implements IMain3Activity {

    ArrayList<Model> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        testList = new ArrayList<>();
        Bundle bundle = new Bundle();
//        bundle.putString("activity","IMain3Activity");
//        ImagePickerFragment imagePickerFragment = new ImagePickerFragment();
//        imagePickerFragment.setArguments(bundle);
//        imagePickerFragment.show(getSupportFragmentManager(),imagePickerFragment.getTag());
        bundle.putInt("mode",1);
        PeopleSearchFragment peopleSearchFragment = new PeopleSearchFragment();
        peopleSearchFragment.setArguments(bundle);
        peopleSearchFragment.show(getSupportFragmentManager(),peopleSearchFragment.getTag());


    }

    @Override
    public void onImageBack(Bitmap bitmap) {

    }

    @Override
    public void addModel(Model model) {
        testList.add(model);
    }

    @Override
    public void removeModel(final Model model) {
//        for (Model m : testList){
//            if (m.getId()==model.getId()){
//                testList.remove(testList.indexOf(m));
//            }
//        }
        testList.remove(model);
    }

    @Override
    public void testModel() {
        testList.size();
        testList.size();
    }
}
