package com.example.user.megadatahack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CompanyInterface extends AppCompatActivity {

    String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_interface);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        session = getIntent().getStringExtra("session");

        Button toOperSpis = (Button) findViewById(R.id.oper_spis);
        toOperSpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyInterface.this, Oper_spis.class);
                intent.putExtra("session", session);
                startActivity(intent);
            }
        });

        Button toOperRegister = (Button) findViewById(R.id.oper_register);
        toOperRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyInterface.this, Oper_register.class);
                intent.putExtra("session", session);
                startActivity(intent);
            }
        });

        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyInterface.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
