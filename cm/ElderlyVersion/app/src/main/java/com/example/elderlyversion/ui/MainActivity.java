package com.example.elderlyversion.ui;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.elderlyversion.R;
import com.example.elderlyversion.data.ElderlyData;
import com.example.elderlyversion.data.step.StepCheckService;
import com.example.elderlyversion.service.BTCTemplateService;
import com.example.elderlyversion.utils.ApiUtils;
import com.example.elderlyversion.utils.AppSettings;
import com.example.elderlyversion.utils.Constants;
import com.example.elderlyversion.utils.Logs;
import com.example.elderlyversion.utils.RecycleUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.example.elderlyversion.utils.Constants.MESSAGE_BT_ADDING;

public class MainActivity extends Activity {
    // Debugging
    private static final String TAG = "RetroWatchActivity";

    private BTCTemplateService mService;
    private Handler mActivityHandler;

    // Global

    // UI stuff
    private ImageView mImageBT = null;
    private TextView mTextStatus = null;

    private String receiveMessage = "";
    private TextView stepText = null;
    private TextView pulseText = null;
    private TextView kcalText = null;
    private TextView latiText = null;
    private TextView longText = null;

    // Time to get message from Arduino
    private static final int NEW_LINE_INTERVAL = 500;
    private static long mLastReceivedTime = 0L;
    // Time to send Data to Server
    private static final int DATA_INTERVAL = 10000; // 600000
    private static long lastTime = 0L;

    // Post to server & Push
    private static String name;
    private static String homeIot;
    private static int ekey;


    private static String regid;

    private static ElderlyData elderlyData;

    // Step Check
    Intent manboService;
    BroadcastReceiver receiver;
    boolean flag = true;
    // TextView stepText; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        elderlyData = new ElderlyData();
        elderlyData.setEstat(1);

        mActivityHandler = new ActivityHandler();
        AppSettings.initializeAppSettings(this);
        setContentView(R.layout.activity_main);

        elderlyData = new ElderlyData(1, 12, 65,0, 6.54, 36.184718283949, 127.801093940);

        // Get & Set Username from LoginActivity
        Intent intent = getIntent();
        processIntent(intent);

        // UI setting
        uiSetting();

        // Do data initialization after service started and binded
        doStartService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendEmergancyData();
        sendData(elderlyData);
    }

    @Override
    public synchronized void onStart() {
        super.onStart();
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finalizeActivity();
    }

    @Override
    public void onLowMemory (){
        super.onLowMemory();
        // onDestroy is not always called when applications are finished by Android system.
        finalizeActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                // Launch the DeviceListActivity to see devices and do scan
                doScan();
                return true;
            case R.id.action_discoverable:
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();		// TODO: Disable this line to run below code
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        // This prevents reload after configuration changes
        super.onConfigurationChanged(newConfig);
    }

    private ServiceConnection mServiceConn = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(TAG, "Activity - Service connected");
            mService = ((BTCTemplateService.ServiceBinder) binder).getService();
            // Activity couldn't work with mService until connections are made
            // So initialize parameters and settings here. Do not initialize while running onCreate()
            initialize();
        }
        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };
    // Start service if it's not running
    private void doStartService() {
        Log.d(TAG, "# Activity - doStartService()");
        startService(new Intent(this, BTCTemplateService.class));
        bindService(new Intent(this, BTCTemplateService.class), mServiceConn, Context.BIND_AUTO_CREATE);
    }
    // Stop the service
    private void doStopService() {
        Log.d(TAG, "# Activity - doStopService()");
        mService.finalizeService();
        stopService(new Intent(this, BTCTemplateService.class));
    }

    // Initialization / Finalization
    private void initialize() {
        Logs.d(TAG, "# Activity - initialize()");
        mService.setupService(mActivityHandler);

        // If BT is not on, request that it be enabled.
        // RetroWatchService.setupBT() will then be called during onActivityResult
        if(!mService.isBluetoothEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, Constants.REQUEST_ENABLE_BT);
        }
    }

    private void finalizeActivity() {
        Logs.d(TAG, "# Activity - finalizeActivity()");

        if(!AppSettings.getBgService()) {
            doStopService();
        }
        // Clean used resources
        RecycleUtils.recursiveRecycle(getWindow().getDecorView());
        System.gc();
    }

    // Launch the DeviceListActivity to see devices and do scan
    private void doScan() {
        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CONNECT_DEVICE);
    }

    // Ensure this device is discoverable by others
    private void ensureDiscoverable() {
        if (mService.getBluetoothScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logs.d(TAG, "onActivityResult " + resultCode);

        switch (requestCode) {
            case Constants.REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = Objects.requireNonNull(data.getExtras()).getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Attempt to connect to the device
                    if (address != null && mService != null)
                        mService.connectDevice(address);
                }
                break;

            case Constants.REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a BT session
                    mService.setupBT();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Logs.e(TAG, "BT is not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                }
                break;
        }    // End of switch(requestCode)
    }


    @SuppressLint("HandlerLeak")
    public class ActivityHandler extends Handler {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what) {
                // Receives BT state messages from service, and updates BT state UI
                case Constants.MESSAGE_BT_STATE_INITIALIZED:
                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " + getResources().getString(R.string.bt_state_init));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
                    break;
                case Constants.MESSAGE_BT_STATE_LISTENING:
                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " +  getResources().getString(R.string.bt_state_wait));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
                    break;
                case Constants.MESSAGE_BT_STATE_CONNECTING:
                    mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " + getResources().getString(R.string.bt_state_connect));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_away));
                    break;
                case Constants.MESSAGE_BT_STATE_CONNECTED:
                    if(mService != null) {
                        String deviceName = mService.getDeviceName();
                        if(deviceName != null) {
                            mTextStatus.setText(getResources().getString(R.string.bt_title) + ": " + getResources().getString(R.string.bt_state_connected) + " " + deviceName);
                            mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
                        }
                    }
                    break;
                case Constants.MESSAGE_BT_STATE_ERROR:
                    mTextStatus.setText(getResources().getString(R.string.bt_state_error));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
                    break;

                // BT Command status
                case Constants.MESSAGE_CMD_ERROR_NOT_CONNECTED:
                    mTextStatus.setText(getResources().getString(R.string.bt_cmd_sending_error));
                    mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
                    break;

                ///////////////////////////////////////////////
                // When there's incoming packets on bluetooth
                // do the UI works like below
                ///////////////////////////////////////////////
                // TODO : Arduino Message -> 화면에 표시
                case Constants.MESSAGE_READ_CHAT_DATA:
                    long current = System.currentTimeMillis();
                    if(msg.obj != null) {
                        Log.d("RECEIVE_MSG",(String)msg.obj);
                        String str = addMessage((String)msg.obj);

                        if (str == MESSAGE_BT_ADDING || str == " "){
                            return;
                        }
                        if (str.substring(0,1).equals("0")){
                            Log.d("STATE_EMR", str);
                            messageParsing(str);
                            messageSetting(elderlyData);
//                            TODO : Emergancy Data Push
                            sendEmergancyData();
                            sendData(elderlyData);
                            lastTime = current;
                        }
                        else if (str.substring(0,1).equals("1")){
                            Log.d("STATE_NOR", str);
                            messageParsing(str);
                            messageSetting(elderlyData);
                        }
                        else{
                            Log.d("RECEIVE_ERROR",str);
                        }

                        if(current - lastTime > DATA_INTERVAL) {
                            sendData(elderlyData);
                            lastTime = current;
                        }
                    }

                    receiveMessage = "";
                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    }	// End of class ActivityHandler

    // TODO : Server에 메시지 PUT
    public void sendData(ElderlyData elderlyData){
        // TODO : Elderly Data -> 서버에 전송( 주기 : DATA_INTERVAL 설정 ㄱㄱ)
        ApiUtils.getElderlyService(getApplicationContext()).putElderlyData(elderlyData).enqueue(new retrofit2.Callback<ResponseBody>() {
//        ApiUtils.getElderlyService(getApplicationContext()).putElderlyData2("elderlyData").enqueue(new retrofit2.Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("PUT_SUCCESS", response.message()); // 성공 : OK(200)
//                    try {
//                        Toast.makeText(getApplicationContext(), "Put Success:" + response.body().string(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    Log.d("PUT_NOT_SUCCESS", response.message()); // 실패
//                    Toast.makeText(getApplicationContext(), "PUT_FAIL:" + response.message() + ", " + response.body(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.getLocalizedMessage();
                Log.d("PUT_FAILURE", call.toString());
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "PUT_FAILURE" + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // TODO : Volley로 Push 보내기(Notification & Data)
    public void sendEmergancyData() {
        Log.d("PUSH","Start");
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject pushData = new JSONObject();

            pushData.put("KEY", elderlyData.getEkey());
            pushData.put("NAME", name);
            pushData.put("HOME", homeIot);

            pushData.put("title", name);
            pushData.put("body", "위험");

            JSONObject push = new JSONObject();
            push.put("to", regid);
            push.put("data", pushData);

            JsonObjectRequest request = new JsonObjectRequest(url, push, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Emergancy_Push","success:"+response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Emergancy_Push","error:"+error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", Constants.FCM_API_KEY);
                    return headers;
                }
            };
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("PUSH","End");
    }

    public void dataPushNPut(ElderlyData elderlyData) throws InterruptedException {
        int i = 1;
        Thread.sleep(10000);
        while(i==1){
            sendEmergancyData();
            sendData(elderlyData);
        }
    }

    // TODO : Arduino Message parsing
    public String addMessage(String message) {
        String addMessage = "";
        if (message != null && message.length() > 0) {
            receiveMessage += message;

            long current = System.currentTimeMillis();
            if (current - mLastReceivedTime > NEW_LINE_INTERVAL) {
                Log.d("ADDED MESSAGE END", receiveMessage);
                mLastReceivedTime = current;
                addMessage = receiveMessage;

                return addMessage;
            }
        }
        Log.d("Method", "Message Adding..");
        return MESSAGE_BT_ADDING;
    }

    public void messageParsing(String receiveMsg){
        String[] array = receiveMsg.split(",");

        //step = array[0].substring(6);
        elderlyData.setEpulse(Integer.parseInt(array[1].substring(6)));
        elderlyData.setEaltitude(Double.parseDouble(array[2].substring(5)));
        elderlyData.setElongitude(Double.parseDouble(array[3].substring(5)));
    }
    public void messageSetting(ElderlyData elderly){
        pulseText.setText(String.valueOf(elderly.getEpulse()));
        latiText.setText(String.valueOf(elderly.getEaltitude()));
        longText.setText(String.valueOf(elderly.getElongitude()));
        //longi = Double.parseDouble(str[3]);
    }

    // Count Step
    class StepReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("PlayignReceiver", "IN");
            int s = Integer.parseInt(intent.getStringExtra("stepService"));
            elderlyData.setEstep(s) ;
            stepText.setText(String.valueOf(elderlyData.getEstep()) );
            elderlyData.setEkcal(elderlyData.getEstep() * 70 * 5.5 / 10000);
            kcalText.setText(String.valueOf(elderlyData.getEkcal()));
        }
    }

    public void getSteps(){
        // TODO Auto-generated method stub
        try {
            IntentFilter mainFilter = new IntentFilter("make.a.yong.manbo");
            registerReceiver(receiver, mainFilter);
            startService(manboService);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void processIntent(Intent intent) {
        if (intent != null){
            Bundle bundle = intent.getExtras();

            name = bundle.getString("name");
            homeIot = bundle.getString("homeIot");
            ekey = bundle.getInt("ekey");
            elderlyData.setEkey(ekey);

            if (bundle.getString("regid")!= null){
                regid = bundle.getString("regid");
            }
            Toast.makeText(this,name+"접속. " + "eKey:"+ekey+"regId:"+regid,Toast.LENGTH_SHORT).show();
        }
    }

    public void uiSetting(){
        // Setup views
        mImageBT = (ImageView) findViewById(R.id.status_title);
        mImageBT.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
        mTextStatus = (TextView) findViewById(R.id.status_text);
        mTextStatus.setText(getResources().getString(R.string.bt_state_init));
        TextView nameText = findViewById(R.id.nameText);
        // stepText = findViewById(R.id.stepText);
        pulseText = findViewById(R.id.pulseText);
        kcalText = findViewById(R.id.kcalText);
        latiText = findViewById(R.id.latitudeText);
        longText = findViewById(R.id.longitudeText);

        // Step Count
        manboService = new Intent(this, StepCheckService.class);
        receiver = new StepReceiver();
        stepText = findViewById(R.id.stepText);
        getSteps();

        Button button = findViewById(R.id.mapButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public static ElderlyData getElderlyData() { return elderlyData; }
}