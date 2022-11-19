package com.lzh.school.manage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lzh.school.R;
import com.lzh.school.db.ScheduleDBHelper;
import com.lzh.school.utils.ScheduleUtils;

public class AddClassActivity extends Activity {
    Button close_activity;
    Button save_activity;
    Spinner selected_time;
    Spinner selected_day;
    EditText subject;
    EditText teacher;
    //数据库名，表名
    public final String DB_NAME = "classes_db.db";
    public final String TABLE_NAME = "classes_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set);

        //当点击dialog之外完成此activity
//        setFinishOnTouchOutside (true);

        //关闭按钮操作
        close_activity=(Button) findViewById(R.id.close_activity);
        close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddClassActivity.this.finish();
            }
        });

        //保存按钮操作
        save_activity = findViewById(R.id.save_activity);
        save_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                selected_day = findViewById(R.id.selected_day);
                String day = selected_day.getSelectedItem().toString();

                if (day.equals("--请选择--")) {
                    Toast.makeText( AddClassActivity.this, "请输入正确的课程信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                selected_time = findViewById(R.id.selected_time);
                String time = selected_time.getSelectedItem().toString();
                if (time.equals("--请选择--")) {
                    Toast.makeText( AddClassActivity.this, "请输入正确的课程信息！", Toast.LENGTH_SHORT).show();
                    return;
                }

                subject = findViewById(R.id.subject);
                String text = subject.getText().toString();
                //当没有输入课程信息和老师信息的时候，点击保存直接将null值存入数据库

                teacher = findViewById(R.id.teacher);
                String teacher = subject.getText().toString();

                //创建一个数据库对象，为了更新数据
                ScheduleDBHelper dbHelper = new ScheduleDBHelper(AddClassActivity.this, DB_NAME, null, 1);

                //把数据存在contentValues中
                ContentValues contentValues = new ContentValues();
                //combineId()方法目的是通过星期几和第几节课算出对应该课程id
                contentValues.put("c_id", combineId(day, time));
                contentValues.put("c_name", text);
                contentValues.put("c_time", time);
                contentValues.put("c_day", day);
                contentValues.put("c_teacher", teacher);

                //更新数据库记录
                update(dbHelper, contentValues);

                //清空栈内所有activity
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);

                //启动ScheduleActivity
                intent.setClass(AddClassActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    public String combineId(String day, String time) {

        //星期几转换成int类型
        int day1 = ScheduleUtils.getDay(day);

        //如1-2节课只取1
        int time1 = Integer.parseInt(time.substring(0, 1));

        return String.valueOf((day1 - 1) * 5 + ((time1 - 1)/2 + 1));
    }

    public void update(ScheduleDBHelper dbHelper, ContentValues contentValues){

        String []a = {contentValues.get("c_id").toString()};

        // 通过DBHelper类获取一个读写的SQLiteDatabase对象
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        // 修改数据
        // 参数1：表名
        // 参数2：修改的值
        // 参数3：修改的条件（SQL where语句）
        // 参数4：表示whereClause语句中的表达式的占位符参数列表，这些字符串会替换where条件中?
        db.update(TABLE_NAME,contentValues,"c_id=?", a);

        // 释放连接
        db.close();
    }

}
