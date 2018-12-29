package com.example.tuananh.module2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DatabaseHandle extends SQLiteOpenHelper {
    private static String DB_NAME = "People.db";
    private static DatabaseHandle databaseHandle=null;

    public DatabaseHandle(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static DatabaseHandle getInstance(Context context){
        if (databaseHandle==null){
            databaseHandle = new DatabaseHandle(context);
        }
        return databaseHandle;
    }


    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public ArrayList<Model> showPeople(){
        ArrayList<Model> models = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from people";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            Model model = new Model(id,name);
            models.add(model);
        }
        return models;
    }


    public void addAddress(int id,ModelAddress address){
        String q = "insert into address values ("+id+","+address.latLng.latitude+","+address.latLng.longitude+",'"+address.address+"');";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(q);
    }

    public ModelAddress getAddress(int id){
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from address where main_id = "+id+";";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            double latitude = c.getDouble(c.getColumnIndex("latitude"));
            double longitude = c.getDouble(c.getColumnIndex("longitude"));
            String mAddress = c.getString(c.getColumnIndex("mAddress"));
            ModelAddress address = new ModelAddress(mAddress,new LatLng(latitude,longitude));
            return address;
        }
        return null;
    }


    public ArrayList<Integer> getIdFromAddress(String address){
        ArrayList<Integer> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select main_id from address where (mAddress='"+address+"');";
        Cursor c = db.rawQuery(q,null);
        while (c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("main_id"));
            list.add(id);
        }
        if (list.size()>0){
            return list;
        }
        else return null;

    }

    public String getAddressFromLatlng(LatLng latLng){
        SQLiteDatabase db = getReadableDatabase();
        String q = "select mAddress from address where (latitude="+latLng.latitude+" and longitude="+latLng.longitude+") limit 1;";
        Cursor c = db.rawQuery(q,null);
        while (c.moveToNext()){
            String mAddress = c.getString(c.getColumnIndex("mAddress"));
            return mAddress;
        }
        return null;
    }

    public ArrayList<LatLng> getAllLatlng(){
        ArrayList<LatLng> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select distinct latitude,longitude,mAddress from address;";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            double latitude = c.getDouble(c.getColumnIndex("latitude"));
            double longitude = c.getDouble(c.getColumnIndex("longitude"));
            list.add(new LatLng(latitude,longitude));
        }
        if (list.size()>0){
            return list;
        }
        else return null;
    }

    public ArrayList<ModelAddress> getAllAddress(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ModelAddress> modelAddresses = new ArrayList<>();
        String q = "select distinct latitude,longitude,mAddress from address";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            double latitude = c.getDouble(c.getColumnIndex("latitude"));
            double longitude = c.getDouble(c.getColumnIndex("longitude"));
            String mAddress = c.getString(c.getColumnIndex("mAddress"));
            modelAddresses.add(new ModelAddress(mAddress,new LatLng(latitude,longitude)));
        }
        return modelAddresses;
    }

    public ArrayList<ModelAddress> getFullAddress(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ModelAddress> modelAddresses = new ArrayList<>();
        String q = "select * from address";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("main_id"));
            double latitude = c.getDouble(c.getColumnIndex("latitude"));
            double longitude = c.getDouble(c.getColumnIndex("longitude"));
            String mAddress = c.getString(c.getColumnIndex("mAddress"));
            modelAddresses.add(new ModelAddress(id,new LatLng(latitude,longitude),mAddress));
        }
        return modelAddresses;
    }

    public String getName(int id){
        SQLiteDatabase db = getReadableDatabase();
        String q = "select name from people where id="+id+";";
        Cursor c = db.rawQuery(q,null);
        while (c.moveToNext()){
            String name = c.getString(c.getColumnIndex("name"));
            return name;
        }
        return null;
    }

    public Model getPerson(int id){
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from people where id="+id+";";
        Cursor c = db.rawQuery(q,null);
        while (c.moveToNext()){
            int mId = c.getInt(c.getColumnIndex("id"));
            String mName = c.getString(c.getColumnIndex("name"));
            return new Model(mId,mName);
        }
        return null;
    }
}
