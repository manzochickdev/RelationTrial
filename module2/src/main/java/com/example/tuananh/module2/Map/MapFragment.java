package com.example.tuananh.module2.Map;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tuananh.module2.DatabaseHandle;
import com.example.tuananh.module2.IModule2;
import com.example.tuananh.module2.ModelAddress;
import com.example.tuananh.module2.R;
import com.example.tuananh.module2.databinding.FragmentMapBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2895;
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 291;
    boolean mLocationPermissionGranted = false;
    FragmentMapBinding fragmentMapBinding;
    DatabaseHandle databaseHandle;
    GoogleMap googleMap;
    IModule2 iModule2;
    Context context;
    String mode;
    LatLng currentLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            mode = bundle.getString("mode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        iModule2 = (IModule2) getParentFragment();
        context = getContext();
        databaseHandle = DatabaseHandle.getInstance(context);
        handlePermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return fragmentMapBinding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //todo load view when map ready
        this.googleMap = googleMap;
        if (mode.equals("view")){
            ArrayList<ModelAddress> list = databaseHandle.getAllAddress();
            for (ModelAddress m:list){
                this.googleMap.addMarker(new MarkerOptions().position(m.latLng).title(m.address));
                this.googleMap.setOnMarkerClickListener(this);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final ArrayList<Integer> list = databaseHandle.getIdFromAddress(marker.getTitle());
        googleMap.setInfoWindowAdapter(new CustomInfoWindow(context,marker.getTitle(),list));
        marker.showInfoWindow();
        Log.d("OK", "onMarkerClick: "+list.size());
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                iModule2.onNearbyShow(list,marker.getPosition());
            }
        });
        return true;
    }

    public void moveCamera(final LatLng latLng, String address){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        if(!mode.equals("view")){
            googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
        }
    }

    public void getCurrentLocation(){
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    String data="";
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        if (addresses.size()!=0){
                            data = addresses.get(0).getAddressLine(0);
                        }
                        else data = currentLocation.latitude+","+currentLocation.longitude;
                    } catch (IOException e) {
                        data = currentLocation.latitude+","+currentLocation.longitude;
                        e.printStackTrace();
                    }
                    finally {
                        moveCamera(currentLocation, data);
                        iModule2.onAddressBack(data,currentLocation);
                    }
                }
                else Toast.makeText(context, "Get Location Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPinnedLocation(final int mod) {
        if(mod==3){
            fragmentMapBinding.ivPin.setVisibility(View.VISIBLE);
            final Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    int x = fragmentMapBinding.getRoot().getWidth() / 2;
                    int y = fragmentMapBinding.getRoot().getHeight() / 2;
                    LatLng position = googleMap.getProjection().fromScreenLocation(new Point(x, y));
                    String s = "";
                    try {
                        List<Address> addresses = geocoder.getFromLocation(position.latitude,position.longitude,1);
                        if (addresses.size()!=0){
                            s = addresses.get(0).getAddressLine(0);
                        }
                        else {
                            s = position.latitude+","+position.longitude;
                        }

                    } catch (IOException e) {
                        s = position.latitude+","+position.longitude;
                        e.printStackTrace();
                    }
                    finally {
                        iModule2.onAddressBack(s,position);
                    }

                }
            });

        }
        else {
            googleMap.setOnCameraIdleListener(null);
            fragmentMapBinding.ivPin.setVisibility(View.GONE);}
    }

    public void moveCameraByAddress(String address){
        FetchAddress fetchAddress = new FetchAddress(context);
        try {
            LatLng latLng = fetchAddress.execute(address).get();
            iModule2.onAddressBack(address,latLng);
            if (!mode.equals("view")){
                googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getDirection(LatLng latLng) {
        if  (currentLocation!=null){
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+currentLocation.latitude+","+currentLocation.longitude+"&destination="+latLng.latitude+","+latLng.longitude));
            startActivity(intent);
        }
        else Toast.makeText(context, "Get Location Error", Toast.LENGTH_SHORT).show();
    }

    public void handleMode(int mod){
        if (!mode.equals("view")){
            googleMap.clear();
        }
        getPinnedLocation(mod);
        if (mod==2){
            getCurrentLocation();
        }
    }

    public boolean isServicesOK(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);

        if(available == ConnectionResult.SUCCESS){
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, 4215);
            dialog.show();
        }else{
            Toast.makeText(context, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void handleGps(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("This application need GPS to work correctly , do you want to enable it ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent,PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void handlePermission(){
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private boolean isGpsEnabled(){
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            handleGps();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isGpsEnabled() && isServicesOK()){
            if (mLocationPermissionGranted){
                getCurrentLocation();
            }
            else handlePermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
                else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                        intent.setData(uri);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, 993);
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PERMISSIONS_REQUEST_ENABLE_GPS:{
                if (mLocationPermissionGranted){
                    getCurrentLocation();
                }
                else handlePermission();
            }
                break;
        }
    }

}
