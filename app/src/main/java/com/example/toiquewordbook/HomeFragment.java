package com.example.toiquewordbook;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.GregorianCalendar;
import java.util.Random;

public class HomeFragment extends Fragment implements View.OnClickListener{

    public boolean notify=false;

    private ImageButton settings;
    private TextView daily_word;  // 홈화면 오늘의 영단어
    private TextView daily_meaning; // 홈화면 오늘의 영단어 뜻
    private ProgressBar total_progressbar; // 홈화면 학습진행율
    private TextView progress_percentage; // 홈화면 학습진행율 텍스트뷰

    private Random random = new Random();
    private DBQueryManager manager;
    private GregorianCalendar gregorianCalendar = new GregorianCalendar();

    public HomeFragment(){

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        settings = (ImageButton) v.findViewById(R.id.settings);
        daily_word = (TextView) v.findViewById(R.id.daily_word);
        daily_meaning = (TextView) v.findViewById(R.id.daily_meaning);
        total_progressbar = (ProgressBar) v.findViewById(R.id.total_progressbar);
        progress_percentage = (TextView) v.findViewById(R.id.progress_percentage);

        setDaily();

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        setTotalProgressbar();

    }

    // 오늘의 단어 (영어, 뜻) 설정

    // 오늘의 단어 (영어, 뜻) 설정
    public void setDaily(){
        int date = gregorianCalendar.get(gregorianCalendar.DAY_OF_MONTH);
        int key = (date%12)+1;
        manager = new DBQueryManager("DAY_"+key);
        Word target = manager.getWordByKey(getContext(), key);

        daily_word.setText(target.getEng());
        daily_meaning.setText(target.getKor());
    }

    // 전체 학습률 설정
    public void setTotalProgressbar(){
        Handler mHandler = new Handler();
        Thread calcThread = new Thread("Calc Thread"){
            public void run(){
                int value=calcRate(getContext());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 현재까지 카운트 된 수를 텍스트뷰에 출력한다.
                        total_progressbar.setProgress(value);
                        progress_percentage.setText(Integer.toString(value)+"%");
                    }
                });

            }
        };
        calcThread.start();
    }

    public int calcRate(Context context) {
        // 학습완료(체크) 단어개수, 전체 단어 개수
        // 전체 테이블 확인
        int checkedCnt=0, wordCnt=0;
        double res;
        DBQueryManager db;
        for (int i=1; i<=10; i++){
            String table = "DAY_" + i;
            db = new DBQueryManager(table);
            wordCnt+=db.getWordCnt(context);
            checkedCnt+=db.getCheckedCnt(context);
        }
        checkedCnt*=100;
        return checkedCnt/wordCnt;
    }

    @Override
    public void onClick(View view) {

    }


}