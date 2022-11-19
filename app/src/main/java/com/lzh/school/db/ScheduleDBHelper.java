package com.lzh.school.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ScheduleDBHelper extends SQLiteOpenHelper {

    public ScheduleDBHelper(@Nullable Context context,
                            @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table classes_db" +
                "(c_id Integer not null primary key autoincrement," +
                " c_name varchar(50) not null," +
                " c_time varchar(50) not null," +
                " c_day varchar(50) not null," +
                " c_teacher varchar(50) not null)");

        // 创建ContentValue设置参数
        ContentValues contentValues = new ContentValues();

        //星期几
        contentValues.put("c_day", "0");
        //第几节课
        contentValues.put("c_time", "0");
        //课程名字
        contentValues.put("c_name", "0");
        //任课教师
        contentValues.put("c_teacher", "0");

        //插入id 1-36 个课程数据，以便添加课程
        for (int i = 1; i < 37; i++) {
            contentValues.put("c_id", i);
            sqLiteDatabase.insert("classes_db", "", contentValues);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}