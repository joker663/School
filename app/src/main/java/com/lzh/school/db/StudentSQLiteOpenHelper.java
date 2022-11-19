package com.lzh.school.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.lzh.school.bean.Student;

public class StudentSQLiteOpenHelper {

    private Context context;
    private com.lzh.school.db.StudentDbHelper dbHelper;
    private SQLiteDatabase db;

    //构造函数
    public StudentSQLiteOpenHelper(Context context) {
        this.context = context;
    }

    //打开数据库方法
    public void open() throws SQLiteException {
        dbHelper = new com.lzh.school.db.StudentDbHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    //关闭数据库方法
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    //根据学号查找学生信息
    @SuppressLint("Range")
    public Student getStudent(String stuNumber) {
        Cursor cursor = db.query("tb_students", null, "stuNumber=?", new String[]{stuNumber}, null, null, null);
        Student student = new Student();
        while (cursor.moveToNext()) {
            student.stuNumber = cursor.getString(cursor.getColumnIndex("stuNumber"));
            student.stuName = cursor.getString(cursor.getColumnIndex("stuName"));
            student.stuMajor = cursor.getString(cursor.getColumnIndex("stuMajor"));
            student.stuScore = cursor.getString(cursor.getColumnIndex("stuScore"));

            student.stuExam1 = cursor.getString(cursor.getColumnIndex("stuExam1"));
            student.stuExam2 = cursor.getString(cursor.getColumnIndex("stuExam2"));
            student.stuExam3 = cursor.getString(cursor.getColumnIndex("stuExam3"));
            student.stuExam4 = cursor.getString(cursor.getColumnIndex("stuExam4"));
            student.stuExam5 = cursor.getString(cursor.getColumnIndex("stuExam5"));

        }
        return student;
    }
}