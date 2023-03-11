package com.example.trenes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trenes.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    public static String lastToken;

    public static boolean TokenObtenido=false;
    public RecyclerView recycler_viewLineas;
    public AdapterLineas adapterLineas=new AdapterLineas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recycler_viewLineas=findViewById(R.id.recycler_viewLineas);
        recycler_viewLineas.setLayoutManager(new LinearLayoutManager(this));
        recycler_viewLineas.setAdapter(adapterLineas);
        String userApi = getUserApi();
        String codificar = codificar(userApi);
        //-->   Voy a pedir la ubicacion y la guardo para mas adelante  <--
        /*
         *  Permiso indicado en manifest -Listo
         *  Guardo en mi objeto de control General La ubicacion para no solicitarla nuevamente
         *  Solicito la ubicacion en tiempo de ejecucion
         */
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_LOCATION_PERMISSION);}
        View VistaRaiz = findViewById(android.R.id.content);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            General.localizacion=location;
                            Snackbar.make(VistaRaiz, "Este es un Snackbar", Snackbar.LENGTH_SHORT).show();

                        }
                        else{
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
                        adapterLineas.setData(response.body());
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
    String getUserApi() {
        Date date = new Date();
        return Base64.encodeToString((new SimpleDateFormat("yyyyMMdd").format(date) + "sofse").getBytes(), 2);
    }
    String codificar(String str) {
        String stringBuffer = new StringBuffer(Base64.encodeToString(new StringBuffer(Base64.encodeToString(str.getBytes(), 2).replace("a", "#t").replace("e", "#x").replace("i", "#f").replace("o", "#l").replace("u", "#7").replace("=", "#g")).reverse().toString().getBytes(), 2).replace("a", "#j").replace("e", "#p").replace("i", "#w").replace("o", "#8").replace("u", "#0").replace("=", "#v")).reverse().toString();
        try {
            return URLEncoder.encode(stringBuffer, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            return stringBuffer;
        }
    }
}