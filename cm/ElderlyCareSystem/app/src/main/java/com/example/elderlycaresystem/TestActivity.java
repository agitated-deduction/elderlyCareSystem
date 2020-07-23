package com.example.elderlycaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class TestActivity extends AppCompatActivity {

    TextView textView;
    TextView textView2;
    String newToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.d("FMS_MAIN", "onCreate() 호출됨");

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        // TODO : 등록 ID(Token) 확인을 위한 리스너 설정하기
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();

                println("등록 id: " + newToken);
                Log.d("Mytoken", "등록 id: " + newToken);

            }
        });
        Log.d("Mytoken", "등록 id: " + newToken);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Mytoken", "버튼 클릭!");
                String instancedId = FirebaseInstanceId.getInstance().getId(); // 등록 ID값 확인을 위한 메서드 호출하기
                println("확인된 인스턴스 id: " + instancedId);
                Log.d("Mytoken", "등록 id: " + instancedId);
                Toast.makeText(TestActivity.this,instancedId,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent() 호출됨");
        Log.d("FMS_MAIN", "onNewIntent() 호출됨");

        if (intent != null) {
            println("Intent is Not null");
            processIntent(intent);
        } else {
            println("Intent is null");
        }

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        Log.d("FMS_MAIN", "processIntent 호출됨");
        int ekey = intent.getIntExtra("ekey", 2);
        String ename = intent.getStringExtra("ename");

        println("ekey: " + ekey);
        textView.setText("");
        textView.setText("[" + ename + "]님의 eKey : " + ekey);
    }

    public void println(String data) {
        textView2.setText(data + "\n");
    }
}