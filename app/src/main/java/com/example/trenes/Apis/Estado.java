package com.example.trenes.Apis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.trenes.R;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Estado extends AppCompatActivity {
    private double Lat=0,Long=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    Log.e("TAG","ENTRO");
        setContentView(R.layout.activity_estado);
        //--> Obtengo la ubicacion actual <--
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //-->   Aca entra si no tenemos la ubiocacion   <--
        Log.e("TAG","Problemas con permisos");
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Lat=location.getLatitude();
        Long=location.getLongitude();
        General.trenesApi.buscarCercanas(General.Token,Lat,Long,100,null,null,null,null,"","").enqueue(new Callback<PaginationContainer<CercanasResponse>>() {
            @Override
            public void onResponse(Call<PaginationContainer<CercanasResponse>> call, Response<PaginationContainer<CercanasResponse>> response) {
                
            }

            @Override
            public void onFailure(Call<PaginationContainer<CercanasResponse>> call, Throwable t) {

            }
        });
    }

}