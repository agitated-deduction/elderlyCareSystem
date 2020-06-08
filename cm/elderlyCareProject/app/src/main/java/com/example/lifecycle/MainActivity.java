package com.example.lifecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_MENU = 101;
    public static final String KEY_SIMPLE_DATA = "data";
    public static int rad_value;
    public EditText edit_id;
    public EditText edit_pw;
    private Button button_login;// Login Button
    ArrayList<DataSet> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 0. 로그인 데이터 저장
        //  0.1 id & pw 값 저장하기
        edit_id = findViewById(R.id.id);
        edit_pw = findViewById(R.id.pw);

        final String id = edit_id.getText().toString();
        final String pw = edit_pw.getText().toString();

        //  0.2 radio 값 저장하기
        final RadioButton option_admin = (RadioButton) findViewById(R.id.op_admin);
        final RadioButton option_user = (RadioButton) findViewById(R.id.op_user);
        rad_value = -1;
        RadioButton.OnClickListener optionOnClickListener = new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (option_admin.isChecked() == true)
                    rad_value = 1;
                else if (option_user.isChecked() == true)
                    rad_value = 0;
                else{
                    Toast.makeText(MainActivity.this.getApplicationContext(),"사용자 유형을 체크해주세요.", Toast.LENGTH_LONG);
                }
            }
        };
        option_admin.setOnClickListener(optionOnClickListener);
        option_user.setOnClickListener(optionOnClickListener);

        // 1.1 로그인 버튼 클릭 시
        button_login = findViewById(R.id.button);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edit_id.getText().toString().length() == 0){
                    Toast.makeText(MainActivity.this,"아이디를 입력하세요.",Toast.LENGTH_SHORT).show();
                    edit_id.requestFocus();
                    return ;
                }
                if (edit_pw.getText().toString().length() == 0){
                    Toast.makeText(MainActivity.this,"비밀번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                    edit_pw.requestFocus();
                    return ;
                }
                //  > radio_value
                if (rad_value == -1){
                    Toast.makeText(MainActivity.this,"계정 유형을 선택하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    //  1.1.0 기본 정보 입력(id&pw&radio)
                    //  > id & pw

                    // 1.1.1 서버 연결 요청(Connect)
                    String clientId = MqttClient.generateClientId();
                    final MqttAndroidClient client =
                            new MqttAndroidClient(MainActivity.this, "tcp://broker.hivemq.com:1883",
                                    clientId);
                    IMqttToken token = client.connect();
                    // 1.1.2 연결 결과 callback 함수
                    token.setActionCallback(new IMqttActionListener() {
                        // > 연결 성공 시
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // We are connected
                            //Toast.makeText(MainActivity.this,"connected",Toast.LENGTH_SHORT).show();
                            //  1.2 Server subscribe(topic&qos)
                            //  > 전송 데이터(Message) : String & qos
                            //String topic = String.format("%-10s/%-10s%d", id, pw,rad_value);
                            String topic = "foo/bar";
                            int qos = 1;
                            try {// > Token(Topic & qos)
                                IMqttToken subToken = client.subscribe(topic, qos);
                                subToken.setActionCallback(new IMqttActionListener() {
                                    // 1.2.2 로그인 정보 전송 성공시!!
                                    @Override
                                    public void onSuccess(IMqttToken asyncActionToken) {



                                    }
                                    // 1.2.3 연결 실패시..
                                    @Override
                                    public void onFailure(IMqttToken asyncActionToken,
                                                          Throwable exception) {
                                        // The subscription could not be performed, maybe the user was not
                                        // authorized to subscribe on the specified topic e.g. using wildcards
                                        Toast.makeText(MainActivity.this,"아이디 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }

                            client.setCallback(new MqttCallback() {
                                // > 연결 끊김....
                                @Override
                                public void connectionLost(Throwable cause) {
                                    Toast.makeText(MainActivity.this,"접속 끊김:"+cause,Toast.LENGTH_SHORT).show();
                                }
                                // > 받은 데이터 확인!!!!
                                @Override
                                public void messageArrived(String topic, MqttMessage message) throws Exception {
                                    //Toast.makeText(MainActivity.this,new String(message.toString()+", "+topic),Toast.LENGTH_SHORT).show();
                                    // 1.3 피부양자 Data 받아 DataSet Array로 저장
                                    // client.setCallback에서 처리 (massageArrived) ㄱㄱ!!
                                }
                                // > Token 전송 성공!!!!
                                @Override
                                public void deliveryComplete(IMqttDeliveryToken token) {
                                    Toast.makeText(MainActivity.this,"Deliver " + token +" Complete",Toast.LENGTH_SHORT).show();
                                }
                            });


                            //   1.2.2 DataSet 객체 생성(사용 가능한 데이터로 변환)


                            //   1.2.2 인텐트에 부가 데이터 넣기
                            //  > for문으로 데이터 나눠서 변수에 저장
                            DataSet data1 = new DataSet("홍길동","위험",34,65,1450);
                            DataSet data2 = new DataSet("장길산","안정",38,100,0);
                            DataSet data3 = new DataSet("임꺽정","양호",37,110,50);

                            dataList = new ArrayList<DataSet>();
                            dataList.add(data1);
                            dataList.add(data2);
                            dataList.add(data3);



                            //  1.2.2 MenuActivity로 데이터 전송할 intent 생성
                            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList(KEY_SIMPLE_DATA,dataList);// KEY_SIMPLE_DATA => "list"?
                            intent.putExtra(KEY_SIMPLE_DATA,bundle);

                            // 2. 메뉴 화면으로 전환(수신 데이터로 activity 구성)
                            startActivityForResult(intent,REQUEST_CODE_MENU);

                        }

                        // > 연결 실패 시
                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            // Something went wrong e.g. connection timeout or firewall problems
                            Toast.makeText(MainActivity.this,"not connected",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        // 회원가입 버튼 클릭(가입 Url 연결)
        Button addaccountbutton = findViewById(R.id.addAcountButton);
        addaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.naver.com"));
                startActivity(intent);
            }
        });
    }
}