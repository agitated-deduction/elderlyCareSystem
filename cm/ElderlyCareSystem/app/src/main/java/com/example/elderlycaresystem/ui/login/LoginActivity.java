package com.example.elderlycaresystem.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elderlycaresystem.data.Elderly;
import com.example.elderlycaresystem.data.LoginResponse;
import com.example.elderlycaresystem.ui.main.MainActivity;
import com.example.elderlycaresystem.R;
import com.example.elderlycaresystem.util.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final int REQUEST_CODE_MAIN = 101;
    private EditText idText;
    private EditText pwText;
    private TextView joinText;
    private Button loginButton;
    private ProgressBar progressBar;
    private  ArrayList<Elderly> elderlyArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        requestCallPermission();
    }

    private void initView() {
        idText = findViewById(R.id.idText);
        pwText = findViewById(R.id.pwText);

        joinText = findViewById(R.id.joinText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        // TODO 1.회원가입 버튼 처리
        joinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com"));
                startActivity(intent);
            }
        });

        // TODO 2. 로그인 버튼 처리
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                // DO something
            }
        });
    }

    private void requestCallPermission() {
        // CALL_PHONE 위험 권한 요청하기
        String[] targets = {Manifest.permission.CALL_PHONE};
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"전화 권한 없음.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(LoginActivity.this,targets,101);
        }
    }

    private void login() {
        // TODO 2.1 서버 연결 & 데이터 수신
        // TODO 2.1.1 송신 데이터(ID & PW) 확인
        String id = idText.getText().toString();
        String pw = pwText.getText().toString();

        if (id.length()==0) {
            Toast.makeText(LoginActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            idText.requestFocus();
            return ;
        }
        if (pw.length()==0) {
            Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            pwText.requestFocus();
            return ;
        }

        // ProgressBar Setting
        progressBar.setVisibility(View.VISIBLE);
        // Server에 로그인 데이터(id,pw) 전송
        ApiUtils.getElderlyService().login(id, pw).enqueue(new Callback<LoginResponse>() {
            // TODO : 응답성공 => response 처리(성공 || 실패)
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE); // ProgressBar Close
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("MainActivity", "posts loaded from API");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // Intent에 Server에서 보낸 <ID & Name &Stat> 담아 MainActivity로 전송
                    intent.putExtra(MainActivity.INTENT_ID, response.body().getId());
                    intent.putExtra(MainActivity.INTENT_NAME, response.body().getName());
                    intent.putExtra(MainActivity.INTENT_STAT, response.body().getStat());
                    startActivity(intent);
                }else {
                    int statusCode  = response.code();
                    Toast.makeText(LoginActivity.this, "Error code : " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }
            // TODO : 응답실패
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Error message : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}