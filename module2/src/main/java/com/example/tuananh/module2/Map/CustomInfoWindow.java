package com.example.tuananh.module2.Map;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.tuananh.module2.Model;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.LayoutCustomInfoWindowBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    Context context;
    String address;
    ArrayList<Integer> listId;
    View mView;

    public CustomInfoWindow(Context context, String address, ArrayList<Integer> listId) {
        this.context = context;
        this.address = address;
        this.listId = listId;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_custom_info_window,null,false);
    }

    private void onRenderLayout() {
        LayoutCustomInfoWindowBinding layoutCustomInfoWindowBinding = DataBindingUtil.bind(mView);
        //todo setDataHere
        layoutCustomInfoWindowBinding.tvAddress.setText(address);

        CustomInfoWindowAdapter customInfoWindowAdapter = new CustomInfoWindowAdapter(listId,context);
        layoutCustomInfoWindowBinding.rvPeople.setAdapter(customInfoWindowAdapter);
        layoutCustomInfoWindowBinding.rvPeople.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

    }

    @Override
    public View getInfoWindow(Marker marker) {
        onRenderLayout();
        return mView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        onRenderLayout();
        return mView;
    }
}
