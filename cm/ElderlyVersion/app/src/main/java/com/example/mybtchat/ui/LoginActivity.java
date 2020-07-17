package com.example.mybtchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybtchat.LoginData;
import com.example.mybtchat.R;
import com.example.mybtchat.data.KeyData;
import com.example.mybtchat.utils.ApiUtils;

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
        Button startButton = findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameText.getText().toString();
                String birth = birthText.getText().toString();
                if (name != null && birth != null){
                    // TODO : 서버에 로그인 정보 Post & {ekey, regId} Response
                    LoginData loginData = new LoginData(name,birth);
                    ApiUtils.getElderlyService(getApplicationContext()).login(name,birth).enqueue(new Callback<KeyData>() { // .login(loginData) 로 변경하기
                        @Override
                        public void onResponse(Call<KeyData> call, Response<KeyData> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                Log.d("LoginActivity","Connect Success");
//                                try {
//                                    Log.d("LoginActivity","response.body(): "+response.body().string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                if (response.body().getEkey()!=null && response.body().getRegId()!=null){
//                                    Toast.makeText(LoginActivity.this,"성공!! eKey:"+response.body().getEkey()+", regid:"+response.body().getRegId(),Toast.LENGTH_LONG).show();
                                    intent.putExtra("name", name);
                                    intent.putExtra("ekey", response.body().getEkey());
                                    intent.putExtra("regid", response.body().getRegId());
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"이름 생년월일 확인하세요",Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "연결 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<KeyData> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "지금은 연결할 수 없습니다. " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });// End of Login
                }
            }
        });
    }
}