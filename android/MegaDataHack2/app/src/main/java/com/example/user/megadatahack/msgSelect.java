package com.example.user.megadatahack;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface msgSelect {
    @GET("api/v1/chat")
    Observable<ChatMessage> get(@Query("Session") String session);

    @GET("api/v1/chat")
    Observable<ChatMessage> getArchive(@Query("Session") String sesssion, @Query("Archive") String archive);

    @GET("api/v1/chat")
    Observable<ChatMessage> getAllChat(@Query("id_chat") String id_chat, @Query("Session") String session);

    @POST("api/v1/chat")
    Call<JSONObject> send(@Query("data") String data);

    @PUT("api/v1/classificator")
    Call<JSONObject> sendToDataset(@Query("data") String data);
}
