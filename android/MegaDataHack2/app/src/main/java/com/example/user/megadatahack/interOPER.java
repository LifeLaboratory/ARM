package com.example.user.megadatahack;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface interOPER {
    @PUT("api/v1/auth")
    Call<JSONObject> saveName(@Query("data") String data);

    @PUT("api/v1/auth")
    Call<JSONObject> saveLoginPassword(@Query("data") String data);
}
