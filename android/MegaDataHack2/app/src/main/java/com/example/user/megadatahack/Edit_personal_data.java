package com.example.user.megadatahack;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;


public class Edit_personal_data extends AppCompatActivity {
    private AutoCompleteTextView login;
    private AutoCompleteTextView name;
    private EditText pass;
    private Button save;
    private Button del;
    private interFACE InterFace;
    private String ses, nam, log;//принять сессию из интента
    private View.OnClickListener Saveclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String temp = "{\"Login\": \"" + login.getText() +"\", \"Password\": \"" + pass.getText()+ "\", \"Name\":\"" +name.getText()+ "\", \"Session\":\"" +ses+"\"}";
            InterFace.put_query(temp).enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    if(response.body()!= null && response.body().getAnswer().equals("Success")){
                        Toast.makeText(getApplicationContext(), "Успешно изменено", LENGTH_LONG).show();
                        Intent intent = new Intent(Edit_personal_data.this, LoginActivity.class);//переход не на логин а на страницу назад
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(),"Ошибка",LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
                }
        });
    }};

    private View.OnClickListener Delete = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "dhtvtyyj", LENGTH_LONG).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_spis);
        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        save = findViewById(R.id.save);
        del = findViewById(R.id.delete);
        save.setOnClickListener(Saveclick);
        Intent intent = getIntent();
        ses = intent.getStringExtra("session");
        log = intent.getStringExtra("login");
        nam = intent.getStringExtra("name");
        del.setOnClickListener(Delete);
    }
}

