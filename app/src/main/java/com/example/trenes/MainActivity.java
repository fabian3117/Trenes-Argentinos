package com.example.trenes;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.example.trenes.Apis.AdapterLineas;
import com.example.trenes.Apis.Alerta;
import com.example.trenes.Apis.AlertaResponse;
import com.example.trenes.Apis.General;
import com.example.trenes.Apis.LineaSimple;
import com.example.trenes.Apis.TokenRequest;
import com.example.trenes.Apis.TokenResponse;
import com.example.trenes.Apis.TrenesApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trenes.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public FrameLayout FragmentPrincipal;
    public BottomNavigationView bottom_navigation;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private ActivityMainBinding binding;
    SharedPreferences sharedPreferences;
    private FusedLocationProviderClient fusedLocationClient;
    public static String lastToken;
    public String[] Array;  //-->   Aca se guardan las alertas  <--
    public static boolean TokenObtenido=false;
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try {
                General.GeneracionTokenSincronico();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        //-->   Obtencion de sharedPreferences  <--
        sharedPreferences= getSharedPreferences(General.PalabraSharedPref, Context.MODE_PRIVATE);
       // GuardarShared(14,42,General.ID_Mitre);
//    runnable.run();
        CargaShared();
        //-->   Vinculacion elementos - xml <---
        View VistaRaiz = findViewById(android.R.id.content);
        bottom_navigation=findViewById(R.id.bottom_navigation);
        FragmentPrincipal=findViewById(R.id.FragmentPrincipal);
        AdapterFragmentPrincipal fragmentPrincipal=new AdapterFragmentPrincipal();
        getSupportFragmentManager().beginTransaction().add(R.id.FragmentPrincipal,fragmentPrincipal).commit();
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle()==getResources().getString(R.string.notificaciones)){
                    //-->   Muestro el fragment de notificaciones   <--
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentPrincipal,new SecondFragment(Array)).commit();
                }
                else if(item.getTitle()==getResources().getString(R.string.horarios)){
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentPrincipal,fragmentPrincipal).commit();
                }
                return false;
            }
        });
        String userApi=General.getUserApi();
        String codificar = General.codificar(userApi);
        //-->   Voy a pedir la ubicacion y la guardo para mas adelante  <--
        /*
         *  Permiso indicado en manifest -Listo
         *  Guardo en mi objeto de control General La ubicacion para no solicitarla nuevamente
         *  Solicito la ubicacion en tiempo de ejecucion
         */
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_LOCATION_PERMISSION);}
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            General.localizacion=location;

                        }
                        else{
                            General.localizacion=null;  //-->   Para que no apunte a cualquier lado <--
                            Snackbar.make(VistaRaiz, "No Pudimos obtener tu ubicacion", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

        //-->   Tendria que simplificar este codigo para que no se vea tan feo en la forma de pedir la informacion  <--

        Retrofit retrofit = new Retrofit.Builder().baseUrl(this.getResources().getString(R.string.trenesApiUrl)).addConverterFactory(GsonConverterFactory.create()).build();
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setUsername(userApi);
        tokenRequest.setPassword(codificar);
        TrenesApi trenesApi = retrofit.create(TrenesApi.class);
        trenesApi.authorize(tokenRequest).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                lastToken=response.body().getToken();
                General.Token=lastToken;
                Log.e("TOKEN",General.Token);
                TokenObtenido=true;
                trenesApi.getLineas(lastToken).enqueue(new Callback<List<LineaSimple>>() {
                    @Override
                    public void onResponse(Call<List<LineaSimple>> call, Response<List<LineaSimple>> response) {
                        fragmentPrincipal.Datos(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<LineaSimple>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                TokenObtenido=false;
            }
        });
    }
    public void CargaShared(){
        sharedPreferences= getSharedPreferences(General.PalabraSharedPref, Context.MODE_PRIVATE);
        //-->   Obtengo las alertas que se predefinieron    <--
        String inter=sharedPreferences.getString(General.GuardadoAlarmas,"");
        //-->   Obtengo mi String[] <--
        Array=inter.split("-");
    }
    public void GuardarShared(int Hora,int Min,int ID_R){
    if(sharedPreferences==null){
    //-->   Emito una alerta al usuario <--
        Log.e("Guardado","PROBLEMA CON EL SHAREDPREFERENCE NULO");
        return;
    }
         //-->   Genero mi cadena caracteristica <--
        String H=(Hora<9)?"0"+String.valueOf(Hora):String.valueOf(Hora);
        String M=(Min<9)?"0"+String.valueOf(Min):String.valueOf(Min);
        String Valor=H+M+String.valueOf(ID_R)+General.FinDato;

        SharedPreferences.Editor edit =sharedPreferences.edit();
        if(edit==null){
            Log.e("Guardado","Problemas en la apertura del edit del sharedpreference");
            return;
        }
        String buf=sharedPreferences.getString(General.GuardadoAlarmas,"");
        buf+=Valor;
        edit.putString(General.GuardadoAlarmas,buf);
        edit.apply();

    }
}