package com.example.tuananh.module1.Model;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

public class Model {
    int id;
    String name,prefix,dispRela;

    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Model() {
        this.prefix="";
        this.dispRela="";
    }

    public Model(int id, String name, String prefix, String dispRela) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.dispRela = dispRela;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDispRela() {
        return dispRela;
    }

    public void setDispRela(String dispRela) {
        this.dispRela = dispRela;
    }

    public static int createId(){
        Calendar c = Calendar.getInstance();
        int id = (int) c.getTimeInMillis();
        return id;
    }

    public String getDisplayName(){
        if (prefix==null){
            return name;
        }
        else return prefix+" "+name;
    }

    public Boolean isEmpty(){
        if (id!=0) return false;
        if (name!=null || name.trim().length()>0) return false;
        if (prefix!=null || prefix.trim().length()>0) return false;
        if (dispRela!=null || dispRela.trim().length()>0) return false;

        return true;
    }
}
