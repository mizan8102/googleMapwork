package com.example.google_map_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

public class Geo_Map_Test extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener
        , LocationListener {
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    TextView tvlat,tvlug,tvde;
    Geocoder geo;
    List<Address> li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo__map__test);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        tvlat=(TextView)findViewById(R.id.txtlat);
        tvlug=(TextView)findViewById(R.id.txtlugt);
        tvde=(TextView)findViewById(R.id.txtdet);

        geo=new Geocoder(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        googleApiClient.disconnect();
        super.onPause();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create()
                .setInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        try {
tvlat.setText("Latitute:"+location.getLatitude());
        tvlug.setText("Longitute:"+location.getLongitude());

            li=geo.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if (li.size()>0){

                String area=li.get(0).getAddressLine(0);
                String city=li.get(0).getLocality();
                String country=li.get(0).getCountryName();
                String post=li.get(0).getPostalCode();
                tvde.setText("Location:"+area+"\nCity:"+city+"\nCountry:"+country+"\n Zip Code:"+post);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}