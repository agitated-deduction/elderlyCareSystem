package com.example.mybtchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybtchat.R;
import com.example.mybtchat.utils.Constants;

public class LoginActivity extends AppCompatActivity {
    private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameText = findViewById(R.id.nameText);
        Button startButton = findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                if (name != null){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra(Constants.USER_NAME,name);
                    startActivity(intent);
                }
            }
        });
    }
}