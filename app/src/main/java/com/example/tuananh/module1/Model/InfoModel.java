package com.example.tuananh.module1.Model;

import com.example.tuananh.module1.Model.Address;
import com.example.tuananh.module1.Model.Model;

public class InfoModel {
    private static InfoModel infoModel;
    Model model;
    Address address;
    DetailInfo detailInfo;

    public InfoModel(Model model, Address address, DetailInfo detailInfo) {
        this.model = model;
        if  (address==null) {
            this.address = new Address();
        }
        else this.address = address;

        if (this.detailInfo==null){
            this.detailInfo = new DetailInfo();
        }
        else this.detailInfo = detailInfo;
    }

    public static InfoModel getInstance(){
        infoModel = new InfoModel();
        infoModel.setModel(new Model());
        infoModel.setAddress(new Address());
        infoModel.setDetailInfo(new DetailInfo());
        return infoModel;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DetailInfo getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(DetailInfo detailInfo) {
        this.detailInfo = detailInfo;
    }

    public InfoModel() {

    }
}
