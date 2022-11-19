package com.lzh.school.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CardDBHelper extends SQLiteOpenHelper {
    //常量定义
    public static final String name = "db_card.db";
    public static final int DB_VERSION = 1;
    //创建表
    public static final String CREATE_CardDATA1 = "create table tb_Card(" +
            "cnumber varchar(20)primary key," +
            "cname varchar(20)," +
            "cmajor varchar(20)," +
            "cnote varchar(20))";

    public CardDBHelper(Context context) {
        super(context, name, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CardDATA1);
        db.execSQL("insert into tb_Card(cnumber,cname,cmajor,cnote)Values('111','李志豪','J软嵌2001','卡丢了呜呜')");
        db.execSQL("insert into tb_Card(cnumber,cname,cmajor,cnote)Values('222','李志','软件','卡消磁了')");
        db.execSQL("insert into tb_Card(cnumber,cname,cmajor,cnote)Values('333','荔枝','计算机','卡丢了')");
        db.execSQL("insert into tb_Card(cnumber,cname,cmajor,cnote)Values('444','张三','J软嵌2002','状态正常')");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

