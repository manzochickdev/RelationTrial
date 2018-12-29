package com.example.tuananh.module1.AddEditDetail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentImagePickerBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class ImagePickerFragment extends BottomSheetDialogFragment {
    private static final int PERMISSIONS_REQUEST_CAMERA = 7644;
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 7643;
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY=7642;
    boolean mCameraPermissionGranted=false;
    boolean mStoragePermissionGranted=false;
    FragmentImagePickerBinding fragmentImagePickerBinding;
    IMain2Activity iMain2Activity;
    int mode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        mode = bundle.getInt("mode");
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentImagePickerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_image_picker,container,false);
        iMain2Activity = (IMain2Activity) getContext();
        fragmentImagePickerBinding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePermission(R.id.btnCamera);
                if (mCameraPermissionGranted){
                    pickImage(R.id.btnCamera);
                }


            }
        });

        fragmentImagePickerBinding.btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePermission(R.id.btnGallery);
                if (mStoragePermissionGranted){
                    pickImage(R.id.btnGallery);
                }


            }
        });

        fragmentImagePickerBinding.btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePermission(R.id.btnStorage);
                if (mStoragePermissionGranted){
                    pickImage(R.id.btnStorage);
                }


            }
        });
        fragmentImagePickerBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return fragmentImagePickerBinding.getRoot();
    }

    private void pickImage(int id) {
        switch (id){
            case R.id.btnCamera :{
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,9191);
            } break;
            case R.id.btnGallery:{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent.createChooser(intent,"Select"),9292);
            } break;
            case R.id.btnStorage:{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select"),9292);
            } break;
        }
    }

    private void handlePermission(int id) {
        switch (id){
            case R.id.btnCamera:{
                if (!(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(
                            new String[]{android.Manifest.permission.CAMERA},
                            PERMISSIONS_REQUEST_CAMERA);
                }
                else mCameraPermissionGranted=true;
            }
            break;
            case R.id.btnGallery :{
                if (!(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)) {
                   requestPermissions(
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY);
                }
                else mStoragePermissionGranted=true;
            }
            break;
            case R.id.btnStorage :{
                if (!(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                else mStoragePermissionGranted=true;
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_REQUEST_CAMERA :{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCameraPermissionGranted = true;
                    pickImage(R.id.btnCamera);
                }
                else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        toSetting();
                    }
                }
            }
            break;
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY :{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mStoragePermissionGranted = true;
                    pickImage(R.id.btnGallery);
                }
                else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        toSetting();
                    }
                }
            }break;
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE : {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mStoragePermissionGranted = true;
                    pickImage(R.id.btnStorage);
                }
                else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        toSetting();
                    }
                }
            }
            break;
        }
    }

    private void toSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 993);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            switch (requestCode){
                case 9191:
                    try{
                        Bundle extras = data.getExtras();
                        Bitmap bitma1p = (Bitmap) extras.get("data");
                        iMain2Activity.onImageBack(bitma1p,mode);
                        dismiss();
                    }
                    catch (Exception e){

                    }
                    finally {

                    }
                    break;
                case 9292:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                        iMain2Activity.onImageBack(bitmap,mode);
                        dismiss();
                    }
                    catch (IOException e) { }
                    finally { }
                    break;

            }
        }
    }


    private void testFileSave() {
        File file = new File(getContext().getFilesDir(),"aaaa");
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = getContext().openFileOutput("aaaa",Context.MODE_PRIVATE);
            String s = "test";
            fileOutputStream.write(s.getBytes());
            Log.d("OK", "testFileSave: ");
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream fileInputStream;
        try {
            fileInputStream = getContext().openFileInput("aaaa");
            int temp =-1;
            String result="";
            while ((temp = fileInputStream.read())!=-1){
                result+=(char) temp;
            }
            Log.d("OK", "testFileSave: "+result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
