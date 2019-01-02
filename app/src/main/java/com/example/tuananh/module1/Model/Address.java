package com.example.tuananh.module1.Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.android.gms.maps.model.LatLng;

public class Address extends BaseObservable {
    String addrText;
    LatLng addrLatlng;

    public Address(String addrText, LatLng addrLatlng) {
        this.addrText = addrText;
        this.addrLatlng = addrLatlng;
    }

    public Address() {
    }

    @Bindable
    public String getAddrText() {
        return addrText;
    }

    public LatLng getAddrLatlng() {
        return addrLatlng;
    }

    public void setAddrText(String addrText) {
        this.addrText = addrText;
        notifyPropertyChanged(BR.addrText);
    }

    public void setAddrLatlng(LatLng addrLatlng) {
        this.addrLatlng = addrLatlng;
    }

    public Boolean isEmpty(){
        if (addrText!= null || addrText.trim().length()>0) return false;
        if (addrLatlng!=null) return false;
        return true;
    }
}
