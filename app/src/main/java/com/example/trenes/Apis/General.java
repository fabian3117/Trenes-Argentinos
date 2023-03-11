package com.example.trenes.Apis;

import android.location.Location;

import com.example.trenes.R;
import com.google.android.gms.location.FusedLocationProviderClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class General {
    //-->   Este objeto va a tener odo lo que use de forma general para simplificarme a mi el trabajo  <--

    public static Location localizacion;
    public static String Token;
    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("https://apiarribos.sofse.gob.ar/").addConverterFactory(GsonConverterFactory.create()).build();
    public static TrenesApi trenesApi = retrofit.create(TrenesApi.class);
    final public static int ID_Sarmiento=1;
    final public static int ID_Mitre=5;
    final public static int ID_Roca=11;
    final public static int RadioTest=100;

}
