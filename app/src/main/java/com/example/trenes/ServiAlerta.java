package com.example.trenes;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.trenes.Apis.AlertaResponse;
import com.example.trenes.Apis.General;
import com.example.trenes.Apis.TokenRequest;
import com.example.trenes.Apis.TokenResponse;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiAlerta extends Service {
    private static final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID ="100" ;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private static Context context;
    public int Hora=8,Minuto=0,Ramal=0;
    public ServiAlerta() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        ObtencionDatosHorarioLinea();   //-->   Si los datos en el sharedpreference estan mal por X motivo finalizare esto.
       Hora=14;
       Minuto=54;
       Ramal=General.ID_Sarmiento;
        if(Hora==-1||Minuto==-1||Ramal==-1){
            Log.e("MIRA","ERROR NOTIFICACIONES");
            System.exit(0); //-->   Fuerzo la finalizacion a la fuerza  <--

        }
        else{
            Log.e("MIRA","ENTRO");
        }

    }
    private void showNotification(String Estado,String Ramal) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.campana)
                .setContentTitle(Ramal)
                .setContentText(Estado)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1, builder.build());
        startForeground(1, builder.build());
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "My Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int salida=super.onStartCommand(intent, flags, startId);
        createNotificationChannel();

        TokenRequest tokenRequest=new TokenRequest();
       // Context context=this;
        tokenRequest.setUsername(General.getUserApi());
        tokenRequest.setPassword(General.codificar(General.getUserApi()));
        General.trenesApi.authorize(tokenRequest).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                General.Token=response.body().getToken();
                General.trenesApi.getAlertas(General.Token,General.ID_Mitre).enqueue(new Callback<AlertaResponse>() {
                    @Override
                    public void onResponse(Call<AlertaResponse> call, Response<AlertaResponse> response) {
                          Log.e(response.body().nombre,response.body().estado.getMensaje());
                          Log.e("MIRA","ENTRAMOS");
                        showNotification(response.body().estado.getMensaje(),response.body().nombre);
                    }

                    @Override
                    public void onFailure(Call<AlertaResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //-->   Problemas de conexion o algo    <--
                Log.e("SERVICO ALERTAS",call.toString());
            }
        });
        //-->   Relanzar la alerta  <--

       /* Intent intenT=new Intent(getApplicationContext(),ServiAlerta.class);
        long Intervalor=1000*30;    //-->   Cada Minuto <--
        long firstTriggerMillis = System.currentTimeMillis() + Intervalor;
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstTriggerMillis, Intervalor,pendingIntent);

        startService(intenT);*/
        return START_NOT_STICKY;
    }
    private boolean Horario(){

        return false;
    }
    void ObtencionDatosHorarioLinea(){
        //-->   Obtengo los datos de Horario de interes y linea de interez de un sharedpreference   <--
        SharedPreferences shr=getApplicationContext().getSharedPreferences("Alertas",Context.MODE_PRIVATE);
        Hora=shr.getInt("HORA",-1);
        Minuto=shr.getInt("MIN",-1);
        Ramal=shr.getInt("RAMAL",-1);
    }
}