package com.example.toiquewordbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private ExamFragment examFragment;
    private ReviewFragment reviewFragment;
    private ImageButton back_btn;
    private int day;

    public TextView quizTitle;
    public int score=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        examFragment= new ExamFragment();
        reviewFragment= new ReviewFragment();
        quizTitle = findViewById(R.id.quiz_title);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.quiz_frame_layout, examFragment).commitAllowingStateLoss();

        Intent intent = getIntent();

        day = intent.getExtras().getInt("day");

        back_btn = (ImageButton) findViewById(R.id.back_btn_quiz);

        //뒤로가기 버튼
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void changeFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.quiz_frame_layout, reviewFragment).commit();
    }

    public int getDay(){
        return day;
    }

}
