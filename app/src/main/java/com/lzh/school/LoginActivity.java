package com.lzh.school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzh.school.MainActivity;
import com.lzh.school.R;


public class  LoginActivity extends AppCompatActivity {
    //声明所有按钮
    private Button login;
    private TextView tv_register;
    private EditText et_username,et_pwd;
    private String userName,passWord,spPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init() {
        //给所有按钮匹配id
        et_username = (EditText) findViewById(R.id.username);
        et_pwd = (EditText) findViewById(R.id.pwd);
        login = (Button)findViewById(R.id.loginBtn);
        tv_register = (TextView) findViewById(R.id.register);

        //登录方法
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                // 定义方法 readPsw为了读取用户名，得到密码
                spPsw = readPsw(userName);
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText( LoginActivity.this, "请输入学号", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(passWord)){
                    Toast.makeText( LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断密码是否与注册的一致
                }else if(passWord.equals(spPsw)){
                    //登录成功后关闭此页面进入主页
                    Intent data = new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    //关闭登录界面
                    LoginActivity.this.finish();
                    //跳转到下一个界面
                    startActivity(new Intent( LoginActivity.this, MainActivity.class));
                    return;
                    //如果用户名或者账号错误会提示错误
                }else if((spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!passWord.equals(spPsw))){
                    Toast.makeText( LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText( LoginActivity.this, "学生不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                Intent intent=new Intent( LoginActivity.this, com.lzh.school.RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

    }
    //获取用户名和密码
    private void getEditString(){
        userName = et_username.getText().toString().trim();
        passWord = et_pwd.getText().toString().trim();
    }
    //从已经存入的对象中读取密码
    private String readPsw(String userName){
        SharedPreferences sp  = getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName , "");
    }
    //返回注册成功数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                et_username.setText(userName);
                et_username.setSelection(userName.length());
            }
        }
    }
    public void onBackPressed() {
        LoginActivity.this.finish();
    }
}
