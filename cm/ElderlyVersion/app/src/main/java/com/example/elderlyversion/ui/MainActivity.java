package com.example.elderlyversion.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elderlyversion.R;
import com.example.elderlyversion.data.step.StepCheckService;

public class MainActivity extends AppCompatActivity {
    Intent manboService;
    BroadcastReceiver receiver;
    boolean flag = true;
    String serviceData;
    TextView countText;
    Button playingBtn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manboService = new Intent(this, StepCheckService.class);
        receiver = new PlayingReceiver();
        countText = (TextView) findViewById(R.id.stepText);


        getSteps();
    }

    public void getSteps(){
        // TODO Auto-generated method stub
        try {
            IntentFilter mainFilter = new IntentFilter("make.a.yong.manbo");
            registerReceiver(receiver, mainFilter);
            startService(manboService);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    class PlayingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("PlayignReceiver", "IN");
            serviceData = intent.getStringExtra("stepService");
            countText.setText(serviceData);
            //Toast.makeText(getApplicationContext(), "Playing game", Toast.LENGTH_SHORT).show();
        }
    }
}