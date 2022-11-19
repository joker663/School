package com.lzh.school.manage;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lzh.school.R;

import java.util.Calendar;

public class SetAlarmActivity extends Activity {
    TimePicker timepicker; 			// 时间拾取器
    Calendar c; 					// 日历对象

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        //获取日历对象
        c=Calendar.getInstance();
        // 获取时间拾取组件
        timepicker = (TimePicker) findViewById(R.id.timePicker1);
        // 设置使用24小时制
        timepicker.setIs24HourView(true);
        // 获取“设置闹钟”按钮
        Button button1 = (Button) findViewById(R.id.button1);
        // 为“设置闹钟”按钮添加单击事件监听器
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象
                Intent intent = new Intent(SetAlarmActivity.this, AlarmActivity.class);
                // 获取显示闹钟的PendingIntent对象
                PendingIntent pendingIntent = PendingIntent.getActivity(SetAlarmActivity.this, 0, intent, FLAG_IMMUTABLE);//PendingIntent是对Intent的描述，主要是用来处理即将发生的事情
                // 获取AlarmManager对象
                AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                c.set(Calendar.HOUR_OF_DAY, timepicker.getHour());// 设置闹钟的小时数
                c.set(Calendar.MINUTE, timepicker.getMinute()); // 设置闹钟的分钟数
                c.set(Calendar.SECOND,0);// 设置闹钟的秒数
                //闹钟的类型：RTC_WAKEUP，使用真实的时间设置闹钟，会唤醒系统
                alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);// 设置一个闹钟
                Toast.makeText(SetAlarmActivity.this, "闹钟设置成功", Toast.LENGTH_SHORT).show();                                    // 显示一个消息提示
            }
        });
    }
}
