package com.example.tuananh.module1.Utils;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.AddEditDetail.IMain2Activity;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.PeopleSearch;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragPeoplePickerBinding;

import java.util.ArrayList;
import java.util.Arrays;


public class FragPeoplePicker extends BottomSheetDialogFragment {
    FragPeoplePickerBinding fragPeoplePickerBinding;
    ArrayList<Model> models;
    Context context;
    FragPeoplePickerAdapter fragPeoplePickerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String mode = bundle.getString("mode");
        String mRela = bundle.getString("relationship");
        int mId = bundle.getInt("id");
        ArrayList<Integer> listId = bundle.getIntegerArrayList("listId");

        context = getContext();
        fragPeoplePickerBinding = DataBindingUtil.inflate(inflater,R.layout.frag_people_picker, container, false);
        models = PeopleSearch.getInstance(context).onSearchListener(null,99);
        fragPeoplePickerAdapter = new FragPeoplePickerAdapter(models,context,mode);
        fragPeoplePickerBinding.rvPeople.setAdapter(fragPeoplePickerAdapter);
        fragPeoplePickerBinding.rvPeople.setLayoutManager(new GridLayoutManager(context,3, LinearLayoutManager.VERTICAL,false));

        if (mode.equals(Mode.PEOPLE_SEARCH_SINGLE)){
            fragPeoplePickerBinding.setIsRelaPicker(true);
            fragPeoplePickerBinding.layoutRelationshipSelect.setRelationship(new ArrayList<>(Arrays.asList(Relationship.getRelationship())));
            fragPeoplePickerBinding.layoutRelationshipSelect.setIMain2Activity((IMain2Activity)context);
            if (mRela!=null){fragPeoplePickerBinding.layoutRelationshipSelect.setClicked(Relationship.convertRelationshipDisplay(mRela));}
            else fragPeoplePickerBinding.layoutRelationshipSelect.setClicked(-1);
            if (mId!=0) {fragPeoplePickerAdapter.setsClicked(mId);} else fragPeoplePickerAdapter.setsClicked(-1);
        }
        else {
            fragPeoplePickerBinding.setIsRelaPicker(false);
            fragPeoplePickerAdapter.setmClicked(listId);
        }

        fragPeoplePickerBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                models = PeopleSearch.getInstance(context).onSearchListener(s.toString().toLowerCase(),10);
                fragPeoplePickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return fragPeoplePickerBinding.getRoot();
    }

    public void notifyRelationSelected(int clicked){
        fragPeoplePickerBinding.layoutRelationshipSelect.setClicked(clicked);
    }

}
