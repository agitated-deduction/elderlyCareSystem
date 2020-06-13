package com.example.elderlycaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {
    public String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        key = bundle.getString("KEY");
        Toast.makeText(InfoActivity.this,"사용자 아이디 : " + key,Toast.LENGTH_LONG).show();

        // TODO : 서버에 Data 요청


        // TODO : Data 보여주기



    }
}