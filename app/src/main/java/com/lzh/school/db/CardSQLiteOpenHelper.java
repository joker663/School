package com.lzh.school.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.lzh.school.bean.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardSQLiteOpenHelper {

    private Context context;
    private com.lzh.school.db.CardDBHelper dbHelper;
    private SQLiteDatabase db;

    //构造函数
    public CardSQLiteOpenHelper(Context context) {
        this.context = context;
    }

    //打开数据库方法
    public void open() throws SQLiteException {
        dbHelper = new com.lzh.school.db.CardDBHelper(context);
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

    //添加发布信息内容
    public long addCard(Card card) {
        // 创建ContentValues对象
        ContentValues values = new ContentValues();
        // 向该对象中插入值
        values.put("cnumber", card.cnumber);
        values.put("cname", card.cname);
        values.put("cmajor", card.cmajor);
        values.put("cnote", card.cnote);

        // 通过insert()方法插入数据库中
        return db.insert("tb_Card", null, values);
    }

    //删除挂失信息
    public int deletCardInfo(Card card) {
        return db.delete("tb_Card", "cnumber=?", new String[]{String.valueOf(card.cnumber)});
    }

    //修改挂失信息
    public int guasi(Card card) {
        ContentValues value = new ContentValues();
        value.put("cname", card.cname);
        value.put("cmajor", card.cmajor);
        value.put("cnote", card.cnote);
        return db.update("tb_Card", value, "cnumber=?", new String[]{String.valueOf(card.cnumber)});
    }

    //根据学号查找已发布内容
    @SuppressLint("Range")
    public Card getCardInfo(String cnumber) {
        Cursor cursor = db.query("tb_Card", null, "cnumber=?", new String[]{cnumber}, null, null, null);
        Card card = new Card();
        while (cursor.moveToNext()) {
            card.cnumber = cursor.getString(cursor.getColumnIndex("cnumber"));
            card.cname = cursor.getString(cursor.getColumnIndex("cname"));
            card.cmajor = cursor.getString(cursor.getColumnIndex("cmajor"));
            card.cnote = cursor.getString(cursor.getColumnIndex("cnote"));

        }
        return card;
    }

    //查找所有发布信息
    @SuppressLint("Range")
    public ArrayList<Map<String, Object>> getCardList() {
        ArrayList<Map<String, Object>> listCard = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.query("tb_Card", null, null, null, null, null,null);

        int resultCounts = cursor.getCount();
        if (resultCounts == 0 ) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cnumber", cursor.getString(cursor.getColumnIndex("cnumber")));
                map.put("cname", cursor.getString(cursor.getColumnIndex("cname")));
                map.put("cmajor", cursor.getString(cursor.getColumnIndex("cmajor")));
                map.put("cnote", cursor.getString(cursor.getColumnIndex("cnote")));

                listCard.add(map);
            }
            return listCard;
        }
    }
}

