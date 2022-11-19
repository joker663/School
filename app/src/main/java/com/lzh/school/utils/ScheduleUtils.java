package com.lzh.school.utils;

public class ScheduleUtils {

    public static int getDay(String day) {
        int j = 0;
        switch (day) {
            case "星期一":{
                j = 1;
                break;
            }
            case "星期二":{
                j = 2;
                break;
            }
            case "星期三":{
                j = 3;
                break;
            }
            case "星期四":{
                j = 4;
                break;
            }
            case "星期五":{
                j = 5;
                break;
            }
            case "星期六":{
                j = 6;
                break;
            }
            case "星期日":{
                j = 7;
                break;
            }
        }
        return j;
    }

}
