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
import com.example.tuananh.module1.Model.Address;
import com.example.tuananh.module1.Model.InfoModel;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentEditBinding;
import com.example.tuananh.module1.databinding.LayoutInfoBinding;
import com.example.tuananh.module1.databinding.LayoutRelationshipBinding;
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

        if (isEdit){
            fragmentEditBinding.detailTabs.getTabAt(1).select();
            fragmentEditBinding.setVisible(false);
            fragmentEditBinding.ivProfile.setEnabled(true);
        }
        else {
            fragmentEditBinding.setVisible(true);
            fragmentEditBinding.ivProfile.setEnabled(false);
        }


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
                if (!isEdit){
                    iMain2Activity.reload(id,false);
                }
                else iMain2Activity.onBackListener();
            }break;
            case R.id.tvOk:{
                iMain2Activity.onFinish();
                customPagerAdapter.handleUpdate();
                if (isImageProfileChange){
                    Bitmap bitmap =((BitmapDrawable) fragmentEditBinding.ivProfile.getDrawable()).getBitmap();
                    iMain2Activity.saveBitmap(id,bitmap);
                }
            }break;
            case R.id.ivProfile :{
                Bundle bundle = new Bundle();
                bundle.putInt("mode",1);
                bundle.putString("activity","Main2Activity");
                ImagePickerFragment imagePickerFragment = new ImagePickerFragment();
                imagePickerFragment.setArguments(bundle);
                imagePickerFragment.show(getFragmentManager(),imagePickerFragment.getTag());
            }break;
        }
    }


    void setImage(Bitmap image){
        fragmentEditBinding.ivProfile.setImageBitmap(image);
        isImageProfileChange = true;
    }

    public void setAddress(String address, LatLng latlng) {
        View view1 = customPagerAdapter.onViewBack(0);
        LayoutInfoBinding layoutInfoBinding = DataBindingUtil.bind(view1);
        Address addr = layoutInfoBinding.getVm().getInfoModel().getAddress();
        addr.setAddrLatlng(latlng);
        addr.setAddrText(address);
}


    private void handleMode(boolean b) {
        fragmentEditBinding.ivProfile.setEnabled(b);
        customPagerAdapter.handleMode(b);
        fragmentEditBinding.setVisible(!b);
    }



}
