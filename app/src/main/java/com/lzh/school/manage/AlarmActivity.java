package com.lzh.school.manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.lzh.school.R;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog alert = new AlertDialog.Builder(this).create();
        //设置对话框的图标
        alert.setIcon(R.drawable.alarm);
        //设置对话框的标题
        alert.setTitle("课程提醒：");
        //设置要显示的内容
        alert.setMessage("该上课了！！！");
        //添加确定按钮
        alert.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //提示后跳转到课程表页面
                Intent intent = new Intent(AlarmActivity.this, ScheduleActivity.class);
                startActivity(intent);
                AlarmActivity.this.finish();
            }
        });
        // 显示对话框
        alert.show();

    }
}
