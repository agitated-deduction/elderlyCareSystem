package com.example.elderlycaresystem.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elderlycaresystem.R;
import com.example.elderlycaresystem.data.info.ElderlyData;
import com.example.elderlycaresystem.ui.map.MapActivity;
import com.example.elderlycaresystem.util.ApiUtils;

import java.io.IOException;
import java.sql.Timestamp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    ElderlyData elderlyData;
    private Timestamp measuredtime;
    private int ekey;
    private int step;
    private int pulse;
    private int stat;
    private double kcal;
    private double latitude;
    private double longitude;
    private String name;


    private TextView nameText;
    private TextView statText;
    private TextView stepText;
    private TextView pulseText;
    private TextView kcalText;
    private TextView tempText;
    private TextView humidText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        nameText = findViewById(R.id.nameText);
        statText = findViewById(R.id.statText);
        stepText = findViewById(R.id.stepText);
        pulseText = findViewById(R.id.pulseText);
        kcalText = findViewById(R.id.kcalText);
        tempText = findViewById(R.id.tempText);
        humidText = findViewById(R.id.humidText);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ekey = bundle.getInt("KEY");
        name = bundle.getString("NAME");
        latitude = 0;
        longitude = 0;
        nameText.setText(name);
        getElderlyList(ekey);

        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((latitude != 0)&&(longitude != 0)){
                    showMap(latitude,longitude);
                }
                else{
                    Toast.makeText(InfoActivity.this,"위치 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // TODO : 서버에서 사용자 List 가져오기
    private void getElderlyList(int key) {
        // Server에서 InfoActivity에 띄울 ElderlyData를 받아와 layout 구성하기
        ApiUtils.getElderlyService(getApplicationContext()).getElderlyData(key).enqueue(new Callback<ResponseBody>() { //TODO : Response body -> ElderlyData 구성하고 그걸로 ㄱㄱ!!
            // 연결 성공시
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("InfoActivity", "GET_DATA_SUCCESS");
//                    Toast.makeText(InfoActivity.this, "Data : " + response.body().toString(), Toast.LENGTH_SHORT).show();
//                    showData(response.body());
                    try {
                        Toast.makeText(InfoActivity.this, "Data : " + response.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.d("InfoActivity", "GET_DATA_SUCCESS");
                    Toast.makeText(InfoActivity.this, "Empty : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("InfoActivity", "Connect_Error");
                Toast.makeText(InfoActivity.this, "Failure : " + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showData(ElderlyData elderlyData){
        // 정상 상태일 때 -> Intent from MainActivity
        stepText.setText(elderlyData.getEstep());
        pulseText.setText(elderlyData.getEpulse());
        kcalText.setText(elderlyData.getEkcal());
        tempText.setText(elderlyData.getEstep());
        humidText.setText(elderlyData.getEstep());
        latitude = elderlyData.getLatitude();
        latitude = elderlyData.getLatitude();
    }

    private void showMap(double lati, double longi){
        Intent intent = new Intent(InfoActivity.this, MapActivity.class);
        intent.putExtra("LATITUDE",lati);
        intent.putExtra("LONGITUDE",longi);
        startActivity(intent);
    }
}