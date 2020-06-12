package com.example.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this, "SMS 수신 권한 주어져 있음.", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(this, "SMS 수신 권한 없음.", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(this, "SMS 권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                // 시스템이 대화상자를 띄워서 사용자에게 권한요청을 한다.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
                // 위 코드의 결과가 Toast메시지로써 보여질 수 있도록 콜백함수를 사용한다(onRequestPermissionsResult() override)
            }
        }
    }

    // override method 통해서 직접 오버라이딩 해준다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0) {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, "SMS 수신권한을 사용자가 승인함", Toast.LENGTH_LONG).show();
                    else if(grantResults[0] == PackageManager.PERMISSION_DENIED)
                        Toast.makeText(this, "SMS 수신권한을 사용자가 거부함", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(this, "SMS 수신권한을 부여받지 못함", Toast.LENGTH_LONG).show();
                }
        }
    }
}

