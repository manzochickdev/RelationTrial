package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentEditBinding;
import com.example.tuananh.module1.databinding.LayoutInfoBinding;
import com.example.tuananh.module1.databinding.LayoutRelationshipBinding;
import com.example.tuananh.module2.ModelAddress;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class EditFragment extends Fragment implements View.OnClickListener {
    FragmentEditBinding fragmentEditBinding;
    int id;
    boolean isEdit;
    CustomPagerAdapter customPagerAdapter;
    Context context;
    Boolean isImageProfileChange = false;
    IMain2Activity iMain2Activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");
        isEdit = bundle.getBoolean("isEdit",false);
        context = getContext();
        customPagerAdapter = new CustomPagerAdapter(context,id,isEdit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit, container, false);
        fragmentEditBinding.setId(id);
        iMain2Activity = (IMain2Activity) context;
        fragmentEditBinding.viewpager.setAdapter(customPagerAdapter);
        fragmentEditBinding.detailTabs.setupWithViewPager(fragmentEditBinding.viewpager);
        setupCustomTab();

        if (isEdit){
            fragmentEditBinding.detailTabs.getTabAt(1).select();
            fragmentEditBinding.setVisible(false);
            fragmentEditBinding.ivProfile.setEnabled(true);
        }
        else {
            fragmentEditBinding.setVisible(true);
            fragmentEditBinding.ivProfile.setEnabled(false);
        }


        fragmentEditBinding.toolbar.inflateMenu(R.menu.menu);
        fragmentEditBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.miEdit:
                        handleMode(true);
                        return true;
                    case R.id.miDelete:
                        iMain2Activity.handleDelete(id);
                        return true;

                    default: return EditFragment.super.onOptionsItemSelected(menuItem);

                }
            }
        });

        fragmentEditBinding.ivBack.setOnClickListener(this);
        fragmentEditBinding.tvCancel.setOnClickListener(this);
        fragmentEditBinding.tvOk.setOnClickListener(this);
        fragmentEditBinding.ivProfile.setOnClickListener(this);
        return fragmentEditBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back :{
                iMain2Activity.onBackListener();
                Log.d("OK", "onClick: ");
            }break;
            case R.id.tvCancel:{
                iMain2Activity.reload(id,isEdit);
            }break;
            case R.id.tvOk:{
                iMain2Activity.onBackListener();
                customPagerAdapter.handleUpdate();
                if (isImageProfileChange){
                    Bitmap bitmap =((BitmapDrawable) fragmentEditBinding.ivProfile.getDrawable()).getBitmap();
                    iMain2Activity.saveBitmap(id,bitmap);
                }
            }break;
            case R.id.ivProfile :{
                Bundle bundle = new Bundle();
                bundle.putInt("mode",1);
                ImagePickerFragment imagePickerFragment = new ImagePickerFragment();
                imagePickerFragment.setArguments(bundle);
                imagePickerFragment.show(getFragmentManager(),imagePickerFragment.getTag());
            }break;
        }
    }

    private void setupCustomTab() {
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_tab,null,false);
//        TextView textView = view.findViewById(R.id.tv_tab);
//
//        textView.setText("INFO");
//        textView.setGravity(Gravity.LEFT);
//        fragmentEditBinding.detailTabs.getTabAt(0).setCustomView(view);
//
//        View view1 = LayoutInflater.from(context).inflate(R.layout.layout_custom_tab,null,false);
//        TextView textView1 = view1.findViewById(R.id.tv_tab);
//        textView1.setText("RELATIONSHIP");
//        textView1.setGravity(Gravity.RIGHT);
//        fragmentEditBinding.detailTabs.getTabAt(1).setCustomView(view1);
    }

    void setImage(Bitmap image){
        fragmentEditBinding.ivProfile.setImageBitmap(image);
        isImageProfileChange = true;
    }

    public void setAddress(String address, LatLng latlng) {
        ModelAddress modelAddress = new ModelAddress(address,latlng);
        View view1 = customPagerAdapter.onViewBack(0);
        LayoutInfoBinding layoutInfoBinding = DataBindingUtil.bind(view1);
        layoutInfoBinding.setModelAddress(modelAddress);
}


    private void handleMode(boolean b) {
        fragmentEditBinding.ivProfile.setEnabled(b);
        View view = (View) customPagerAdapter.onViewBack(1);
        LayoutRelationshipBinding layoutRelationship = DataBindingUtil.bind(view);
        RelationshipAdapter relationshipAdapter = (RelationshipAdapter) layoutRelationship.rvRelationship.getAdapter();
        relationshipAdapter.setIsEdit(b);

        View view1 = customPagerAdapter.onViewBack(0);
        LayoutInfoBinding layoutInfoBinding = DataBindingUtil.bind(view1);
        layoutInfoBinding.setIsEdit(b);
        fragmentEditBinding.setVisible(!b);
    }


}
