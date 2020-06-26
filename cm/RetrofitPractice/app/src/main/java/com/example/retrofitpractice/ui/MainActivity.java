package com.example.retrofitpractice.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.retrofitpractice.R;
import com.example.retrofitpractice.data.TestData;
import com.example.retrofitpractice.data.TestResponse;
import com.example.retrofitpractice.data.UserResponse;
import com.example.retrofitpractice.data.remote.ApiService;
import com.example.retrofitpractice.util.ApiUtils;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://192.168.1.29:9090/elderlycare/";
    private List<TestData> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ArrayList<TestData>();

        // 1. 로그인 데이터(id&pw) 서버로 보내고, response 받아서 처리
        ApiUtils.getApiService().Login("staff10158","staff101058").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.body() != null){
                    try {
                        Log.v("Test","Body -> " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    Log.v("Test","Body Is Empty -> " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }


    public void getOneDataTest(){
            // 김영희 데이터 파싱
        ApiUtils.getApiService().getStaff().enqueue(new Callback<UserResponse>(){
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if(response.isSuccessful() && response.body() != null){
                    Log.v("Test","Body -> " + response.body().getUemail());

                }else{
                    Log.v("Test","Body Is Empty -> " + response.body());
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("RetroFailure","RetroFailure: " + t);
            }
        });
    }




    public void getDataListTest(){

        ApiUtils.getApiService().getComments(1).enqueue(new Callback<List<TestData>>() {
            @Override
            public void onResponse(Call<List<TestData>> call, Response<List<TestData>> response) {

                if(response.isSuccessful() && response.body() != null) {
                    for(int i = 0; i< response.body().size(); i++){
                        data.add(response.body().get(i));
                    }
                    for(int i = 0; i< data.size(); i++){
                        Log.v("Test Complete!", data.get(i).getEmail().toString());
                    }
                }else{
                    Log.v("Test","No Response.body()");
                }
            }

            @Override
            public void onFailure(Call<List<TestData>> call, Throwable t) {
                Log.e("RetroFailure","RetroFailure: " + t);
            }
        });
    }

}