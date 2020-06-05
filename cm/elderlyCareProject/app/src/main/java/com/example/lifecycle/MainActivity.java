package com.example.lifecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {



    public static final int REQUEST_CODE_MENU = 101;
    public static final String KEY_SIMPLE_DATA = "data";
    public static int rad_value;
    public EditText edit_id;
    public EditText edit_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 0. 로그인 데이터 저장
        //  0.1 id & pw 값 저장하기
        edit_id = findViewById(R.id.id);
        edit_pw = findViewById(R.id.pw);





        //  0.2 radio 값 저장하기
        final RadioButton option_admin = (RadioButton) findViewById(R.id.op_admin);
        final RadioButton option_user = (RadioButton) findViewById(R.id.op_user);

        RadioButton.OnClickListener optionOnClickListener = new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (option_admin.isChecked() == true)
                    rad_value = 1;
                else if (option_user.isChecked() == true)
                    rad_value = 0;
                else{
                    Toast.makeText(MainActivity.this.getApplicationContext(),"사용자 유형을 체크해주세요", Toast.LENGTH_LONG);
                    option_admin.setChecked(true);
                }
            }
        };
        option_admin.setOnClickListener(optionOnClickListener);
        option_user.setOnClickListener(optionOnClickListener);




        // 1. 로그인 버튼 클릭
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  1.0.0 MenuActivity로 데이터 전송할 intent 생성
                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                //  1.0.1 MQTT 서버에 데이터 하기


                //  1.1 서버로 데이터 전송(id&pw&radio)
                // id & pw
                String id = edit_id.getText().toString();
                String pw = edit_pw.getText().toString();

                //  > 전송 데이터(Message) : String
                String send_data = String.format("%-10s%-10s%d", id, pw,rad_value);






                //  1.2 서버로부터 데이터 수신(data)




                //   1.2.2 DataSet 객체 생성(사용 가능한 데이터로 변환)


                //  > for문으로 데이터 나눠서 변수에 저장
                DataSet data = new DataSet("이름","nomal",36,100,100);



                //   1.2.2 인텐트에 부가 데이터 넣기
                intent.putExtra(KEY_SIMPLE_DATA, data);

                // 2. 메뉴 화면으로 전환(수신 데이터로 activity 구성)
                startActivityForResult(intent,REQUEST_CODE_MENU);



            }
        });





    }
}