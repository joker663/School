package com.lzh.school;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.qweather.sdk.bean.Basic;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_tianqi;
    private TextView tv_wendu;
    private TextView tv_fengxiang;
    private TextView tv_fengli;

    private String weatherCountry = "http://guolin.tech/api/china/";//中国的省份城市api
    private String weatherProvince, weatherCity;//获取到的省份id和城市id
    private ArrayList<Province> provinceList;//中国的省份集合
    private ArrayList<City> cityList;//具体省份的城市集合

    private String cityId;//具体城市天气id，如湛江，"weather_id"=CN101281001

    //创建的apk 的 用户id 以及 该apk的key（包名要一致）
    private String userName = "HE2211171520351594";
    private String key = "36c18c72be1d4e978996ab4c9e151d43";//自己申请的key

    //请输入该省份的城市
    private EditText mCityEdit;
    //查询
    private Button mSearch;
    //请输入要查询的天气的省份
    private EditText mProvinceEdit;

    String province, city;//接收输入的省份和城市收用的字符串
    private LinearLayout mWeatherInfo;
    private TextView weatherresult;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
//        使用 SDK 时，需提前进行账户初始化（全局执行一次即可）
        HeConfig.init(userName, key);
//        个人开发者、企业开发者、普通用户等所有使用免费数据的用户需要切换到免费服务域名，s6到s7更新了，可以自己去官方文档看
        HeConfig.switchToDevService();
    }

    private void initView() {
        mCityEdit = (EditText) findViewById(R.id.city_edit);
        mSearch = (Button) findViewById(R.id.search);
        mSearch.setOnClickListener(this);
        mProvinceEdit = (EditText) findViewById(R.id.province_edit);
        mWeatherInfo = (LinearLayout) findViewById(R.id.weather_info);
        weatherresult = (TextView) findViewById(R.id.findweatherresult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                province = mProvinceEdit.getText().toString().trim();
                city = mCityEdit.getText().toString().trim();
                queryWeather();
                break;
            default:
                break;
        }
    }

    private void queryWeather() {
        provinceList = new ArrayList<Province>();//省份集合
        cityList = new ArrayList<City>();//具体省份的城市集合
        new Thread() {
            @Override
            public void run() {
                try {
                    //weatherCountry = "http://guolin.tech/api/china/"
                    URL url = new URL(weatherCountry);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();//开启一个url的连接，用HttpURLConnection连接方式处理
                    connection.setRequestMethod("GET");//设置连接对象的请求数据的方式
                    connection.setConnectTimeout(3000);//设置连接对象的请求超时的时间

                    //将请求返回的数据流转换成字节输入流对象
                    InputStream is = connection.getInputStream();
                    //将字节输入流对象转换成字符输入流对象
                    InputStreamReader isr = new InputStreamReader(is);
                    //创建字符输入缓冲流对象
                    BufferedReader br = new BufferedReader(isr);
                    StringBuffer sb = new StringBuffer();
                    String string;

                    //读文本
                    while ((string = br.readLine()) != null) {
                        sb.append(string);
                    }
                    String result = sb.toString();

                    Log.d("MainActivity", "" + result);//查看获取的所有省份
                    JSONArray provinceArray = new JSONArray(result);
                    for (int i = 0; i < provinceArray.length(); i++) {
                        JSONObject provinceInfo = provinceArray.getJSONObject(i);//获取每个省份信息
                        Province provinceBean = new Province();//创建省份实体类对象
                        Gson gson = new Gson();//创建Gson解析对象
                        //反序例化，将json数据转化为实体类对象的成员变量值
                        provinceBean = gson.fromJson(provinceInfo.toString(), Province.class);

                        //添加保存好的省份对象数据进入省份集合
                        provinceList.add(provinceBean);
                    }

                    for (Province pro : provinceList) {
                        //如果该省份为用户输入的省份
                        if (pro.getName().equals(province)) {
                            //则拼接链接
                            //如：北京 weatherProvince = "http://guolin.tech/api/china/1/"
                            weatherProvince = weatherCountry + pro.getId() + "/";//将输入的省份拼接到获取的省份集合里面
                        }
                    }

                    Log.d("WeatherProvince", "" + weatherProvince);//打印获取的省份

                    //如：北京 weatherProvince = "http://guolin.tech/api/china/1/"
                    url = new URL(weatherProvince);
                    connection = (HttpURLConnection) url.openConnection();//开启一个url的连接用HttpURLConnection连接方式处理
                    connection.setRequestMethod("GET");//设置连接对象的请求数据的方式
                    connection.setConnectTimeout(3000);//设置连接对象的请求超时的时间

                    //将请求返回的数据流转换成字节输入流对象
                    is = connection.getInputStream();
                    //将字节输入流对象转换成字符输入流对象
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);

                    StringBuffer sb2 = new StringBuffer();
                    //读文本
                    while ((string = br.readLine()) != null) {
                        sb2.append(string);
                    }
                    String result2 = sb2.toString();

                    Log.d("MainActivity2", "" + result2);//获取该省份的所有城市

                    JSONArray cityArray = new JSONArray(result2);
                    for (int i = 0; i < cityArray.length(); i++) {
                        JSONObject cityInfo = cityArray.getJSONObject(i);//获取具体省份城市信息
                        City cityBean = new City();//创建城市实体类对象
                        Gson gson = new Gson();//创建Gson解析对象
                        //反序例化，将json数据转化为实体类对象的成员变量值
                        cityBean = gson.fromJson(cityInfo.toString(), City.class);
                        //添加保存好的城市对象数据进入城市集合
                        cityList.add(cityBean);
                    }

                    for (City c : cityList) {
                        //如果该城市为用户输入的城市
                        if (c.getName().equals(city)) {
                            //则拼接链接
                            //如：北京 weatherCity = "http://guolin.tech/api/china/1/1/"
                            weatherCity = weatherProvince + c.getId() + "/";//拼接城市
                        }
                    }

                    Log.d("WeatherCity", ""+weatherCity);//打印所查询省份下的城市天气

                    //如：北京 weatherCity = "http://guolin.tech/api/china/1/1/"
                    url = new URL(weatherCity);//最终拼接的链接，用于获取城市的天气
                    connection = (HttpURLConnection) url.openConnection();//开启一个url的连接用HttpURLConnection连接方式处理
                    connection.setRequestMethod("GET");//设置连接对象的请求数据的方式
                    connection.setConnectTimeout(3000);//设置连接对象的请求超时的时间

                    //将请求返回的数据流转换成字节输入流对象
                    is = connection.getInputStream();
                    //将字节输入流对象转换成字符输入流对象
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);

                    StringBuffer sb3 = new StringBuffer();
                    //读文本
                    while ((string = br.readLine()) != null) {
                        sb3.append(string);
                    }

                    String result3 = sb3.toString();

                    Log.d("MainActivity3", "" + result3);//城市下面的地级市

                    JSONArray jsonArray = new JSONArray(result3);
                    JSONObject cityIdInfo = jsonArray.getJSONObject(0);
                    cityId=cityIdInfo.getString("weather_id");

                    queryWeather2();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void queryWeather2(){
        /**
         * @param context  上下文
         * @param location 地址详解
         * @param lang     多语言，默认为简体中文，海外城市默认为英文
         * @param unit     单位选择，公制（m）或英制（i），默认为公制单位
         * @param listener 网络访问回调接口
         */
        QWeather.getWeatherNow(WeatherActivity.this, cityId, Lang.ZH_HANS , Unit.METRIC , new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "Weather Now onError: ", e);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(WeatherNowBean weatherBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));

                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK == weatherBean.getCode()) {
                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();

                    String tianqi=now.getText();
                    String wendu=now.getTemp()+" ℃";
                    String fengli=now.getWindScale();
                    String fengxiang=now.getWindDir();
                    String feelsLike = now.getFeelsLike()+" ℃";

                    weatherresult.setText("当前天气："+tianqi+"\n" +
                            "当前温度："+wendu+"\n"+
                            "当前风力："+fengli+"级"+"\n"+
                            "当前风向："+fengxiang+"\n"+
                            "体感温度："+feelsLike+"\n");

                    Message message=new Message();
                    message.what=1;
                    WeatherActivity.this.myHandler.sendMessage(message);

                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherBean.getCode();
                    System.out.println("失败代码: " + code);
                    //Log.i(TAG, "failed code: " + code);
                }
            }

        });
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    mWeatherInfo.setVisibility(View.VISIBLE);
                    weatherProvince = "";
                    weatherCity = "";
                    break;
            }
            super.handleMessage(msg);
        }
    };

}