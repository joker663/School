package com.lzh.school;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzh.school.card.CardManageActivity;
import com.lzh.school.manage.EduSystemActivity;
import com.lzh.school.manage.ScheduleActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_update;
    private Button bt_delete;
    private Button bt_weather;
    private Button bt_kebiao;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_update = findViewById(R.id.main_card);
        bt_update.setOnClickListener(this);

        bt_delete = findViewById(R.id.main_edu);
        bt_delete.setOnClickListener(this);

        bt_weather = findViewById(R.id.main_weather);
        bt_weather.setOnClickListener(this);

        bt_kebiao = findViewById(R.id.main_kebiao);
        bt_kebiao.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        //通过switch方法跳转到相应界面
        switch (v.getId()) {
            case R.id.main_card:
                Intent intent_card = new Intent();
                intent_card.setClass(MainActivity.this, CardManageActivity.class);
                startActivity(intent_card);
                break;

            case R.id.main_edu:
                Intent intent_edu = new Intent();
                intent_edu.setClass(MainActivity.this, EduSystemActivity.class);
                startActivity(intent_edu);
                break;

            case R.id.main_weather:
                Intent intent_weather = new Intent();
                intent_weather.setClass(MainActivity.this, WeatherActivity.class);
                startActivity(intent_weather);
                break;

            case R.id.main_kebiao:
                Intent intent_kebiao = new Intent();
                intent_kebiao.setClass(MainActivity.this, ScheduleActivity.class);
                startActivity(intent_kebiao);
                break;

        }
    }
}