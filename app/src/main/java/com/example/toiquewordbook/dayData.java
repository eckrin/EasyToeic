package com.example.toiquewordbook;

import android.widget.Button;

// 기존 getDay -> getDayString으로 변경. 생성자에서 string아니고 int로 받음

public class dayData {

    private String dayString;
    private int day;

    public dayData(int day) {
        this.day=day;
        this.dayString = "DAY_"+day;
    }

    public String getDayString() {
        return dayString;
    }

    public int getDay() {
        return day;
    }
}