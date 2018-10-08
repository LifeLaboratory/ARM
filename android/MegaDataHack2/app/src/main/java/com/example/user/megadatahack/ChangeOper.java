package com.example.user.megadatahack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

public class ChangeOper extends AppCompatActivity {

    String login, password, name, session;
    int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_oper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText loginEdit = (EditText) findViewById(R.id.login);
        EditText passwordEdit = (EditText) findViewById(R.id.password);
        EditText nameEdit = (EditText) findViewById(R.id.name);
        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");
        name = getIntent().getStringExtra("name");
        id_user = getIntent().getIntExtra("id_user", 0);
        session = getIntent().getStringExtra("session");
        loginEdit.setText(login);
        passwordEdit.setText(password);
        nameEdit.setText(name);

        Button saveBtn = (Button) findViewById(R.id.sign_up_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://90.189.132.25:80/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                interOPER interOPERTmp = retrofit.create(interOPER.class);
                String dataForName = "{\"Name\": \"" + nameEdit.getText().toString() + "\", \"Session\": \"" + session + "\", \"id_user\": " + id_user + "}";
                Log.d("MegaDataHack_log", dataForName);
                interOPERTmp.saveName(dataForName).enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) { }
                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) { }
                });

                String dataForLoginPassword = "{\"Login\": \"" + loginEdit.getText().toString() + "\", \"Password\": \"" + passwordEdit.getText().toString() + "\", \"Session\": \"" + session + "\", \"id_user\": " + id_user + "}";
                Log.d("MegaDataHack_log", dataForLoginPassword);
                interOPERTmp.saveLoginPassword(dataForLoginPassword).enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) { }
                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) { }
                });
            }
        });
    }

}
