package com.lzh.school.card;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lzh.school.R;
import com.lzh.school.db.CardSQLiteOpenHelper;

import java.util.List;
import java.util.Map;

public class ListCardActivity extends AppCompatActivity {
    //定义组件
    ListView listView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_cardinfo);
        //初始化界面
        initView();
    }

    private void initView() {
        CardSQLiteOpenHelper dao=new CardSQLiteOpenHelper(getApplicationContext());
        dao.open();
        List<Map<String,Object>> cardListData=dao.getCardList();
        listView=(ListView)findViewById(R.id.lst_students);
        String[] from={"cnumber","cname","cmajor","cnote"};
        int[] to={R.id.item_number,R.id.item_name,R.id.item_major,R.id.item_note};
        SimpleAdapter listItemAdapter=new SimpleAdapter(ListCardActivity.this,cardListData,R.layout.item_list,from,to);
        listView.setAdapter(listItemAdapter);
        dao.close();
    }
}
