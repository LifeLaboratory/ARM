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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;


public class Oper_register extends AppCompatActivity {
    private EditText pass;
    private AutoCompleteTextView login;
    private AutoCompleteTextView name;
    private Button signup;
    private EditText repass;
    private interFACE InterFace;
    private Retrofit retrofit;
    public String ses;
    private View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            if (pass.getText().equals(repass.getText())) {
                Toast.makeText(getApplicationContext(), "Пароли не совпадают", LENGTH_LONG).show();
                return;
            };
            String temp = "{\"Login\": \"" + login.getText() +"\", \"Password\": \"" + pass.getText()+ "\", \"Name\":\"" +name.getText()+ "\", \"Session\":\"" +ses+"\"}";
            InterFace.put_query(temp).enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    if(response.body()!= null){
                        Toast.makeText(getApplicationContext(), "Войдите под новым логином", LENGTH_LONG).show();
                        Intent intent = new Intent(Oper_register.this, LoginActivity.class);//переход не на логин а на страницу назад
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(),"Такой пользователь уже существует",LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
                }
            });

        }
    };
            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oper_register);
        Intent intent = getIntent();
        ses = intent.getStringExtra("session");
        pass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        repass = findViewById(R.id.repassword);
        signup = findViewById(R.id.sign_up_button);
        signup.setOnClickListener(listener);
        retrofit = new Retrofit.Builder()
                       .baseUrl("http://90.189.132.25/") //Базовая часть адреса
                       .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                       .build();
        InterFace = retrofit.create(interFACE.class); //Создаем объект, при помощи которого будем выполнять запросы
    }
}

