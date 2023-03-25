package com.example.trenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.trenes.Apis.General;
import com.example.trenes.Apis.Horario;
import com.example.trenes.Apis.PaginationContainer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstacionVer extends AppCompatActivity {
//-->   Aca se ingresa posterior seleccion de Ramal-Estacion    <--
int ID_Estacion=0;
int ID_Rama=0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getIntent().putExtras(new Bundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion_ver);
        getSupportActionBar().hide();
        //-->   Obtener los datos del boundle   <--
        //-->   Verificando si es posible su obtencion  <--/*

        Bundle contenedor=getIntent().getExtras();  //-->   No me gusta como esta echo ese if con otro if Deberia reescribirlo  <--
        if(contenedor!=null){
           // finish();
            Log.e("MIRA","FALLO POR NULLO");
            ID_Estacion=contenedor.getInt("Estacion",-1);
            ID_Rama=contenedor.getInt("Rama",General.ID_Sarmiento);
            if(ID_Estacion==-1){
                //-->   Corto esta activity Se ejecuto por error    <--
                finish();
                finishAffinity();
            }
        }
        findViewById(R.id.CrearAcceso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ENTRO","ACCES");
                ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
                Intent intent = new Intent(view.getContext(), EstacionVer.class);
                Bundle bundle=new Bundle();
                bundle.putInt("Estacion",ID_Estacion);
                bundle.putInt("Rama",ID_Rama);
                intent.putExtras(bundle);
                intent.setAction(Intent.ACTION_VIEW);
                ShortcutInfo shortcut = new ShortcutInfo.Builder(view.getContext(), "id_del_acceso_directo")
                        .setShortLabel("Mi acceso directo")
                        .setLongLabel(String.valueOf(ID_Rama))
                        .setIntent(intent)
                        .build();

                // Agrega el acceso directo al lanzador de la aplicación
                shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
                Intent pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(shortcut);
                PendingIntent successCallback = PendingIntent.getBroadcast(getApplicationContext(), 0, pinnedShortcutCallbackIntent, PendingIntent.FLAG_IMMUTABLE);
                shortcutManager.requestPinShortcut(shortcut, successCallback.getIntentSender());
                Snackbar.make(view,"Creado",Snackbar.LENGTH_SHORT).show();

            }
        });

        final RecyclerView recycler_view=findViewById(R.id.ReciclerTrenesCirculaA);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
if(General.trenesApi==null){
    Log.e("ERROR","NULL");
    finishAffinity();
}
        General.trenesApi.getHorarios(General.Token,ID_Estacion).enqueue(new Callback<PaginationContainer<Horario>>() {
            @Override
            public void onResponse(Call<PaginationContainer<Horario>> call, Response<PaginationContainer<Horario>> response) {
                //-->   Aca tenemos los datos de los horarios   <--
                //-->   Tengo que verificar que haya datos quizas no ahi servicios en esa estacion  <--
                if(response.body().results==null || response.code()!=200){
                Log.e("ERROR","NULL 2");
                    return;
                }
                //-->   Tendria que buscar dentro del vector si tengo otros destinos aparte de este <--
                AdapterTrenVer adapterTrenVer=new AdapterTrenVer(response.body().results);
                recycler_view.setAdapter(adapterTrenVer);
            }

            @Override
            public void onFailure(Call<PaginationContainer<Horario>> call, Throwable t) {

            }
        });

        ExtendedFloatingActionButton floatingActionButton=findViewById(R.id.ActivaAlertas);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-->   Lanzo el servicio de alertas    <--
                Intent intent=new Intent(getApplicationContext(),PruebaServ.class);
                intent.setAction("Normal");
                Bundle tm=new Bundle();
                tm.putInt("ID_Es",ID_Estacion);
                tm.putString("ID_Ram", String.valueOf(ID_Rama));    //-->   Le doy este IDE al Servicio para poder notificarme  <--
                intent.putExtras(tm);
                startService(intent);
                Snackbar.make(view,"Te Informo", Snackbar.LENGTH_SHORT).show();
                floatingActionButton.setVisibility(View.GONE);
            }
        });
    }
}