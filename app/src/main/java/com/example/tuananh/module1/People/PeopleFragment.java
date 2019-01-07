package com.example.tuananh.module1.People;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.PeopleSearch;
import com.example.tuananh.module1.People.Adapter;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.Utils.Mode;
import com.example.tuananh.module1.databinding.FragmentPeopleBinding;

import java.util.ArrayList;


public class PeopleFragment extends Fragment {
    FragmentPeopleBinding fragmentPeopleBinding;
    OnDataHandle onDataHandle;
    ArrayList<Model> models;
    PeopleSearch peopleSearch;
    String mode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentPeopleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false);
        fragmentPeopleBinding.rvPeople.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        handleData();
        return fragmentPeopleBinding.getRoot();
    }

    void getData(ArrayList<Model> m){
        models = new ArrayList<>();
        models.addAll(m);
        MainAdapter mainAdapter = new MainAdapter(models,getContext());
        fragmentPeopleBinding.rvPeople.setAdapter(mainAdapter);
    }

    private void handleData() {
        onDataHandle = new OnDataHandle() {
            @Override
            public void updateList(ArrayList<Model> m) {
                getData(m);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        peopleSearch = new PeopleSearch(getContext());
        fragmentPeopleBinding.setViewHandle(new ViewHandle((IMainActivity) getContext(),onDataHandle,peopleSearch));
        getData(peopleSearch.getModels());
        if (getContext() instanceof IMainActivity){
            ((IMainActivity) getContext()).notifySelectedMenuItem(1);
        }
    }

    public interface OnDataHandle{
        void updateList(ArrayList<Model> models);
    }

}
