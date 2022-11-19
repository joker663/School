package com.lzh.school.bean;

public class Classes {

    private int c_id;
    private String c_name;
    private String c_time;
    private String c_day;

    @Override
    public String toString() {
        return "Classes{" +
                "c_id=" + c_id +
                ", c_name='" + c_name + '\'' +
                ", c_time='" + c_time + '\'' +
                ", c_day='" + c_day + '\'' +
                '}';
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getC_day() {
        return c_day;
    }

    public void setC_day(String c_day) {
        this.c_day = c_day;
    }
}
