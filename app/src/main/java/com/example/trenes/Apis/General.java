package com.example.trenes.Apis;

import android.location.Location;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    final public static int ID_SanMartin=31;
    final public static int RadioTest=100;
    final public static String PalabraSharedPref="TrenesPreferencias";
    final public static String PedirHoraPreferencia="HORA";
    final public static String FinDato="-";
    final public static String GuardadoAlarmas="Alarmas";


    public static String getUserApi() {
        Date date = new Date();
        return Base64.encodeToString((new SimpleDateFormat("yyyyMMdd").format(date) + "sofse").getBytes(), 2);
    }
    public static String codificar(String str) {
        String stringBuffer = new StringBuffer(Base64.encodeToString(new StringBuffer(Base64.encodeToString(str.getBytes(), 2).replace("a", "#t").replace("e", "#x").replace("i", "#f").replace("o", "#l").replace("u", "#7").replace("=", "#g")).reverse().toString().getBytes(), 2).replace("a", "#j").replace("e", "#p").replace("i", "#w").replace("o", "#8").replace("u", "#0").replace("=", "#v")).reverse().toString();
        try {
            return URLEncoder.encode(stringBuffer, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            return stringBuffer;
        }
    }
    public static String QueRamalEs(int id){
        String retorno="";
        switch (id){
            case ID_Mitre:
                retorno="Mitre";
                break;
            case ID_Roca:
                retorno ="Roca";
                break;
            case ID_SanMartin:
                retorno="San martin";
                break;
            case ID_Sarmiento:
                retorno="Sarmiento";
                break;

        }
        return retorno;
    }
public static   void GeneracionToken(){
        TokenRequest tokenRequest=new TokenRequest();
        tokenRequest.setUsername(getUserApi());
        tokenRequest.setPassword(codificar(getUserApi()));
        trenesApi.authorize(tokenRequest).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                Token=response.body().getToken();

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //-->   Problemas de conexion o algo    <--
                Log.e("SERVICO ALERTAS",call.toString());
            }
        });
    }
    public static   void GeneracionTokenSincronico() throws IOException {
        TokenRequest tokenRequest=new TokenRequest();
        tokenRequest.setUsername(getUserApi());
        tokenRequest.setPassword(codificar(getUserApi()));
        Token=trenesApi.authorize(tokenRequest).execute().body().token;
    }
    public static Response<AlertaResponse> AlertaRamal(int id) throws IOException{
        return trenesApi.getAlertas(Token,id).execute();
    }

}
