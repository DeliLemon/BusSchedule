package com.welcome.schoolbusschedule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolbusschedule.R;
import com.login.schoolbusschedule.LoginActivity;

public class MainActivity extends AppCompatActivity {
    MainActivityModel mainActivityModel;
    Handler hander=new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        hander.sendEmptyMessageDelayed(0, 2000);
    }
    private void init(){
        mainActivityModel=new MainActivityModel(MainActivity.this);
        mainActivityModel.init();
    }
}
