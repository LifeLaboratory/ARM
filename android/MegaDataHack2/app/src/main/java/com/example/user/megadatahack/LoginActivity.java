package com.example.user.megadatahack;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;
    public AutoCompleteTextView EmailView;// = findViewById(R.id.email);
    public EditText PasswordView; //= findViewById(R.id.password);
    private View ProgressView;
    private View LoginFormView;
    private interFACE InterFACE;
    public String ses;
    private Button ok_button; //= findViewById(R.id.email_sign_in_button);
    private View.OnClickListener OKListener = new View.OnClickListener() {
        public void onClick(View v) {
            String temp = "{\"Login\": \"" + EmailView.getText() +"\", \"Password\": \"" + PasswordView.getText()+ "\"}";
            InterFACE.auth(temp).enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    if(response.body().getAnswer().equals("Success")){
                        ses = response.body().getData().getsession();
                        if(response.body().getData().getType().equals("Company")){
                    //описать переключение на интерфейс компании

                            Toast.makeText(getApplicationContext(), "I'm ok", LENGTH_LONG).show();
                        }
                        else {
                         //переключение на страницу чатов
                        }
                    }
                    else Toast.makeText(getApplicationContext(),"Неверный логин или пароль",LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
                }
            });
        }
    };

    private View.OnClickListener REGListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, Company_register.class);
            startActivity(intent);
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EmailView = findViewById(R.id.email);
        PasswordView = findViewById(R.id.password);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://90.189.132.25:13451/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        InterFACE = retrofit.create(interFACE.class); //Создаем объект, при помощи которого будем выполнять запросы
        Button ok_button = findViewById(R.id.email_sign_in_button);
        ok_button.setOnClickListener(OKListener);
        Button reg_but = findViewById(R.id.email_sign_up_button);
        reg_but.setOnClickListener(REGListener);
    }
}

