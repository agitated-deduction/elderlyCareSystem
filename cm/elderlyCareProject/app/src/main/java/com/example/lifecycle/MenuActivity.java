package com.example.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifecycle.DataSet;
import com.example.lifecycle.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    // 1.1 Button(name) & TextView(data) 생성
    public ArrayList<Button> buttons;
    public ArrayList<TextView> temps;
    public ArrayList<TextView> purses;
    public ArrayList<TextView> gpss;
    public ArrayList<TextView> stats;

    public static final String KEY_SIMPLE_DATA = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. MenuActivity 화면 구성하기
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        ArrayList<LinearLayout> subLayouts;


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

        buttons = new ArrayList<Button>();
        temps = new ArrayList<TextView>();
        purses = new ArrayList<TextView>();
        gpss = new ArrayList<TextView>();
        stats = new ArrayList<TextView>();

        // 2.1 데이터 적용
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getBundleExtra(KEY_SIMPLE_DATA);
            ArrayList<DataSet> elderData = bundle.getParcelableArrayList(KEY_SIMPLE_DATA);
            //Toast.makeText(MenuActivity.this,"First name: "+elderData.get(0).user_name,Toast.LENGTH_SHORT).show();

            int j = 0;
            for (DataSet i:elderData) {
                //Toast.makeText(MenuActivity.this,"First name: "+i.user_name,Toast.LENGTH_SHORT).show();
                if ((i != null)) {

                    Button button = new Button(this);
                    TextView temp = new TextView(this);
                    TextView purse = new TextView(this);
                    TextView gps = new TextView(this);
                    TextView stat = new TextView(this);

                    button.setText(i.user_name);
                    temp.setText("체온 " + i.temporature + "°C\t");
                    purse.setText("맥박 " + i.purse + "bpm\t");
                    gps.setText("집-거리 " + i.gps + "m\t");
                    stat.setText("상태 " + i.statement + "  \t");

                    buttons.add(j, button);
                    temps.add(j, temp);
                    purses.add(j, temp);
                    gpss.add(j, temp);
                    stats.add(j, temp);
                    // SubLayout 구성해서 내용 담고 MainLayout에 추가!!
                    LinearLayout subLayout = new LinearLayout(this);

                    subLayout.setOrientation(LinearLayout.HORIZONTAL);

                    subLayout.addView(buttons.get(j),0,params2);
                    subLayout.addView(temps.get(j),1,params2);

//                    purses.get(j).setLayoutParams(params2);
//                    subLayout.addView(purses.get(j),2);

//                    gpss.get(j).setLayoutParams(params2);
//                    subLayout.addView(gpss.get(j));
//
//                    stats.get(j).setLayoutParams(params2);
//                    subLayout.addView(stats.get(j));

                    mainLayout.addView(subLayout);
                    j++;
                }
            }
            setContentView(mainLayout);
        }


        // Todo 이름 버튼 누르면 영상 Url로 이동 기능 추가!!




        // 2. 피부양자 버튼 클릭시 해당 피부양자 Data 화면(Activity or fragment) 띄우기





    }

}