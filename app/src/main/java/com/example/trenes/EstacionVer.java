package com.example.trenes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EstacionVer extends AppCompatActivity {
//-->   Aca se ingresa posterior seleccion de Ramal-Estacion    <--
int ID_Estacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion_ver);
        //-->   Obtener los datos del boundle   <--
        //-->   Verificando si es posible su obtencion  <--
        ID_Estacion=getIntent().getExtras().getInt("Estacion",-1);
        if(ID_Estacion==-1){
            //-->   Corto esta activity Se ejecuto por error    <--
            finish();
        }

    }
}