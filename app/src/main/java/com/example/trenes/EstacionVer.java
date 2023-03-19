package com.example.trenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.trenes.Apis.General;
import com.example.trenes.Apis.Horario;
import com.example.trenes.Apis.PaginationContainer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstacionVer extends AppCompatActivity {
//-->   Aca se ingresa posterior seleccion de Ramal-Estacion    <--
int ID_Estacion;
TextView Prueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion_ver);
        //-->   Obtener los datos del boundle   <--
        //-->   Verificando si es posible su obtencion  <--/*

        Bundle contenedor=getIntent().getExtras();  //-->   No me gusta como esta echo ese if con otro if Deberia reescribirlo  <--
        if(contenedor!=null){
           // finish();
            ID_Estacion=contenedor.getInt("Estacion",-1);
            if(ID_Estacion==-1){
                //-->   Corto esta activity Se ejecuto por error    <--
                finish();
                finishAffinity();
            }


        }
        final RecyclerView recycler_view=findViewById(R.id.ReciclerTrenesCirculaA);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        General.trenesApi.getHorarios(General.Token,ID_Estacion).enqueue(new Callback<PaginationContainer<Horario>>() {
            @Override
            public void onResponse(Call<PaginationContainer<Horario>> call, Response<PaginationContainer<Horario>> response) {
                //-->   Aca tenemos los datos de los horarios   <--
                //-->   Tengo que verificar que haya datos quizas no ahi servicios en esa estacion  <--
                AdapterTrenVer adapterTrenVer=new AdapterTrenVer(response.body().results);
                recycler_view.setAdapter(adapterTrenVer);
            }

            @Override
            public void onFailure(Call<PaginationContainer<Horario>> call, Throwable t) {

            }
        });

    }
}