package com.example.elderlycaresystem;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    public int REQUEST_CODE_MAIN = 101;
    public String DATA_CODE = "ElderlyData";
    public String DATA_KEY = "Key";
    private EditText idText; private EditText pwText;
    private TextView joinText; private Button loginButton;
    public  ArrayList<Elderly> elderlyArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 전화걸기 권한 요청하기
        String[] targets = {Manifest.permission.CALL_PHONE};
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"전화 권한 없음.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(LoginActivity.this,targets,101);
        }




        setContentView(R.layout.activity_login);
        idText = findViewById(R.id.idText);
        pwText = findViewById(R.id.pwText);

        joinText = findViewById(R.id.joinText);
        loginButton = findViewById(R.id.loginButton);

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


                // TODO 2.1.2 데이터(ID & PW) 송신
                // TODO 2.1.3 데이터(Login Success, failure) 수신
                //  > Success : 메인 엑티비티로
                //  >
                //   > 임의 response setting
                //   response => String res = "admin02"; => data를 요청할때 쓸 KEY



                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("ID",id);
                startActivity(intent);

                //  > 로그인 실패
                // Case1 : 존재하지 않는 USER
                //Toast.makeText(LoginActivity.this,"아이디 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                // Case2. 승인되지 않은 USER
                //Toast.makeText(LoginActivity.this,"승인되지 않은 계정입니다.",Toast.LENGTH_SHORT).show();



            }
        });
    }

}