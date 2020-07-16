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

import com.example.elderlycaresystem.data.login.LoginData;
import com.example.elderlycaresystem.data.elderly.ElderlyInfo;
import com.example.elderlycaresystem.data.login.LoginResponse;
import com.example.elderlycaresystem.R;
import com.example.elderlycaresystem.ui.main.MainActivity;
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
    private  ArrayList<ElderlyInfo> elderlyInfoArrayList;

    private ElderlyInfo elder;


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

        idText.setText("staff000001");
        pwText.setText("staff000001");

        joinText = findViewById(R.id.joinText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        // TODO 1.회원가입 버튼 처리
        joinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.1.29:9090/elderlycare/users/join"));
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
        // TODO 테스트 끝나면 지우기

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
        LoginData loginData = new LoginData(id,pw);

        // ProgressBar Setting
        progressBar.setVisibility(View.VISIBLE);
        // Server에 로그인 데이터(id,pw) 전송

        //ApiUtils.getElderlyService().login(id, pw).enqueue(new Callback<LoginResponse>() {
        ApiUtils.getElderlyService(getApplicationContext()).login(loginData).enqueue(new Callback<LoginResponse>() {
            // TODO : 응답성공 => response 처리(성공 || 실패)
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE); // ProgressBar Close
                if(response.isSuccessful() && response.body() != null) {
                    // TODO : 서버에서 받은 쿠키 확인하기
                    Log.d("Connect_Login", "Header values(Set-Cookie) : "+response.headers().values("Set-Cookie").toString().substring(12,44));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // TODO : 서버에서 받은(LoginResponse 객체로 변환된) 데이터를 인텐트에 담아 MainActivity 실행
                    if (response.body().getUid()!=null){
                        intent.putExtra(MainActivity.INTENT_ID, response.body().getUid());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"아이디 비밀번호를 확인하세요",Toast.LENGTH_LONG).show();
                        return;
                    }
                }else {
                    int statusCode  = response.code();
                    Toast.makeText(LoginActivity.this, "연결 실패 : " + statusCode+", 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
            // TODO : 응답실패
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "지금은 연결할 수 없습니다. " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}