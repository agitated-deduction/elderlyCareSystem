package com.example.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lifecycle.DataSet;
import com.example.lifecycle.R;

public class MenuActivity extends AppCompatActivity {

    public static final String KEY_SIMPLE_DATA = "data"
            ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. MenuActivity 화면 구성하기
        LinearLayout mainLayout = new LinearLayout(this);
        LinearLayout subLayout = new LinearLayout(this);

        mainLayout.setOrientation(LinearLayout.VERTICAL);
        subLayout.setOrientation(LinearLayout.HORIZONTAL);

        //  > Main Layer
        LinearLayout.LayoutParams params1 =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,//width
                        LinearLayout.LayoutParams.WRAP_CONTENT//height
                );

        //  > sub Layer
        LinearLayout.LayoutParams params2 =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.HORIZONTAL
                );

        // 1.1 Button(name) & TextView(data) 생성
        Button button1 = new Button(this);
        TextView text_temp = new TextView(this);
        TextView text_pur = new TextView(this);
        TextView text_gps = new TextView(this);
        TextView text_stat = new TextView(this);



        // 2.1 데이터 적용
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            DataSet data = bundle.getParcelable(KEY_SIMPLE_DATA);
            if (intent != null) {
                text_temp.setText("체온 "+data.temporature + "°C\t");
                text_temp.setLayoutParams(params2);
                subLayout.addView(text_temp);
                text_pur.setText("맥박 "+data.purse + "bpm\t");
                text_pur.setLayoutParams(params2);
                subLayout.addView(text_pur);
                text_gps.setText("집-거리 "+data.gps + "m\t");
                text_gps.setLayoutParams(params2);
                subLayout.addView(text_gps);
                text_stat.setText("상태 "+data.statement + "  \t");
                text_stat.setLayoutParams(params2);
                subLayout.addView(text_stat);
                button1.setText(data.user_name);//
                button1.setLayoutParams(params1);
                mainLayout.addView(button1);
            }
        }
        mainLayout.addView(subLayout);
        setContentView(mainLayout);



        // 2. 피부양자 버튼 클릭시 해당 피부양자 Data 화면(Activity or fragment) 띄우기





    }

}