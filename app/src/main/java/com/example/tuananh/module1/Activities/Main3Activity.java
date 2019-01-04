package com.example.tuananh.module1.Activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tuananh.module1.AddEditDetail.ImagePickerFragment;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.Utils.FragPeoplePicker;
import com.example.tuananh.module1.Utils.Mode;
import com.example.tuananh.module1.Utils.PeopleSearchFragment;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity implements IMain3Activity {

    ArrayList<Model> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        testList = new ArrayList<>();
//        bundle.putString("activity","IMain3Activity");
//        ImagePickerFragment imagePickerFragment = new ImagePickerFragment();
//        imagePickerFragment.setArguments(bundle);
//        imagePickerFragment.show(getSupportFragmentManager(),imagePickerFragment.getTag());

//        PeopleSearchFragment peopleSearchFragment = new PeopleSearchFragment();
//        peopleSearchFragment.setArguments(bundle);
//        peopleSearchFragment.show(getSupportFragmentManager(),peopleSearchFragment.getTag());
        findViewById(R.id.tv_people).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> listId = new ArrayList<>();
                Bundle bundle = new Bundle();
                bundle.putString("mode", Mode.PEOPLE_SEARCH_MULTIPLE);
                for (Model m : testList){
                    listId.add(m.getId());
                }
                bundle.putIntegerArrayList("listId",listId);
                FragPeoplePicker fragPeoplePicker = new FragPeoplePicker();
                fragPeoplePicker.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container,fragPeoplePicker,"FragPeoplePicker")
                        .addToBackStack("FragPeoplePicker")
                        .commit();
            }
        });


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
        testList.remove(model);
    }

    @Override
    public void testModel() {
        testList.size();
        testList.size();
    }
}
