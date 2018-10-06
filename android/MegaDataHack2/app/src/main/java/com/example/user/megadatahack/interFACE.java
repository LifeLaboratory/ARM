package com.example.user.megadatahack;

import android.view.Display;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface interFACE {
    @GET("api/v1/auth")
    Call<Model> getSession(@Query("data") String data);

    @PUT("api/v1/auth")
    Call<Model> put_query(@Query("data") String data);

    @POST("api/v1/auth")
    Call<Model> auth(@Query("data") String data);
}
