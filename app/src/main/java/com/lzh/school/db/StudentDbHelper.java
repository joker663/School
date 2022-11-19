package com.lzh.school.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDbHelper extends SQLiteOpenHelper {

    //定义学生表
    public static final String DB_NAME = "tb_students";
    public static final int DB_VERSION = 1;
    //创建表
    private static final String CREATE_STUDENTS_DB = "create table tb_students(" +
            "stuNumber varchar(22)primary key," +
            "stuName varchar(22)," +
            "stuMajor varchar(22)," +
            "stuScore varchar(22)," +
            "stuExam1 varchar(32)," +
            "stuExam2 varchar(32)," +
            "stuExam3 varchar(32)," +
            "stuExam4 varchar(32)," +
            "stuExam5 varchar(32))";

    public StudentDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENTS_DB);
        db.execSQL("insert into tb_students(stuNumber,stuName,stuMajor,stuScore,stuExam1,stuExam2,stuExam3,stuExam4,stuExam5)" +
                "Values('111','李志豪','J软嵌2001','3.2','Java程序设计    90','数据结构    85','数据库    80','安卓编程    86','操作系统    84')");
        db.execSQL("insert into tb_students(stuNumber,stuName,stuMajor,stuScore,stuExam1,stuExam2,stuExam3,stuExam4,stuExam5)" +
                "Values('222','李志','J软嵌2002','2.2','Java程序设计    89','数据结构    75','数据库    83','安卓编程    81','操作系统    74')");
        db.execSQL("insert into tb_students(stuNumber,stuName,stuMajor,stuScore,stuExam1,stuExam2,stuExam3,stuExam4,stuExam5)" +
                "Values('333','荔枝','J软嵌2003','3.1','Java程序设计    79','数据结构    95','数据库    87','安卓编程    84','操作系统    64')");
        db.execSQL("insert into tb_students(stuNumber,stuName,stuMajor,stuScore,stuExam1,stuExam2,stuExam3,stuExam4,stuExam5)" +
                "Values('444','张三','J软嵌2001','3.5','Java程序设计    80','数据结构    65','数据库    90','安卓编程    85','操作系统    94')");
        db.execSQL("insert into tb_students(stuNumber,stuName,stuMajor,stuScore,stuExam1,stuExam2,stuExam3,stuExam4,stuExam5)" +
                "Values('555','李四','J软嵌2004','3.0','Java程序设计    70','数据结构    85','数据库    60','安卓编程    76','操作系统    75')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

