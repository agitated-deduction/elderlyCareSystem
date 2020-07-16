package com.example.elderlycaresystem;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.elderlycaresystem.ui.info.InfoActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingServie extends FirebaseMessagingService {
    private static final String TAG = "FMS";

    public MyFirebaseMessagingServie() {
    }

    // 새로운 토큰을 확인했을 때 호출되는 메서드
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e(TAG,"onNewToken() 호출됨: " + s);
    }

    // 새로운 메시지를 받았을 때 호출되는 메서드
    // TODO : 긴급상황일 때 MainActivity 위에 EmergencyAcitivity 띄우기
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"onMessageReceived() 호출됨");

        String from = remoteMessage.getFrom();
        Map<String,String> data = remoteMessage.getData();
        String contents = data.get("contents");

        Log.d(TAG,"from : " + from + ", contents : "+contents);
        sendToActivity(getApplicationContext(),from, contents);
    }

    // sendToActivity() : Intent 생성해 InfoActivity 실행
    private void sendToActivity(Context context, String from, String contents){
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra("STAT","emergency");
        intent.putExtra("FROM",from);
        intent.putExtra("CONTENTS",contents);
        context.startActivity(intent);
    }
}
