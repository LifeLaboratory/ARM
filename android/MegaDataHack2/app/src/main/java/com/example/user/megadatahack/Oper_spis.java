package com.example.user.megadatahack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

public class Oper_spis extends AppCompatActivity {

    ListView listView;
    OperListAdapter listAdapter;
    String ses;
    ArrayList<ModelForOpers.data> opers = new ArrayList<ModelForOpers.data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oper_spis);
        Intent intent = getIntent();
        ses = intent.getStringExtra("session");
        listView = (ListView) findViewById(R.id.oper_list);
        Log.d("MegaDataHack_log", ses);
        listAdapter = new OperListAdapter(this, opers, ses);
        listView.setAdapter(listAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://90.189.132.25:80/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        interFACE InterFACE = retrofit.create(interFACE.class); //Создаем объект, при помощи которого будем выполнять запросы

        InterFACE.getListOper(ses, "GetOperators").enqueue(new Callback<ModelForOpers>() {
            @Override
            public void onResponse(Call<ModelForOpers> call, Response<ModelForOpers> response) {
                if(response.body().getAnswer().equals("Success")){
                    for(ModelForOpers.data oper : response.body().getData()){
                        Log.d("MegaDataHack_log", oper.getName());
                        opers.add(oper);
                    }
                    listAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelForOpers> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
            }
        });
    }
}
