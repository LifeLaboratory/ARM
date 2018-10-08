package com.example.user.megadatahack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface interFACE {
    @GET("api/v1/auth")
    Call<Model> getSession(@Query("Session") String data);

    @PUT("api/v1/auth")
    Call<Model> put_query(@Query("data") String data);

    @POST("api/v1/auth")
    Call<Model> auth(@Query("data") String data);

    @GET("api/v1/auth")
    Call<ModelForOpers> getListOper(@Query("Session") String session, @Query("Param") String param);

    @PUT("api/v1/auth")
    Call<Model> put_status(@Query("data") String data);

    @POST("api/v1/classificator")
    Call<String> classfication(@Query("data") String data);

    @GET("api/v1/statistic")
    Call<Statistic> getStatistic(@Query("Attr") String Attr);
}
