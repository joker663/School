package com.lzh.school.card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lzh.school.R;
import com.lzh.school.bean.Card;
import com.lzh.school.db.CardSQLiteOpenHelper;


public class CardManageActivity extends AppCompatActivity implements View.OnClickListener{
    //组件定义
    private EditText cardNumber;
    private EditText cardName;
    private EditText cardMajor;
    private EditText cardNote;

    private Button btnSearch;
    private Button btnGuaSi;
    private Button btnPublish;
    private Button btnList;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cardinfo);
        initView();
    }

    private void initView() {
        cardNumber = findViewById(R.id.guasi_number);
        cardName = findViewById(R.id.guasi_name);
        cardMajor = findViewById(R.id.guasi_major);
        cardNote = findViewById(R.id.guasi_note);

        btnSearch = findViewById(R.id.guasi_find);
        btnSearch.setOnClickListener((View.OnClickListener) this);

        btnGuaSi=  findViewById(R.id.guasi_btn);
        btnGuaSi.setOnClickListener((View.OnClickListener) this);

        btnPublish = findViewById(R.id.guasi_publish);
        btnPublish.setOnClickListener(this);

        btnList = findViewById(R.id.guasi_list);
        btnList.setOnClickListener(this);

        btnDelete = findViewById(R.id.guasi_delete);
        btnDelete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.guasi_find:
                findOrder();
                break;
            case R.id.guasi_btn:
                guaSiCard();
                break;
            case R.id.guasi_publish:
                Intent intent_publish = new Intent();
                intent_publish.setClass(CardManageActivity.this, AddCardActivity.class);
                startActivity(intent_publish);
                break;
            case R.id.guasi_list:
                Intent intent_list = new Intent();
                intent_list.setClass(CardManageActivity.this, ListCardActivity.class);
                startActivity(intent_list);
                break;
            case R.id.guasi_delete:
                Intent intent_delete = new Intent();
                intent_delete.setClass(CardManageActivity.this, DeleteCardActivity.class);
                startActivity(intent_delete);
                break;
        }
    }
    private void findOrder() {
        String cnum=cardNumber.getText().toString().trim();
        CardSQLiteOpenHelper dao=new CardSQLiteOpenHelper(getApplicationContext());
        dao.open();
        Card card=dao.getCardInfo(cnum);
        cardName.setText(card.cname);
        cardMajor.setText(card.cmajor);
        cardNote.setText(card.cnote);

        dao.close();
    }

    private void guaSiCard() {
        Card card=new Card();
        card.cnumber=cardNumber.getText().toString().trim();
        card.cname=cardName.getText().toString().trim();
        card.cmajor=cardMajor.getText().toString().trim();
        card.cnote=cardNote.getText().toString().trim();

        CardSQLiteOpenHelper dao=new CardSQLiteOpenHelper(getApplicationContext());
        dao.open();
        long result= dao.guasi(card);
        if(result>0) {
            Toast.makeText(this, "挂失成功", Toast.LENGTH_SHORT).show();
            //跳转到所有挂失，详细信息页面
            Intent intent = new Intent(CardManageActivity.this, ListCardActivity.class);
            startActivity(intent);
            CardManageActivity.this.finish();
        }else{
            Toast.makeText(this, "挂失失败", Toast.LENGTH_SHORT).show();
        }
        dao.close();
    }
}
