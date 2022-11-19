package com.lzh.school.manage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.lzh.school.R;
import com.lzh.school.bean.Student;
import com.lzh.school.db.StudentSQLiteOpenHelper;

public class EduSystemActivity extends AppCompatActivity {
    //组件定义
    private EditText stuNumber;
    private EditText stuName;
    private EditText stuMajor;
    private EditText stuScore;

    private EditText stuExam1;
    private EditText stuExam2;
    private EditText stuExam3;
    private EditText stuExam4;
    private EditText stuExam5;

    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu);
        initView();
    }

    private void initView() {
        stuNumber = findViewById(R.id.stu_number);
        stuName = findViewById(R.id.stu_name);
        stuMajor = findViewById(R.id.stu_major);
        stuScore = findViewById(R.id.stu_score);

        stuExam1 = findViewById(R.id.exam_score01);
        stuExam2 = findViewById(R.id.exam_score02);
        stuExam3 = findViewById(R.id.exam_score03);
        stuExam4 = findViewById(R.id.exam_score04);
        stuExam5 = findViewById(R.id.exam_score05);

        btnSearch = findViewById(R.id.stu_search);
        btnSearch.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                searchStudent();
            }
        });

    }

    private void searchStudent() {
        String stuNum=stuNumber.getText().toString().trim();
        StudentSQLiteOpenHelper dao=new StudentSQLiteOpenHelper(getApplicationContext());
        dao.open();
        Student student=dao.getStudent(stuNum);//看看这个Get方法
        stuName.setText(student.stuName);
        stuMajor.setText(student.stuMajor);
        stuScore.setText(student.stuScore);

        stuExam1.setText(student.stuExam1);
        stuExam2.setText(student.stuExam2);
        stuExam3.setText(student.stuExam3);
        stuExam4.setText(student.stuExam4);
        stuExam5.setText(student.stuExam5);

        dao.close();
    }

}
