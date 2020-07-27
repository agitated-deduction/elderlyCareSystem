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
                        intent.putExtra("name", name);
                        intent.putExtra("ekey", response.body().getEkey());
                        intent.putExtra("regid", response.body().getRegId());
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
        intent.putExtra("ekey", 1);

//        intent.putExtra("regid", "e-A5lnhOXLg:APA91bGTHRMaaKXBT3a_tl5wjApb3pc_bkk7C2GlAqYnqaLqXebScVMZHec2F1qDaytod0CmB9OITs3fo9NNxVKnnMCnPSHhSZEnfox8HI7wa6s3rWc1FsTHKKHEC6Uz-eFk7e5Bygd5");
//        intent.putExtra("regid", "cU5SXNtAK1M:APA91bGCXzrFsivGR2bN1icHZM1xbXqr22UYEvOh5Tj6Sysk5RjuE7daZqkoluOhwUwqCSghaBl2iWDmEgCvjCI-7SNgwo_6FSYwUL2VsuYhF5ABQdWC818DU5UPvNkBxQR4-6vONVl5");
//        intent.putExtra("regid", "fW5vaG9wrME:APA91bEHkDueZb_RSeqCH9soJ8k2SGc5kWFrCfz0Ahk9_EA6n69ZPcmDEK1OCNSnyx8TCcjpjK2Qc9dWR4zcZRfBVdaG59HEI7tXz3kbJhiPu74canyYF1yStVCmBSCCNixrerDU-FHC");
        intent.putExtra("regid", "cTTX-DV0R1GPcOK48bxMf5:APA91bGZcYpPcHxfHK-IJGuPYIfMIE4KNxmCzM_IP8gHratx_uc42vrWRBrU7mNmM7pp3a2pFcs8nK2WKVOw5eLhbqZ2Q5_v8qifkVI-ti9GAx6D1m-7MjVbbtNhwYIH31DPuSErCgL-");


        startActivity(intent);
    }
}