package com.example.tuananh.module2.MapManipulation.PlaceSearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.tuananh.module2.IModule2;
import com.example.tuananh.module2.MapManipulation.PeopleSearch.SearchAdapter;
import com.example.tuananh.module2.MapManipulation.PeopleSearch.SearchHandle;
import com.example.tuananh.module2.ModelAddress;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.LayoutPeopleSearchItemBinding;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;


public class PlaceSearchFragment extends Fragment {
    String mode;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            mode= bundle.getString("mode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_search, container, false);
        Context context = getContext();
        final IModule2 iModule2 = (IModule2) getParentFragment().getParentFragment();
        final AutoCompleteTextView textView = view.findViewById(R.id.et_search);
        textView.setDropDownVerticalOffset(20);
        if (mode.equals("place")){
            GeoDataClient geoDataClient = Places.getGeoDataClient(context, null);
            PlaceAutocompleteAdapter placeAutocompleteAdapter = new PlaceAutocompleteAdapter(context,geoDataClient,null);
            textView.setAdapter(placeAutocompleteAdapter);
        }
        else {
            SearchAdapter searchAdapter = new SearchAdapter(context);
            textView.setAdapter(searchAdapter);
        }
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mode.equals("place")){
                    String data = textView.getText().toString();
                    iModule2.moveCamera(data);
                }
                else {
                    LayoutPeopleSearchItemBinding layoutPeopleSearchItemBinding = DataBindingUtil.bind(view);
                    ModelAddress modelAddress = layoutPeopleSearchItemBinding.getModelAddress();
                    iModule2.onAddressBack(modelAddress.address,modelAddress.latLng);
                    iModule2.moveCamera(modelAddress.latLng,modelAddress.address);
                }
            }
        });

        return view;
    }
}
