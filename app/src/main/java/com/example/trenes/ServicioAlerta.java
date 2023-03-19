package com.example.trenes;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.trenes.Apis.AlertaResponse;
import com.example.trenes.Apis.General;
import com.example.trenes.Apis.TokenRequest;
import com.example.trenes.Apis.TokenResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioAlerta extends Service {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    //-->   Tengo que utilizando este servicio Verificar las alertas de mi ramal    <--
    public int Hora=8,Minuto=0,Ramal=0;
    private static final int NOTIFICATION_ID = 143;
    @Override
    public void onCreate() {
        super.onCreate();
        //-->   Para guardar los datos de a que hora y dias se repite puedo utilizar sharedpreferences  <--
        ObtencionDatosHorarioLinea();   //-->   Si los datos en el sharedpreference estan mal por X motivo finalizare esto.
        if(Hora==-1||Minuto==-1||Ramal==-1){
            System.exit(0); //-->   Fuerzo la finalizacion a la fuerza  <--
        }
        //-->   Obtengo una instancia del servicio de manejos de alarmas    <--
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Crear un Intent para el servicio
        Intent intent = new Intent(this, ServicioAlerta.class);
        // Crear un PendingIntent para la alarma
        alarmIntent = PendingIntent.getService(this, 0, intent, 0);
        // Establecer la hora de inicio de la alarma
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Hora);
        calendar.set(Calendar.MINUTE, Minuto);
        //-->   Programo la alarma  <--
        //-->   Aca se relanza la alarma por lo tanto si opmito este codigo no se relanzaria <--

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    void ObtencionDatosHorarioLinea(){
        //-->   Obtengo los datos de Horario de interes y linea de interez de un sharedpreference   <--
        SharedPreferences shr=getApplicationContext().getSharedPreferences("Alertas",Context.MODE_PRIVATE);
        Hora=shr.getInt("HORA",-1);
        Minuto=shr.getInt("MIN",-1);
        Ramal=shr.getInt("RAMAL",-1);
    }
    void GeneracionToken(){
        //General.retrofit;
        TokenRequest tokenRequest=new TokenRequest();
        tokenRequest.setUsername(General.getUserApi());
        tokenRequest.setPassword(General.codificar(General.getUserApi()));
        General.trenesApi.authorize(tokenRequest).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                General.Token=response.body().getToken();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
            //-->   Problemas de conexion o algo    <--
                Log.e("SERVICO ALERTAS",call.toString());
                System.exit(0);
            }
        });
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //-->   Aca Consulto el estado del Ramal solicitado <--
        try {
            General.GeneracionTokenSincronico();
            //-->   Aca tengo el mensaje de la alerta   <--
            String Alerta=General.trenesApi.getAlertas(General.Token,Ramal).execute().body().estado.getMensaje();
            //-->   Pasos previos a la muestra de notificaciones   <--
            Intent intent1=new Intent(this,MainActivity.class); //-->   En caso de tocar la notificacion tenemos que abrir la aplicacion de trenes <--
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
            //-->   Ahora Muestro la notificacion en si <--
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle("Alerta trenes") //--> Título de la notificación   <--
                    .setContentText(Alerta) //-->   Contenido de la notificación <--
                    .setPriority(NotificationCompat.PRIORITY_HIGH) //-->    Prioridad de la notificación  <--
                    .setContentIntent(pendingIntent) //-->  PendingIntent   <--
                    .setAutoCancel(true);   //-->   Eliminarla al tocarla   <--
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.notify(NOTIFICATION_ID,builder.build());

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Acciones que se desean ejecutar cuando se activa la alarma
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
