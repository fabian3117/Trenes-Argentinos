package com.example.trenes;

import static com.example.trenes.Apis.General.trenesApi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.trenes.Apis.AlertaResponse;
import com.example.trenes.Apis.General;
import com.example.trenes.Apis.Horario;
import com.example.trenes.Apis.PaginationContainer;
import com.example.trenes.Apis.TokenRequest;
import com.example.trenes.Apis.TokenResponse;
import com.example.trenes.Apis.TrenesApi;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PruebaServ extends Service {
    private static final String CHANNEL_ID ="101" ;
    private static final String NombreCanal="Alertas";
    private  static String ID_Ram="5";
    private int ID_Estacion;
    private static String T_Rest="";
    static int Valor=0;
    private static String Color="#";
    private static String Mensaje="HOLA";
    private  Handler handler=new Handler();
    private  Runnable runnable=new Runnable() {
        @Override
        public void run() {
            showNotification("En "+T_Rest,Mensaje);
            TokenRequest tokenRequest=new TokenRequest();
            tokenRequest.setUsername(General.getUserApi());
            tokenRequest.setPassword(General.codificar(General.getUserApi()));
            trenesApi.authorize(tokenRequest).enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    General.Token=response.body().getToken();
                    trenesApi.getHorarios(General.Token,ID_Estacion).enqueue(new Callback<PaginationContainer<Horario>>() {
                        @Override
                        public void onResponse(Call<PaginationContainer<Horario>> call, Response<PaginationContainer<Horario>> response) {
                            Mensaje=String.valueOf(response.body().getResults().get(0).servicio.getDestino().getNombre());
                            Date Tiempo=response.body().results.get(0).desde.getLlegada();
                            Calendar calendar=Calendar.getInstance();
                            int difMin= Tiempo.getMinutes()-calendar.get(Calendar.MINUTE);
                            T_Rest=String.valueOf(difMin);
                        }

                        @Override
                        public void onFailure(Call<PaginationContainer<Horario>> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    //-->   Problemas de conexion o algo    <--
                    Log.e("SERVICO ALERTAS",call.toString());
                }
            });

            handler.postDelayed(this, 10 * 1000);
        }
    };
    private void showNotification(String Estado,String Ramal) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.campana)
                .setContentTitle(Ramal)
                .setContentText(Estado)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        //-->   Boton cancelar  <--
        Intent notificacionIntent=new Intent(getApplicationContext(),PruebaServ.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,notificacionIntent,PendingIntent.FLAG_IMMUTABLE);
        notificacionIntent.setAction("Anular");
        PendingIntent acceptPendingIntent=PendingIntent.getService(getApplicationContext(),0,notificacionIntent,PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent)
                .addAction(R.drawable.campana, "Aceptar", acceptPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(2, builder.build());
        startForeground(2, builder.build());
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    NombreCanal,
                    NotificationManager.IMPORTANCE_HIGH
            );
            //getApplicationContext().getSystemService()
            NotificationManager notificationManager =getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public PruebaServ() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction()==null || intent.getExtras()==null){
            stopSelf();
            //finalize();
            return START_NOT_STICKY;
        }
        createNotificationChannel();
        ID_Ram=intent.getExtras().getString("ID_Ram",String.valueOf(General.ID_Mitre));
       ID_Estacion=intent.getExtras().getInt("ID_Es",248);
        handler.postDelayed(runnable, 10 * 1000);
        switch (intent.getAction()){
            case "Anular":
                Log.e("TOCO","ANULACIOMM");
                stopSelf();
                break;
            case "Normal":
                Log.e("TOCO","NORMAL");
                //showNotification("HOLA","HS");
                break;
            case "Actualiza":
                Log.e("TOCO","Actualiza");
                //showNotification("HOLA",Mensaje);
                break;
            default:
                break;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        stopSelf();
    }
}