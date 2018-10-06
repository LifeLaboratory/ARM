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

public class Company_register extends AppCompatActivity {

    private Retrofit retrofit;
    private interFACE InterFACE;
    private AutoCompleteTextView login;
    private AutoCompleteTextView name;
    private AutoCompleteTextView desc;
    private EditText pass;
    private EditText repass;
    public String ses;
    private View.OnClickListener Signupclick = new View.OnClickListener() {
        public void onClick(View v) {
            if(pass.getText().equals(repass.getText())) {
                Toast.makeText(getApplicationContext(), "Пароли не совпадают", LENGTH_LONG).show();
                return;
            };
            String temp = "{\"Login\": \"" + login.getText() +"\", \"Password\": \"" + pass.getText()+ "\", \"Name\":\"" +name.getText()+ "\", \"Description\":\"" +desc.getText()+"\"}";
            InterFACE.put_query(temp).enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    if(response.body()!= null){
                        ses = response.body().getData().getsession();
                        Toast.makeText(getApplicationContext(), "Все хорошо", LENGTH_LONG).show();
                        //стартовать активити чатов уже под логином компании
                        Intent intent = new Intent(Company_register.this, LoginActivity.class);// делать переход в чаты, а не на логин
                        intent.putExtra("session", ses);
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(),"Такой пользователь уже существует",LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
                }
            });

        }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);

        login = findViewById(R.id.login);
        name = findViewById(R.id.comp_name);
        desc = findViewById(R.id.comp_desc);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.repeat_password);
        Button signup = findViewById(R.id.reg_sign_up_button);
        signup.setOnClickListener(Signupclick);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://90.189.132.25:13451/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        InterFACE = retrofit.create(interFACE.class); //Создаем объект, при помощи которого будем выполнять запросы
    }
}

