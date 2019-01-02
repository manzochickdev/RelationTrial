package com.example.tuananh.module2.MapManipulation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tuananh.module2.IModule2;
import com.example.tuananh.module2.MapManipulation.PlaceSearch.PlaceSearchFragment;
import com.example.tuananh.module2.ModelAddress;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.FragmentMapManipulationBinding;
import com.google.android.gms.maps.model.LatLng;


public class MapManipulationFragment extends Fragment {
    FragmentMapManipulationBinding fragmentMapManipulationBinding;
    String address="";
    TextView textView;
    ModelAddress modelAddress;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMapManipulationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_manipulation, container, false);
        context = getContext();
        ViewHandle viewHandle = new ViewHandle(true,context,(IModule2)getParentFragment());
        onModeHandle(2);
        fragmentMapManipulationBinding.setViewHandle(viewHandle);
        return fragmentMapManipulationBinding.getRoot();
    }

    public void onModeHandle(int mode) {
        FrameLayout frameLayout = fragmentMapManipulationBinding.fragContainer;
        FrameLayout frameLayout1 = fragmentMapManipulationBinding.fragContainer1;
        frameLayout.removeAllViewsInLayout();
        frameLayout1.removeAllViews();
        if (mode==0){
            Bundle bundle = new Bundle();
            bundle.putString("mode","place");
            PlaceSearchFragment placeSearchFragment = new PlaceSearchFragment();
            placeSearchFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag_container,placeSearchFragment,"PlaceSearchFragment");
            fragmentTransaction.commit();
        }
        else if(mode==1){
            Bundle bundle = new Bundle();
            bundle.putString("mode","people");
            PlaceSearchFragment placeSearchFragment = new PlaceSearchFragment();
            placeSearchFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag_container,placeSearchFragment,"PlaceSearchFragment");
            fragmentTransaction.commit();
        }
        else {
            Log.d("OK", "onModeHandle: "+mode);
            textView = new TextView(context);
            textView.setText(address);
            textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(4,4,4,4);
            frameLayout.addView(textView);
        }
    }

    public void onAddressBack(String text, LatLng latLng) {
        modelAddress = new ModelAddress(text,latLng);

        this.address = text;
        if(textView!=null){
            textView.setText(text);
        }
    }

    public ModelAddress getAddress(){
        return modelAddress;
    }
}
