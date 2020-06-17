package com.example.elderlycaresystem.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.elderlycaresystem.data.Elderly;
import com.example.elderlycaresystem.R;
import com.example.elderlycaresystem.data.ElderlyListResponse;
import com.example.elderlycaresystem.util.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_ID = "INTENT_ID"; // static -> 다른 액티비티에서도 사용 가능
    public static final String INTENT_NAME = "INTENT_NAME";
    public static final String INTENT_STAT = "INTENT_STAT";
    public String DATA_CODE = "ElderlyData";
    public String DATA_KEY = "key";
    private ArrayList<Elderly> items;
    private String id;
    private String name;
    private String stat;


    private long time= 0;// System Back Button time
    private MainAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : 1. LoginActivity에서 Key(ID) 받아오기
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString(INTENT_ID);
        name = bundle.getString(INTENT_NAME);
        stat = bundle.getString(INTENT_STAT);

        Toast.makeText(MainActivity.this,"사용자 아이디 : " + id,Toast.LENGTH_LONG).show();


        // Adapter를 RecyclerView 객체에 설정하고, Adapter 안에 Elderly 객체들을 넣기!
        // recyclerView 객체 할당
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // RecyclerView에 Layout Manager 설정하기
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter();


        // TODO : 2. 서버에 데이터 요청(Key 사용)
        //  > 요청 성공( => JSON data ~> GSON data로 변환)

        recyclerView.setAdapter(adapter);
    }

    // 액티비티로 돌아올 때마다 데이터 갱신
    @Override
    protected void onResume() {
        super.onResume();
        getElderlyList();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this,"뒤로 가시려면 한번 더 눌러주세요",Toast.LENGTH_LONG).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
        }
    }

    
    // TODO : 서버에서 사용자 List 가져오기
    private void getElderlyList() {
        // Server에서 MainActivity에 띄울 elderlyList를 받아와 Queue에 넣기
        ApiUtils.getElderlyService().getMainList().enqueue(new Callback<ElderlyListResponse>() {
            // 연결 성공시
            @Override
            public void onResponse(Call<ElderlyListResponse> call, Response<ElderlyListResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("MainActivity", "posts loaded from API");
                    adapter.addItems(response.body().getElderlyList());
                    adapter.notifyDataSetChanged();
                }else {
                    int statusCode  = response.code();
                    Toast.makeText(MainActivity.this, "Error code : " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ElderlyListResponse> call, Throwable t) {
            }
        });
    }
}