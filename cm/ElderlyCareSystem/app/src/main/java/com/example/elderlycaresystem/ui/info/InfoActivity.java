package com.example.elderlycaresystem.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.elderlycaresystem.R;
import com.example.elderlycaresystem.data.ElderlyListResponse;
import com.example.elderlycaresystem.data.Info;
import com.example.elderlycaresystem.data.InfoResponse;
import com.example.elderlycaresystem.util.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {
    public String key;
    private String name;
    private String pulse;
    private String temp;
    private String humid;

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
    // TODO : 서버에서 사용자 List 가져오기
    private void getElderlyList() {
        // Server에서 MainActivity에 띄울 elderlyList를 받아와 Queue에 넣기
        ApiUtils.getElderlyService().getinfoList().enqueue(new Callback<InfoResponse>() {
            // 연결 성공시
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("InfoActivity", "posts loaded from API");

                }else {
                    int statusCode  = response.code();
                    Toast.makeText(InfoActivity.this, "Error code : " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {

            }
        });
    }
}