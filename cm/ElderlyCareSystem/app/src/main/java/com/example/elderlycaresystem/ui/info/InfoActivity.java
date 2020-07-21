package com.example.elderlycaresystem.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elderlycaresystem.ui.map.MapActivity;
import com.example.elderlycaresystem.R;
import com.example.elderlycaresystem.data.info.ElderlyData;
import com.example.elderlycaresystem.util.RetroUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    ElderlyData elderlyData;
    private String measuredtime;
    private int ekey;
    private int step;
    private int pulse;
    private int stat;
    private double kcal;
    private double latitude;
    private double longitude;
    private String name;
    private String home;
    private float temp;
    private float humid;

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
        home = bundle.getString("HOME");

        latitude = 0;
        longitude = 0;
        nameText.setText(name);


//        getElderlyList(ekey);
        getTestData();

        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("InfoActivity","mapButton Clicked. Lati: "+latitude+"longi: "+longitude);
                if ((latitude != 0)&&(longitude != 0)){
//                    showMap(latitude,longitude);
                    Intent intent = new Intent(InfoActivity.this, MapActivity.class);
                    intent.putExtra("LATITUDE",latitude);
                    intent.putExtra("LONGITUDE",longitude);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(InfoActivity.this,"위치 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button camButton = findViewById(R.id.camButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (home== null){
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(home));
                startActivity(intent);
            }
        });
}

    // TODO : LoopBack version. ( Test version : getTestData() -> getElderlyData(ekey) )
    private void getTestData() {
        // Server에서 InfoActivity에 띄울 ElderlyData를 받아와 layout 구성하기
        RetroUtils.getElderlyService(getApplicationContext()).getTestData().enqueue(new Callback<ElderlyData>() {
            // 연결 성공시
            @Override
            public void onResponse(Call<ElderlyData> call, Response<ElderlyData> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("InfoActivity", "GET_DATA_SUCCESS");
                    Toast.makeText(InfoActivity.this, "Elderly State : " + response.body().getStat(), Toast.LENGTH_SHORT).show();
                    setData(response.body());
                }else {
                    Log.d("InfoActivity", "GET_DATA_SUCCESS");
                    Toast.makeText(InfoActivity.this, "Empty Body : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ElderlyData> call, Throwable t) {
                Log.d("InfoActivity", "Connect_Error");
                Toast.makeText(InfoActivity.this, "Failure : " + call.toString(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    // TODO : Final version.
    private void getTestData2() {
        // Server에서 InfoActivity에 띄울 ElderlyData를 받아와 layout 구성하기
        RetroUtils.getElderlyService(getApplicationContext()).getElderlyData(ekey).enqueue(new Callback<ElderlyData>() {
            // 연결 성공시
            @Override
            public void onResponse(Call<ElderlyData> call, Response<ElderlyData> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("InfoActivity", "GET_DATA_SUCCESS");
                    Toast.makeText(InfoActivity.this, "Elderly State : " + response.body().getStat(), Toast.LENGTH_SHORT).show();
                    setData(response.body());
                }else {
                    Log.d("InfoActivity", "GET_DATA_SUCCESS");
                    Toast.makeText(InfoActivity.this, "Empty Body : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ElderlyData> call, Throwable t) {
                Log.d("InfoActivity", "Connect_Error");
                Toast.makeText(InfoActivity.this, "Failure : " + call.toString(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }



    private void setData(ElderlyData elderlyData){
        // 정상 상태일 때 -> Intent from MainActivity
        step = elderlyData.getEstep();
        pulse = elderlyData.getEpulse();
        kcal = elderlyData.getEkcal();
        stat = elderlyData.getStat();
        temp = elderlyData.getTemp();
        humid = elderlyData.getHumid();

        stepText.setText(String.valueOf(step));
        pulseText.setText(String.valueOf(pulse));
        kcalText.setText(String.valueOf(elderlyData.getEkcal()));
        tempText.setText(String.valueOf(temp));
        humidText.setText(String.valueOf(step));

        latitude = elderlyData.getEaltitude();
        longitude = elderlyData.getElongitude();
        Log.d("InfoActivity","SET lati:"+latitude+", longi:"+longitude);

        if (stat == 1){
            statText.setText("정상");
        } else if (stat == 0){
            statText.setText("위험");
        }
    }

    private void showMap(double lati, double longi){
//        Intent intent = new Intent(InfoActivity.this, MapActivity.class);
//        intent.putExtra("LATITUDE",lati);
//        intent.putExtra("LONGITUDE",longi);
//        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null){
            Log.d("InfoActivity New Intent","Intent is null");
            return ;
        }
        Bundle bundle =  intent.getExtras();
        String title = bundle.getString("title");
        String text = bundle.getString("text");
        if (title == null || text == null){
            Log.d("InfoActivity New Intent","title & text is null");
            return ;
        }
        nameText.setText(title);
        statText.setText(text);
        Log.d("InfoActivity New Intent","title: "+ title + ", text: "+text);
        super.onNewIntent(intent);
    }
}