package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tuananh.module1.DataBinding;
import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Address;
import com.example.tuananh.module1.Model.InfoModel;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentAddBinding;
import com.example.tuananh.module1.databinding.LayoutRelationshipBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class AddFragment extends Fragment implements View.OnClickListener {
    FragmentAddBinding fragmentAddBinding;
    int id;
    Boolean isImageProfileChange = false;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            id = bundle.getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add, container, false);
        context = getContext();
        handleInfoLayout();
        handleRelationshipLayout();
        fragmentAddBinding.ivBack.setOnClickListener(this);
        fragmentAddBinding.ivProfile.setOnClickListener(this);
        fragmentAddBinding.tvOk.setOnClickListener(this);
        return fragmentAddBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:{
                IMain2Activity iMain2Activity = (IMain2Activity) context;
                iMain2Activity.onBackListener();
            }break;
            case R.id.tv_ok :{
                Model model = fragmentAddBinding.layoutInfo.getVm().infoModel.getModel();
                if (model==null){
                    Toast.makeText(context, "Name can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = model.getName();
                if (name==null || name.trim().length()==0){
                    Toast.makeText(context, "Name can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                InfoModel infoModel = fragmentAddBinding.layoutInfo.getVm().getInfoModel();

                RelationshipAdapter adapter = (RelationshipAdapter) fragmentAddBinding.layoutRelationship.rvRelationship.getAdapter();
                ArrayList<ModelRela> modelRelas = adapter.getItemList();
                Bitmap bitmap = null;
                if (isImageProfileChange){
                    bitmap =((BitmapDrawable) fragmentAddBinding.ivProfile.getDrawable()).getBitmap();
                }
                IMain2Activity iMain2Activity = (IMain2Activity) context;
                iMain2Activity.onDataBack(infoModel,modelRelas,bitmap);
            }break;
            case R.id.ivProfile:{
                Bundle bundle = new Bundle();
                bundle.putInt("mode",0);
                bundle.putString("activity","Main2Activity");
                ImagePickerFragment imagePickerFragment = new ImagePickerFragment();
                imagePickerFragment.setArguments(bundle);
                imagePickerFragment.show(getFragmentManager(),imagePickerFragment.getTag());
            }break;
        }
    }


    void setImage(Bitmap image){
        fragmentAddBinding.ivProfile.setImageBitmap(image);
        isImageProfileChange = true;
    }

    public void setAddress(String address, LatLng latlng) {
        Address addr = fragmentAddBinding.layoutInfo.getVm().getInfoModel().getAddress();
        addr.setAddrText(address);
        addr.setAddrLatlng(latlng);
    }


    RelationshipAdapter relationshipAdapter;
    private void handleRelationshipLayout() {
        final ArrayList<ModelRela> modelRelas = new ArrayList<>();
        if (id!=-1){
            Model model = DatabaseHandle.getInstance(context).getPerson(id);
            modelRelas.add(new ModelRela(model));
        }
        else modelRelas.add(new ModelRela());
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
        relationshipAdapter = new RelationshipAdapter(modelRelas,context,onDataHandle,"add");
        fragmentAddBinding.layoutRelationship.rvRelationship.setAdapter(relationshipAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3,LinearLayoutManager.VERTICAL,false);
        fragmentAddBinding.layoutRelationship.rvRelationship.setLayoutManager(gridLayoutManager);
    }

    private void handleInfoLayout() {
        InfoModel infoModel = InfoModel.getInstance();
        fragmentAddBinding.layoutInfo.setVm(new InfoViewModel(infoModel,context,0));
        fragmentAddBinding.layoutInfo.setIsEdit(true);
        fragmentAddBinding.layoutInfo.setIMain2Activity((IMain2Activity) context);
    }

}
