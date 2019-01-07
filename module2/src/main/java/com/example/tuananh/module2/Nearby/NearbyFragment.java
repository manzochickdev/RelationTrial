package com.example.tuananh.module2.Nearby;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module2.DatabaseHandle;
import com.example.tuananh.module2.IModule2;
import com.example.tuananh.module2.IModule21;
import com.example.tuananh.module2.Model;
import com.example.tuananh.module2.ModelAddress;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.FragmentNearbyBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class NearbyFragment extends Fragment {
    DatabaseHandle databaseHandle;
    FragmentNearbyBinding fragmentNearbyBinding;
    IModule2 iModule2;
    OnDataListener onDataListener;
    Model model;
    LatLng latLng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNearbyBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_nearby, container, false);
        iModule2 = (IModule2) getParentFragment();
        databaseHandle = DatabaseHandle.getInstance(getContext());
        onDataListener = new OnDataListener() {
            @Override
            public void onModelBack(Model m) {
                model = m;
                fragmentNearbyBinding.setModel(m);
            }
        };
        fragmentNearbyBinding.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IModule21)getContext()).getDetailInfo(model.getId());
            }
        });
        fragmentNearbyBinding.tvDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IModule2) getParentFragment()).getDirection(latLng);
            }
        });
        return fragmentNearbyBinding.getRoot();
    }



    public void onAddressBack(String text, LatLng latLng){
        //showNearby(latLng);
    }

    public void showNearby(ArrayList<Integer> id,LatLng latLng) {
        this.latLng = latLng;
//        nearby.clear();
//        for(ModelAddress m : modelAddresses){
//            float[] n=new float[1];
//            try{
//                //todo : Ok , set ui
//                Location.distanceBetween(latLng.latitude,latLng.longitude,m.latLng.latitude,m.latLng.longitude,n);
//                if (n[0]>=0 && n[0]<3000){
//                    String name = databaseHandle.getName(m.id);
//                    Log.d("OK", "onNearbyShow: "+name);
//                    m.name = name;
//                    nearby.add(m);
//                }
//            }
//            catch (Exception e){
//
//            }
//        }
//        adapter.notifyDataSetChanged();
        ArrayList<Model> nearby = new ArrayList<>();
        for (Integer i :id){
            String name = databaseHandle.getName(i);
            Model model = new Model(i,name);
            nearby.add(model);
        }
        Adapter adapter = new Adapter(nearby,latLng,iModule2,getContext(),onDataListener);
        fragmentNearbyBinding.rv.setAdapter(adapter);
        fragmentNearbyBinding.rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

    }

    public void hideNearby() {
        fragmentNearbyBinding.rv.setAdapter(null);
        fragmentNearbyBinding.setModel(null);
    }


    public interface OnDataListener{
        void onModelBack(Model model);
    }

}
