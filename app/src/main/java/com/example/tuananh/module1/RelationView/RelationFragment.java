package com.example.tuananh.module1.RelationView;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentRelationBinding;
import com.example.tuananh.module1.databinding.LayoutPeopleSearchItem1Binding;

import java.util.ArrayList;


public class RelationFragment extends Fragment implements IModule3 {
    FragmentRelationBinding fragmentRelationBinding;
    int id;
    int clicked=-1;
    Context context;
    MainAdapter mainAdapter;
    DatabaseHandle databaseHandle;
    ArrayList<ModelView> modelViews;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            this.id = bundle.getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRelationBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_relation, container, false);
        context = getContext();
        databaseHandle = DatabaseHandle.getInstance(context);
        if (id!=0){
            init(id);
        }
        PeopleSearchAutocompleteAdapter peopleSearchAutocompleteAdapter = new PeopleSearchAutocompleteAdapter(context);
        fragmentRelationBinding.etSearch.setAdapter(peopleSearchAutocompleteAdapter);
        fragmentRelationBinding.etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutPeopleSearchItem1Binding layoutPeopleSearchItem1Binding = DataBindingUtil.bind(view);
                Model model = layoutPeopleSearchItem1Binding.getModel();
                init(model.getId());
            }
        });
        return fragmentRelationBinding.getRoot();
    }

    void init(int id){
        Model model = databaseHandle.getPerson(id);
        ArrayList<Model> models = new ArrayList<>();
        models.add(model);
        modelViews = new ArrayList<>();
        modelViews.add(new ModelView(models,new ViewHandle(context,this)));
        mainAdapter = new MainAdapter(modelViews,context,this);
        fragmentRelationBinding.rvPeople.setAdapter(mainAdapter);
        fragmentRelationBinding.rvPeople.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void notifyDataChange() {
        mainAdapter.discardShowSummaryInfo();
        mainAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleUpdate(int id) {
        int pos = getPosition(id)[1];

        if (modelViews.get(pos).root==id){
            getSummaryInfo(id);
            return;
        }
        updateParent(id);
        updateChild(id);
        updateSibling(id);

        pos = getPosition(id)[1];
        modelViews.get(pos).root = id;

    }

    @Override
    public void updateParent(int id) {
        int pos = getPosition(id)[1];
        ArrayList<Model> parent = databaseHandle.getChild(id,2);
        if (parent!=null){
            try{
                ModelView p = modelViews.get(pos-1);
                if (!isContain(modelViews.get(pos-1).models,parent)){
                    modelViews.set(pos-1,new ModelView(parent,new ViewHandle(context,this)));
                }
            }
            catch (Exception e){
                modelViews.add(0,new ModelView(parent,new ViewHandle(context,this)));
            }
        }
        notifyDataChange();
    }

    @Override
    public void updateChild(int id) {
        int pos = getPosition(id)[1]+1;
        ArrayList<Model> children = databaseHandle.getChild(id,0);
        if (children!=null){
            try {
                ModelView p1 = modelViews.get(pos);
                if (!isContain(modelViews.get(pos).models,children)){
                    while(pos<modelViews.size()){
                        modelViews.remove(pos);
                    }
                    modelViews.add(new ModelView(children,new ViewHandle(context,this)));
                }
            }
            catch (Exception e){
                modelViews.add(new ModelView(children,new ViewHandle(context,this)));
            }

        }
        else {
            while(pos<modelViews.size()){
                modelViews.remove(pos);
            }
        }
        notifyDataChange();
    }

    @Override
    public void updateSibling(int id) {
        int pos = getPosition(id)[1];
        ArrayList<Model> siblings = new ArrayList<>();
        ArrayList<Model> parent = databaseHandle.getChild(id,2);
        ArrayList<Model> s = databaseHandle.getChild(id,1);
        if (parent!=null){
            siblings.addAll(databaseHandle.getChild(parent.get(0).getId(),0));
        }
        if (s!=null){
            siblings.addAll(s);
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0;i<siblings.size();i++){
            for (int n=0;n<modelViews.get(pos).models.size();n++){
                if (siblings.get(i).getId()==modelViews.get(pos).models.get(n).getId()){
                    list.add(i);
                }
            }
        }

        for (int i=list.size()-1;i>=0;i--){
            siblings.remove(list.get(i));
        }

        for (Model m : siblings){
            boolean check=false;
            for (Model n : modelViews.get(pos).models){
                if (m.getId()==n.getId()){
                    check=true;
                }
            }
            if (!check){
                modelViews.get(pos).models.add(m);
            }
        }

        notifyDataChange();
    }

    private Boolean isContain(ArrayList<Model> m1,ArrayList<Model> m2){
        if (m2.size()>m1.size())return false;
        boolean[] check = new boolean[m2.size()];
        for (Model m :m2){
            for (Model n : m1){
                if (m.getId() == n.getId()){
                    check[m2.indexOf(m)]=true;
                }
            }
        }
        for (int i=0;i<check.length;i++){
            if (!check[i]) return false;
        }
        return true;
    }


    private void getSummaryInfo(int id) {
        int pos = getPosition(id)[1];
        mainAdapter.showSummaryInfo(id,pos);
    }

    private int[] getPosition(int id){
        int[] temp = new int[2];
        for (ModelView list : modelViews){
            for (Model m : list.models){
                if (m.getId() == id){
                    //column
                    temp[0] = list.models.indexOf(m);
                    //row
                    temp[1] = modelViews.indexOf(list);
                }
            }
        }
        return temp;
    }
}
