package com.example.tuananh.module1.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.tuananh.module1.Activities.IMain3Activity;
import com.example.tuananh.module1.AddEditDetail.IMain2Activity;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.PeopleSearch;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentPeopleSearchBinding;

import java.util.ArrayList;
import java.util.Arrays;


public class PeopleSearchFragment extends BottomSheetDialogFragment {
    FragmentPeopleSearchBinding fragmentPeopleSearchBinding;
    PeopleSearchAdapter peopleSearchAdapter;
    ArrayList<Model> models;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        fragmentPeopleSearchBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_people_search, container, false);
        Bundle bundle = this.getArguments();
        int mode = bundle.getInt("mode");
        String rela = bundle.getString("relationship");
        int mId = bundle.getInt("id",-1);


        final PeopleSearch peopleSearch = new PeopleSearch(context);
        models = peopleSearch.onSearchListener(null,10);
        peopleSearchAdapter = new PeopleSearchAdapter(context,models,mode,null);
        fragmentPeopleSearchBinding.rvPeople.setAdapter(peopleSearchAdapter);
        fragmentPeopleSearchBinding.rvPeople.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        if(mode==0){
            fragmentPeopleSearchBinding.layoutRelationshipSelect.setRelationship(new ArrayList<>(Arrays.asList(Relationship.getRelationship())));
            fragmentPeopleSearchBinding.layoutRelationshipSelect.setIMain2Activity((IMain2Activity)context);
            fragmentPeopleSearchBinding.layoutRelationshipSelect.setClicked(-1);
        }
        else fragmentPeopleSearchBinding.layoutRelationshipSelect.getRoot().setVisibility(View.GONE);

        if (rela!=null){fragmentPeopleSearchBinding.layoutRelationshipSelect.setClicked(Relationship.convertRelationshipDisplay(rela));}
        else fragmentPeopleSearchBinding.layoutRelationshipSelect.setClicked(-1);
        if (mId!=0) {peopleSearchAdapter.setClicked(mId);} else peopleSearchAdapter.setClicked(-1);


        fragmentPeopleSearchBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                models = peopleSearch.onSearchListener(charSequence.toString(),10);
                peopleSearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        return fragmentPeopleSearchBinding.getRoot();
    }

    public void notifyRelationSelected(int clicked){
        fragmentPeopleSearchBinding.layoutRelationshipSelect.setClicked(clicked);
    }

}
