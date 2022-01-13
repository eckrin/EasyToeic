package com.example.toiquewordbook;

import android.util.Log;

public class Word {

    int day;
    String eng;
    String kor;
    String engpron;
    String sentence;
    int checked;
    int myword;

    public Word(int day, String eng, String engpron, String kor, String sentence, int checked, int myword) {
        this.day = day;
        this.eng = eng;
        this.kor = kor;
        this.engpron = engpron;
        this.sentence = sentence;
        this.checked = checked;
        this.myword = myword;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getKor() {
        return kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public String getEngpron() {
        return engpron;
    }

    public void setEngpron(String engpron) {
        this.engpron = engpron;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public boolean getCheckedState(){
        if (checked==1) return true;
        return false;
    }

    public void setCheckedState(int checked){
        this.checked = checked;
    }

    public boolean getMyWordState(){
        if (myword ==1) return true;
        return false;
    }

    public void setMyWordState(int myword){
        this.myword = myword;
    }

}
