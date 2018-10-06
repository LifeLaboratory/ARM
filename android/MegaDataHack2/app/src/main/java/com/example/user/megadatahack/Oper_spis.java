package com.example.user.megadatahack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Oper_spis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oper_spis);
        Intent intent = getIntent();
        ses = intent.getStringExtra("session");
    }
}
