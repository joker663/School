package com.lzh.school.manage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lzh.school.R;
import com.lzh.school.bean.Classes;
import com.lzh.school.db.ScheduleDBHelper;
import com.lzh.school.utils.ScheduleUtils;

import java.util.ArrayList;
import java.util.List;

//课程表Activity
public class ScheduleActivity extends AppCompatActivity implements View.OnTouchListener{

    public final String DB_NAME = "classes_db.db";
    public final String TABLE_NAME = "classes_db";

    private MenuItem alarm;
    private MenuItem addclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //拿到数据库对象，为了读取数据
        ScheduleDBHelper scheduleDBHelper = new ScheduleDBHelper(ScheduleActivity.this, DB_NAME, null, 1);

        //动态渲染课程框
        framework();

        //将数据库课程渲染到课程框
        applyDraw(scheduleDBHelper);

    }


    public void applyDraw(ScheduleDBHelper scheduleDBHelper) {
        //从数据库拿到课程数据保存在list集合中
        List<Classes> classes = query(scheduleDBHelper);
        for (Classes aClass : classes) {
            //第几节课
            int i = Integer.parseInt(aClass.getC_time().charAt(0) + "");
            //星期几
            int j = ScheduleUtils.getDay(aClass.getC_day());
            //获取此课程对应TextView的id
            TextView Class = findViewById((j - 1) * 5 + ((i - 1)/2 + 1));
            //课程表信息映射出来
            Class.setText(aClass.getC_name());
        }

    }

    @SuppressLint("Range")
    public List<Classes> query(ScheduleDBHelper scheduleDBHelper) {

        List<Classes> classes = new ArrayList<>();
        // 通过DBHelper类获取一个读写的SQLiteDatabase对象
        SQLiteDatabase db = scheduleDBHelper.getWritableDatabase();
        // 参数1：table_name
        // 参数2：columns 要查询出来的列名。相当于 select  *** from table语句中的 ***部分
        // 参数3：selection 查询条件字句，在条件子句允许使用占位符“?”表示条件值
        // 参数4：selectionArgs ：对应于 selection参数 占位符的值
        // 参数5：groupby 相当于 select *** from table where && group by ... 语句中 ... 的部分
        // 参数6：having 相当于 select *** from table where && group by ...having %%% 语句中 %%% 的部分
        // 参数7：orderBy ：相当于 select  ***from ？？  where&& group by ...having %%% order by@@语句中的@@ 部分，如： personid desc（按person 降序）
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        // 将游标移到开头
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环
            if (!cursor.getString(cursor.getColumnIndex("c_day")).equals("0")) {
                Classes Class = new Classes();
                Class.setC_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("c_id"))));
                Class.setC_name(cursor.getString(cursor.getColumnIndex("c_name")));
                Class.setC_time(cursor.getString(cursor.getColumnIndex("c_time")));
                Class.setC_day(cursor.getString(cursor.getColumnIndex("c_day")));
                classes.add(Class);
            }
            // 将游标移到下一行
            cursor.moveToNext();
        }
        db.close();
        return classes;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clock:
                Intent alarm = new Intent();
                alarm.setClass(ScheduleActivity.this, SetAlarmActivity.class);
                startActivity(alarm);
                break;
            case R.id.action_menu:
                Intent intent = new Intent();
                intent.setClass(ScheduleActivity.this, AddClassActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void framework() {

        //创建一个GridLayout对象
        GridLayout gridLayout;
        //定义每个框的id，之后会动态改变id值
        int id = 1;

        //渲染每一列（周）
        for (int i = 1; i < 8; i++) {

            //注入GridLayout对应的列，根据星期几调用LayoutColumn方法
            gridLayout = LayoutColumn(i);

            //渲染每一行（节课）
            for (int j = 1; j < 10; j +=2) {//会执行5次循环，代表每天五节课，每节课两个课时
                //声明一个新的TextView
                TextView textView1 = new TextView(this);

                //给TextView设置style样式
                textView1.setId(id++);//这个对应的是每一列的id
                textView1.setText("");
                textView1.setMaxLines(5);
                textView1.setEllipsize(TextUtils.TruncateAt.END);
                textView1.setBackgroundColor(Color.parseColor("#F0FFFF"));
                textView1.setGravity(Gravity.CENTER);

                //GridLayout.LayoutParams设置在此gridLayout列中TextView布局
                GridLayout.LayoutParams params1 = new GridLayout.LayoutParams();
                params1.setMargins(5,10,5,10);
                params1.width = GridLayout.LayoutParams.MATCH_PARENT;
                params1.height = 0;
                //设置在gridLayout中的方位，参数1：在第几行。参数2：占几行。参数3：权值
                //这个权值是根据xml中第一个gridLayout节课权值设定的。
                params1.rowSpec = GridLayout.spec( j, 2,1);

                //把TextView和布局样式添加到此gridLayout中
                gridLayout.addView(textView1, params1);
            }

        }

    }

    public GridLayout LayoutColumn(int i) {

        //防止空指针操作
        GridLayout gridLayout = findViewById(R.id.d1);

        //参数i：星期几。根据i寻找xml对应的GridLayout并注入。
        switch (i) {
            case 1: {
                gridLayout = findViewById(R.id.d1);
                break;
            }
            case 2: {
                gridLayout = findViewById(R.id.d2);
                break;
            }
            case 3: {
                gridLayout = findViewById(R.id.d3);
                break;
            }
            case 4: {
                gridLayout = findViewById(R.id.d4);
                break;
            }
            case 5: {
                gridLayout = findViewById(R.id.d5);
                break;
            }
            case 6: {
                gridLayout = findViewById(R.id.d6);
                break;
            }
            case 7: {
                gridLayout = findViewById(R.id.d7);
                break;
            }
        }
        return gridLayout;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}