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


public class DeleteCardActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText delNumber;
    private EditText delName;
    private EditText delMajor;
    private EditText delNote;

    private Button btnSearch;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cardinfo);
        initView();
    }

    private void initView() {
        delNumber=(EditText) findViewById(R.id.del_number);
        delName=(EditText)findViewById(R.id.del_name);
        delMajor=(EditText)findViewById(R.id.del_major);
        delNote=(EditText)findViewById(R.id.del_note);

        btnSearch=(Button) findViewById(R.id.del_search);
        btnDelete= (Button) findViewById(R.id.del_btn);

        btnSearch.setOnClickListener((View.OnClickListener) this);
        btnDelete.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.del_search:
                findCard();
                break;
            case R.id.del_btn:
                deleteCard();
                break;
        }
    }
    //根据学生学号，查找学生信息
    private void findCard() {
        String studentid=delNumber.getText().toString().trim();
        CardSQLiteOpenHelper dao=new CardSQLiteOpenHelper(getApplicationContext());
        dao.open();
        Card card=dao.getCardInfo(studentid);
        delName.setText(card.cname);
        delMajor.setText(card.cmajor);
        delNote.setText(card.cnote);

        dao.close();
    }
    private void deleteCard() {
        Card card=new Card();
        card.cnumber=delNumber.getText().toString().trim();
        CardSQLiteOpenHelper dao=new CardSQLiteOpenHelper(getApplicationContext());
        dao.open();
        int result= dao.deletCardInfo(card);
        if(result>0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            //跳转到详细信息页面
            Intent intent = new Intent(DeleteCardActivity.this, com.lzh.school.card.CardManageActivity.class);
            startActivity(intent);
            DeleteCardActivity.this.finish();
        }else{
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
        dao.close();
    }
}
