package com.example.tuananh.module2;

import com.google.android.gms.maps.model.LatLng;

public class ModelAddress {
    public int id;
    public LatLng latLng;
    public String address;
    public String name;

    public ModelAddress(String address,LatLng latLng) {
        this.latLng = latLng;
        this.address = address;
    }

    public ModelAddress(int id, LatLng latLng, String address) {
        this.id = id;
        this.latLng = latLng;
        this.address = address;
    }

    public String getName() {
        return name;

    }

    public String getAddress() {
        return address;
    }


}
