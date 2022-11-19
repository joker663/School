package com.lzh.school.card;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lzh.school.R;
import com.lzh.school.bean.Card;
import com.lzh.school.db.CardSQLiteOpenHelper;


public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    //组件定义
    private EditText cardNumber;
    private EditText cardName;
    private EditText cardMajor;
    private EditText cardNote;

    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cardinfo);
        //初始化界面
        initView();
    }

    //初始化界面
    private void initView() {
        cardNumber=(EditText)findViewById(R.id.card_number);
        cardName = (EditText) findViewById(R.id.card_name);
        cardMajor = (EditText) findViewById(R.id.card_major);
        cardNote = (EditText) findViewById(R.id.card_note);

        btnAdd = (Button) findViewById(R.id.card_publish);
        //设置按钮的点击事件
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //当单击“添加”按钮时，获取添加信息
        String card_Number=cardNumber.getText().toString().trim();
        String card_Name = cardName.getText().toString().trim();
        String card_Major = cardMajor.getText().toString().trim();
        String card_Note = cardNote.getText().toString();

        //检验信息是否正确
        if (TextUtils.isEmpty(card_Number)) {
            Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(card_Name)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(card_Major)) {
            Toast.makeText(this, "请输入专业", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(card_Note)) {
            Toast.makeText(this, "请输入描述", Toast.LENGTH_SHORT).show();
            return;
        }

        //添加信息
        Card card =new Card();
        card.cnumber= card_Number;
        card.cname = card_Name;
        card.cmajor = card_Major;
        card.cnote= card_Note;

        //创建数据库访问对象
        CardSQLiteOpenHelper dao = new CardSQLiteOpenHelper(getApplicationContext());
        dao.open();
        long result = dao.addCard(card);

        if (result > 0) {
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            //跳转到详细信息页面
            Intent intent = new Intent(AddCardActivity.this, com.lzh.school.card.ListCardActivity.class);
            startActivity(intent);
            AddCardActivity.this.finish();
        } else {
            Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
        }
        dao.close();
        finish();
    }
}