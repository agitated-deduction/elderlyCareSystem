package com.example.elderlycaresystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public String DATA_CODE = "ElderlyData";
    public String DATA_KEY = "key";
    public ArrayList<Elderly> items;
    public String key;
    ArrayList<Elderly> elderlyArrayList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : 1. LoginActivity에서 Key(ID) 받아오기
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        key = bundle.getString("ID");
        Toast.makeText(MainActivity.this,"사용자 아이디 : " + key,Toast.LENGTH_LONG).show();


        // Adapter를 RecyclerView 객체에 설정하고, Adapter 안에 Elderly 객체들을 넣기!
        // recyclerView 객체 할당
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // RecyclerView에 Layout Manager 설정하기
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ElderlyAdapter adapter = new ElderlyAdapter();


        // TODO : 2. 서버에 데이터 요청(Key 사용)
        //  > 요청 성공( => JSON data ~> GSON data로 변환)



        // TODO : 데이터 => ArrayList<Elderly> 담기 로..
        //  > 06.10 : 임시데이터(ArrayList<Elderly>) 로 Adapter View 구성
        elderlyArrayList = new ArrayList<>();
        elderlyArrayList.add(new Elderly("chang","홍길동","010-0000-0000","양호")) ;
        elderlyArrayList.add(new Elderly("min","임꺽정","010-0000-0000","양호")) ;
        elderlyArrayList.add(new Elderly("gogo","장길산","010-0000-0000","위험")) ;
        elderlyArrayList.add(new Elderly("gugu","배철수","010-0000-0000","양호")) ;
        elderlyArrayList.add(new Elderly("hogho","김영희","010-0000-0000","주의")) ;


        // TODO : Data List -> Adapter에 넣기
        for (int position = 0; position<elderlyArrayList.size();position++){
            adapter.addItem(elderlyArrayList.get(position));
        }

        recyclerView.setAdapter(adapter);
    }


    // 어플리케이션 종료
    private long time= 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this,"뒤로 가시려면 한번 더 눌러주세요",Toast.LENGTH_LONG).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
        }
    }
}