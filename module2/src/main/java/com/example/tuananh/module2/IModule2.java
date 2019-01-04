package com.example.tuananh.module2;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface IModule2 {
    void onModeHandle(int mode);
    void moveCamera(String address);
    void onAddressBack(String text, LatLng latLng);

    void onNearbyShow(ArrayList<Integer> id,LatLng latLng);

    void moveCamera(LatLng latLng, String address);

    void getDirection(LatLng latLng);

    void hideNearby();
}
