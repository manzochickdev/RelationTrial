package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Address;
import com.example.tuananh.module1.Model.DetailInfo;
import com.example.tuananh.module1.Model.InfoModel;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutInfoBinding;
import com.example.tuananh.module1.databinding.LayoutRelationshipBinding;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {
    Context context;
    int id;
    View layoutInfo;
    View layoutRelationship;
    Boolean isEdit;
    ArrayList<ModelRela> input;
    DatabaseHandle databaseHandle;


    public CustomPagerAdapter(Context context, int id, Boolean isEdit) {
        this.context = context;
        this.id = id;
        this.isEdit = isEdit;
        databaseHandle = DatabaseHandle.getInstance(context);
        input = databaseHandle.getAllRelative(id);
        if (input==null){
            input = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        switch (position){
            case 0:
                layoutInfo = layoutInflater.inflate(R.layout.layout_info,container,false);
                handleLayoutInfo(layoutInfo);
                container.addView(layoutInfo);
                return layoutInfo;
            case 1:
                layoutRelationship = layoutInflater.inflate(R.layout.layout_relationship,container,false);
                handleLayoutRelationship(layoutRelationship);
                container.addView(layoutRelationship);
                return layoutRelationship;
        }
        return null;
    }

    private void handleLayoutInfo(View view) {
        LayoutInfoBinding layoutInfoBinding = DataBindingUtil.bind(view);
        layoutInfoBinding.setIsEdit(isEdit);
        Model model = databaseHandle.getPerson(id);
        Address address = databaseHandle.getAddr(id);
        DetailInfo detailInfo = databaseHandle.getDetailInfo(id);
        InfoModel infoModel = new InfoModel(model,address,detailInfo);
        layoutInfoBinding.setVm(new InfoViewModel(infoModel,context,1));
        layoutInfoBinding.setIMain2Activity((IMain2Activity) context);
    }

    RelationshipAdapter relationshipAdapter;
    ArrayList<ModelRela> modelRelas;
    private void handleLayoutRelationship(View view) {
        final LayoutRelationshipBinding layoutRelationship = DataBindingUtil.bind(view);
        modelRelas = new ArrayList<>();
        modelRelas.addAll(input);
        if (modelRelas==null){
            modelRelas = new ArrayList<>();
        }
        OnDataHandle onDataHandle = new OnDataHandle() {
            @Override
            public void addNewRelationship() {
                modelRelas.add(new ModelRela());
                relationshipAdapter.notifyItemInserted(modelRelas.size()-1);
            }

            @Override
            public void cancelAddRelationship() {
                int position = modelRelas.size()-1;
                modelRelas.set(position,new ModelRela());
                relationshipAdapter.notifyItemChanged(position);
            }

            @Override
            public void onRemove(int position) {
                int pos =-1;
                for (ModelRela m : modelRelas){
                    if (m.model!=null&&m.relationship!=null){
                        if (m.model.getId()==position){
                            pos = modelRelas.indexOf(m);
                        }
                    }
                }
                modelRelas.remove(pos);
                relationshipAdapter.notifyItemRemoved(pos);
            }
        };
        relationshipAdapter = new RelationshipAdapter(modelRelas,context,onDataHandle,"edit");
        if (isEdit) relationshipAdapter.setIsEdit(true);
        layoutRelationship.rvRelationship.setAdapter(relationshipAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,4, LinearLayoutManager.VERTICAL,false);
        layoutRelationship.rvRelationship.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
            if(position==0){
                return "Info";
            }
            else return "Relationship";
    }

    public View onViewBack(final int pos){
        switch (pos){
            case 0:
                return layoutInfo;
            case 1:
                return layoutRelationship;
            default:return null;
        }
    }

    public void handleMode(final boolean b){
        LayoutRelationshipBinding layoutRelationshipBinding = DataBindingUtil.bind(layoutRelationship);
        RelationshipAdapter relationshipAdapter = (RelationshipAdapter) layoutRelationshipBinding.rvRelationship.getAdapter();
        relationshipAdapter.setIsEdit(b);


        LayoutInfoBinding layoutInfoBinding = DataBindingUtil.bind(layoutInfo);
        layoutInfoBinding.setIsEdit(b);
    }

    public void handleUpdate(){
        LayoutInfoBinding layoutInfoBinding = DataBindingUtil.bind(layoutInfo);
        InfoModel infoModel = layoutInfoBinding.getVm().getInfoModel();
        Model model = infoModel.getModel();

        Address address = infoModel.getAddress();
        if (address!=null && !address.isEmpty()){
            if (databaseHandle.getAddr(id)==null){
                databaseHandle.addAddress(address,id);
            }
            else databaseHandle.updateAddress(address,id);
        }
        DetailInfo detailInfo = infoModel.getDetailInfo();
        if (detailInfo!=null && !detailInfo.isEmpty()) databaseHandle.updateDetailInfo(detailInfo,id);

        LayoutRelationshipBinding layoutRelationshipBinding = DataBindingUtil.bind(layoutRelationship);
        RelationshipAdapter relationshipAdapter = (RelationshipAdapter) layoutRelationshipBinding.rvRelationship.getAdapter();
        ArrayList<ModelRela> output = relationshipAdapter.getItemList();
        ArrayList<ModelRela> temp = new ArrayList<>();
        for (int m=0;m<input.size();m++){
            for (int n=0;n<output.size();n++){
                if(input.get(m).model.getId()==output.get(n).model.getId()){
                    temp.add(input.get(m));
                }
            }
        }

        input.removeAll(temp);
        output.removeAll(temp);

        for (int i=0;i<input.size();i++){
            databaseHandle.removeRelative(id,input.get(i).model.getId());
        }

        if (model.getName()!=null && model.getName().trim().length()>0){
            databaseHandle.updatePerson(model);
            for (int i=0;i<output.size();i++){
                databaseHandle.addRelative(model,output.get(i).model, Relationship.convertRelationship(output.get(i).relationship));
            }
        }


    }

}
