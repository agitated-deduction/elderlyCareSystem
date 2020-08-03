package com.example.elderlyversion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elderlyversion.R;
import com.example.elderlyversion.data.KeyData;
import com.example.elderlyversion.data.LoginData;
import com.example.elderlyversion.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText nameText;
    private EditText birthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameText = findViewById(R.id.nameText);
        birthText = findViewById(R.id.birthText);

        nameText.setText("노인1");
        birthText.setText("4912304102030");


        Button startButton = findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login2();
            }
        });
    }

    private void login(){
        final String name = nameText.getText().toString();
        String birth = birthText.getText().toString();

        if (name.length()>0 && birth.length()>0){
            // TODO : 서버에 로그인 정보 Post & {ekey, regId} Response
            LoginData loginData = new LoginData(name,birth);
            ApiUtils.getElderlyService(getApplicationContext()).login(loginData).enqueue(new Callback<KeyData>() { // .login(loginData) 로 변경하기
                @Override
                public void onResponse(Call<KeyData> call, Response<KeyData> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        Log.d("LoginActivity","Connect Success");

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name", response.body().getEname());
                        intent.putExtra("ekey", response.body().getEkey());
                        intent.putExtra("regId", response.body().getRegId());
                        intent.putExtra("homeIoT", response.body().getHomeIoT());
                        startActivity(intent);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<KeyData> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "지금은 연결할 수 없습니다. " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });// End of Login
        }else{
            Toast.makeText(LoginActivity.this, "이름 생년월일을 확인하세요. ", Toast.LENGTH_SHORT).show();
            return ;
        }
    }

    private void login2(){
        final String name = nameText.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("homeIot", "http://192.168.1.22:3000/");
        intent.putExtra("ekey",1);

        intent.putExtra("regid", "eTRx-Z31TdCjy00iLSygQB:APA91bHKGYvaPTKc26kIJjhC2Bu_GQf-XPlwnZNMubK4gqptdhxtIEmqdh-r9-RyFClj0BLAoXRQn_xOBN-obMhMsUU__q_JqmKeSN1DCcQlb5zSzgepPzJM6gD_Qwu43S4bpZhhA1Gx");

        startActivity(intent);
    }
}