package com.example.elderlycaresystem.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.elderlycaresystem.data.elderly.ElderlyInfo;
import com.example.elderlycaresystem.R;
import com.example.elderlycaresystem.util.RetroUtils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_ID = "INTENT_ID"; // static -> 다른 액티비티에서도 사용 가능
    public static final String INTENT_KEY= "INTENT_KEY";
    private String id;

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
        //String cookie = bundle.getString(INTENT_COOKIE);

        Toast.makeText(MainActivity.this, "User's ID : " + id, Toast.LENGTH_SHORT).show();

        // Adapter를 RecyclerView 객체에 설정하고, Adapter 안에 Elderly 객체들을 넣기!
        // recyclerView 객체 할당
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // RecyclerView에 Layout Manager 설정하기
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter();

        recyclerView.setAdapter(adapter);
        getElderlyList();

    }

    // TODO : LoopBack version. ( Test version : getTestList() -> getElderlyList(id) )
    private void getElderlyList() {
        // Server에서 MainActivity에 띄울 elderlyList를 받아와 View Queue에 넣기
        RetroUtils.getElderlyService(getApplicationContext()).getTestList().enqueue(new Callback<List<ElderlyInfo>>() {
            // 연결 성공시
            @Override
            public void onResponse(Call<List<ElderlyInfo>> call, Response<List<ElderlyInfo>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("Main_getList_Success", "Get Data(0).home : "+ response.body().get(0).getHomeIoT());
                    adapter.addItems(response.body());
                    adapter.notifyDataSetChanged();// 어댑터로 화면 내 데이터 새로고침
                } else {
                    int statusCode = response.code();
                    Log.d("Main_getList_Empty", "Empty Body.");
                    Toast.makeText(MainActivity.this, "Empty Body : " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ElderlyInfo>> call, Throwable t) {
                Log.d("Main_getList_Failure", "Get Failed.");
                Toast.makeText(MainActivity.this, "Error code : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : Final version
    private void getElderlyList2() {
        // Server에서 MainActivity에 띄울 elderlyList를 받아와 View Queue에 넣기
        RetroUtils.getElderlyService(getApplicationContext()).getTestList().enqueue(new Callback<List<ElderlyInfo>>() {
            // 연결 성공시
            @Override
            public void onResponse(Call<List<ElderlyInfo>> call, Response<List<ElderlyInfo>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("Main_getList_Success", "Get Data(0).home : "+ response.body().get(0).getHomeIoT());
                    adapter.addItems(response.body());
                    adapter.notifyDataSetChanged();// 어댑터로 화면 내 데이터 새로고침
                } else {
                    int statusCode = response.code();
                    Log.d("Main_getList_Empty", "Empty Body.");
                    Toast.makeText(MainActivity.this, "Empty Body : " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ElderlyInfo>> call, Throwable t) {
                Log.d("Main_getList_Failure", "Get Failed.");
                Toast.makeText(MainActivity.this, "Error code : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        logoutService();
    }

    // TODO : 액티비티로 돌아올 때마다 데이터 갱신
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

    private void logoutService() {
        RetroUtils.getElderlyService(getApplicationContext()).logout().enqueue(new Callback<ResponseBody>() {
            // 연결 성공시
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("Main_logout_Success", "Get Success.");
                }else {
                    int statusCode  = response.code();
                    Log.d("Main_logout_Success", "Empty Body.");
                    Toast.makeText(MainActivity.this, "Error code : " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Main_logout_Failure", "Get Failed.");
                Toast.makeText(MainActivity.this, "Error code : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}