package com.example.tuananh.module1.AddEditDetail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.InfoModel;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.Utils.FragPeoplePicker;
import com.example.tuananh.module1.Utils.Mode;
import com.example.tuananh.module1.Utils.PeopleSearchFragment;
import com.example.tuananh.module2.MainActivity;
import com.example.tuananh.module2.ModelAddress;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity implements IMain2Activity {
    String mode;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mode = getIntent().getStringExtra("mode");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mode.equals("add")){
            AddFragment addFragment = new AddFragment();
            fragmentTransaction.replace(R.id.container,addFragment,"AddFragment");
        }
        else if (mode.equals("addNew")){
            Bundle bundle = new Bundle();
            id=getIntent().getIntExtra("id",-1);
            bundle.putInt("id",id);
            AddFragment addFragment = new AddFragment();
            addFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.container,addFragment,"AddFragment");
        }
        else if (mode.equals("view")) {
            id = getIntent().getIntExtra("id",-1);
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            EditFragment editFragment = new EditFragment();
            editFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.container,editFragment,"EditFragment");
        }
        else{
            //"addExisting"
            id = getIntent().getIntExtra("id",-1);
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            bundle.putBoolean("isEdit",true);
            EditFragment editFragment = new EditFragment();
            editFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.container,editFragment,"EditFragment");
        }
        fragmentTransaction.commit();

    }

    @Override
    public void onDataBack(InfoModel infoModel, ArrayList<ModelRela> modelRela, Bitmap bitmap, ModelAddress modelAddress) {
        Model model = infoModel.getModel();
        model.setId(Model.createId());
        DatabaseHandle databaseHandle = DatabaseHandle.getInstance(getBaseContext());
        databaseHandle.addPeople(model);
        if (infoModel.getDetailInfo()!=null && !infoModel.getDetailInfo().isEmpty()) databaseHandle.addDetailInfo(infoModel.getDetailInfo(),model.getId());
        if (infoModel.getAddress().getAddrText()!=null && infoModel.getAddress().getAddrLatlng()!=null){databaseHandle.addAddress(infoModel.getAddress(),model.getId());}
        if (modelRela!=null){
            for (ModelRela m : modelRela){
                if (m.relationship!=null && m.model!=null){
                    databaseHandle.addRelative(model,m.model, Relationship.convertRelationship(m.relationship));
                }
            }
        }
        if (bitmap!=null){
            saveBitmap(model.getId(),bitmap);
        }
        onBackPressed();
    }

    @Override
    public void saveBitmap(int id,Bitmap bitmap) {
        String name = Integer.toString(id);
        Context context = getBaseContext();
        File file = new File(context.getFilesDir(),name);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackListener() {
        onBackPressed();
    }

    @Override
    public void onImageBack(Bitmap bitmap, int mode) {
        if (mode==0){
            AddFragment addFragment = (AddFragment) getSupportFragmentManager().findFragmentByTag("AddFragment");
            addFragment.setImage(bitmap);
        }
        else {
            EditFragment editFragment = (EditFragment) getSupportFragmentManager().findFragmentByTag("EditFragment");
            editFragment.setImage(bitmap);
        }
    }

    @Override
    public void reload(int id, Boolean isEdit) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putBoolean("isEdit",isEdit);
        EditFragment editFragment = new EditFragment();
        editFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,editFragment,"EditFragment").commit();
    }

    @Override
    public void handleDelete(int id) {
        String name = Integer.toString(id);
        DatabaseHandle.getInstance(getBaseContext()).removePerson(id);
        getBaseContext().deleteFile(name);
        onBackPressed();
    }

    RelaViewModel.OnDataHandle onHandler;

    @Override
    public void onSelectListener(RelaViewModel.OnDataHandle onHandler,ModelRela modelRela) {
        this.onHandler = onHandler;
        Bundle bundle = new Bundle();
        if (modelRela!=null){
            if (modelRela.relationship!=null) bundle.putString("relationship",modelRela.relationship);
            if (modelRela.model!=null) bundle.putInt("id",modelRela.model.getId());
        }
        bundle.putString("mode", Mode.PEOPLE_SEARCH_SINGLE);
//        PeopleSearchFragment peopleSearchFragment = new PeopleSearchFragment();
//        peopleSearchFragment.setArguments(bundle);
//        peopleSearchFragment.show(getSupportFragmentManager(),"PeopleSearchFragment");
        FragPeoplePicker fragPeoplePicker = new FragPeoplePicker();
        fragPeoplePicker.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                add(R.id.container,fragPeoplePicker,"FragPeoplePicker")
                .addToBackStack("FragPeoplePicker")
                .commit();
    }

    @Override
    public void onSelectFinish() {
//        PeopleSearchFragment peopleSearchFragment = (PeopleSearchFragment) getSupportFragmentManager().findFragmentByTag("PeopleSearchFragment");
//        peopleSearchFragment.dismiss();
        onBackPressed();
    }

    @Override
    public void onDataBack(InfoModel infoModel, ArrayList<ModelRela> modelRelas, Bitmap bitmap) {
        Model model = infoModel.getModel();
        model.setId(Model.createId());
        DatabaseHandle databaseHandle = DatabaseHandle.getInstance(getBaseContext());
        databaseHandle.addPeople(model);

        if (infoModel.getDetailInfo()!=null && !infoModel.getDetailInfo().isEmpty()) databaseHandle.addDetailInfo(infoModel.getDetailInfo(),model.getId());
        if (infoModel.getAddress().getAddrText()!=null && infoModel.getAddress().getAddrLatlng()!=null ){databaseHandle.addAddress(infoModel.getAddress(),model.getId());}
        if (modelRelas!=null){
            for (ModelRela m : modelRelas){
                if (m.relationship!=null && m.model!=null){
                    databaseHandle.addRelative(model,m.model, Relationship.convertRelationship(m.relationship));
                }
            }
        }
        if (bitmap!=null){
            saveBitmap(model.getId(),bitmap);
        }
        onBackPressed();
    }

    @Override
    public void onBDaySet(long dDeath) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Log.d("OK", "onDateSet: "+i+"--"+i1+"--"+i2);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(getBaseContext(),
                onDateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        if (dDeath>0) dialog.getDatePicker().setMaxDate(dDeath);
        dialog.show();
    }

    @Override
    public void onDDatSet(long dDeath) {

    }

    @Override
    public void onFinish() {
        Intent intent = new Intent();
        intent.putExtra("id",id);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onRelationshipBack(int i) {
//        PeopleSearchFragment peopleSearchFragment = (PeopleSearchFragment) getSupportFragmentManager().findFragmentByTag("PeopleSearchFragment");
//        peopleSearchFragment.notifyRelationSelected(i);

        FragPeoplePicker fragPeoplePicker = (FragPeoplePicker) getSupportFragmentManager().findFragmentByTag("FragPeoplePicker");
        fragPeoplePicker.notifyRelationSelected(i);
        onHandler.onDataBack(Relationship.getRelationship()[i]);
    }

    @Override
    public void onModelBack(Model model) {
        onHandler.onDataBack(model);
    }



    @Override
    public void onPickAddress() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("mode","pick");
        startActivityForResult(intent,11335);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            switch (requestCode){
                case 11335 :
                    if (mode.equals("add") || mode.equals("addNew")){
                        AddFragment addFragment = (AddFragment) getSupportFragmentManager().findFragmentByTag("AddFragment");
                        addFragment.setAddress(data.getStringExtra("address"),new LatLng(data.getDoubleExtra("latitude",0),data.getDoubleExtra("longitude",0)));
                    }
                    else {
                        EditFragment editFragment = (EditFragment) getSupportFragmentManager().findFragmentByTag("EditFragment");
                        editFragment.setAddress(data.getStringExtra("address"),new LatLng(data.getDoubleExtra("latitude",0),data.getDoubleExtra("longitude",0)));
                    }
                    break;
            }
        }
    }

}
