package com.example.trenes.Apis;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
/* loaded from: classes2.dex */
public interface TrenesApi {
    @POST("v1/auth/authorize")
    Call<TokenResponse> authorize(@Body TokenRequest tokenRequest);

    @GET("v1/estaciones/cercanas")
    Call<PaginationContainer<CercanasResponse>> buscarCercanas(@Header("Authorization") String Authorization,@Query("lat") Double d, @Query("lon") Double d2, @Query("radio") Integer num, @Query("limit") Integer num2, @Query("lineas") RetrofitArray<Integer> retrofitArray, @Query("ramales") RetrofitArray<Integer> retrofitArray2, @Query("exclude") RetrofitArray<Integer> retrofitArray3, @Query("orderBy") String str, @Query("fields") String str2);

    @GET("v1/estaciones/buscar")
    Call<PaginationContainer<Estacion>> buscarEstaciones(@Header("Authorization") String Authorization,@Query("ids") RetrofitArray<Integer> retrofitArray, @Query("lineas") RetrofitArray<Integer> retrofitArray2, @Query("ramales") RetrofitArray<Integer> retrofitArray3, @Query("exclude") RetrofitArray<Integer> retrofitArray4, @Query("limit") Integer num, @Query("orderBy") String str);

    //@GET("v1/estaciones/buscar")
    //Call<PaginationContainer<Estacion>> buscarEstaciones(@Query("nombre") String str, @Query("lineas") RetrofitArray<Integer> retrofitArray, @Query("ramales") RetrofitArray<Integer> retrofitArray2, @Query("exclude") RetrofitArray<Integer> retrofitArray3, @Query("limit") Integer num, @Query("orderBy") String str2);

    @GET("v1/alertas")
    Call<List<AlertaResponse>> getAlertas(@Header("Authorization") String Authorization);

    @GET("v1/lineas/{id}/alertas")
    Call<AlertaResponse> getAlertas(@Header("Authorization") String Authorization,@Path("id") int i);

//    @GET("v1/estaciones/{id}/alertas/geo")
  //  Call<BeaconResponse> getAlertasGeo(@Path("id") int i, @Query("token") String str);

    @GET("v1/alertas/viaje")
    Call<List<Alerta>> getAlertasViaje(@Header("Authorization") String Authorization,@Query("desde") Integer num, @Query("hasta") Integer num2);

    @GET("v1/estaciones/{id}")
    Call<Estacion> getEstacion(@Header("Authorization") String Authorization,@Path("id") int i);

  //  @GET("v1/estaciones/{id}/horarios/groups")
//   Call<PaginationContainer<Group<Horario>>> getGroups(@Path("id") Integer num, @Query("fields") String str, @Query("lineas") RetrofitArray<Integer> retrofitArray);

    @GET("v1/estaciones/{id}/horarios")
    Call<PaginationContainer<Horario>> getHorarios(@Header("Authorization") String Authorization,@Path("id") Integer num);
    //    Call<PaginationContainer<Horario>> getHorarios(@Header("Authorization") String Authorization,@Path("id") Integer num, @Query("hasta") Integer num2);  //-->   Incluye modificador para delimitar hasta donde ir   <--
//    @GET("v1/estaciones/{id}/horarios")
  //  Call<PaginationContainer<Horario>> getHorarios(@Header("Authorization") String Authorization,@Path("id") Integer num, @Query("hasta") Integer num2, @Query("fields") String str, @Query("lineas") RetrofitArray<Integer> retrofitArray, @Query("ramales") RetrofitArray<Integer> retrofitArray2, @Query("cabeceraFinal") RetrofitArray<Integer> retrofitArray3, @Query("servicio") Integer num3, @Query("limit") Integer num4);

/*    @GET("v1/estaciones/{id}/horarios")
    Call<PaginationContainer<Horario>> getItinerario(@Path("id") Integer num, @Query("hasta") Integer num2, @Query("fecha") String str, @Query("tipo") String str2, @Query("servicio") Integer num3, @Query("fields") String str3);
    @GET("v1/ramales")
    Call<PaginationContainer<RamalDetalle>> getRamales(@Query("ids") RetrofitArray<Integer> retrofitArray, @Query("lineas") RetrofitArray<Integer> retrofitArray2, @Query("limit") Integer num, @Query("fields") String str);
*/
    @GET("v1/lineas")
    Call<List<LineaSimple>> getLineas(@Header("Authorization") String Authorization);


    @POST("v1/reclamos")
    Call<ReclamosResponse> postReclamo(@Body ReclamosRequest reclamosRequest);

    @GET("v1/estaciones/cercanas")
    Call<PaginationContainer<CercanasResponse>> buscarCercanas(@Header("Authorization")String token,@Query("lat") double latitude, @Query("lon") double longitude,@Query("lineas") int i,@Query("rad")int rad);
}
