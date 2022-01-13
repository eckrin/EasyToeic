package com.example.toiquewordbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CallWord extends Activity {
    private RecyclerView recyclerView;
    private Wordadapter wordAdapter;
    private ArrayList<Word> arrayList;
    private LinearLayoutManager linearLayoutManager;

    private Intent getIntent;
    private Intent putIntent;
    private String day;
    String themeColor;

    private TextView wordListTitle;
    private Button btn_inv;
    private boolean btn_inv_check;
    ImageButton back_btn;


    private Context mContext;


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callwords);
        btn_inv_check= false;

        //기본 SharedPreferences 환경과 관련된 객체를 얻어옵니다.
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // SharedPreferences 수정을 위한 Editor 객체를 얻어옵니다.
        editor = preferences.edit();

        mContext=getApplicationContext();
        getIntent = getIntent();// 인텐트 받아오기
        day = getIntent.getStringExtra("day");
        getIntent= new Intent (this, Wordadapter.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getIntent.putExtra("day", day);

        //어떤 단어를 넘기는지 알려야함.
        arrayList = new ArrayList<>();

        DBQueryManager manager = new DBQueryManager(day);
        arrayList = manager.getWordList(this);

        recyclerView = findViewById(R.id.recyclerView);
        wordListTitle = findViewById(R.id.wordlist_title);
        wordListTitle.setText(day);
        back_btn = (ImageButton) findViewById(R.id.wordList_back_btn);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        wordAdapter = new Wordadapter(arrayList, day, mContext, btn_inv_check);
        recyclerView.setAdapter(wordAdapter);
        btn_inv=findViewById(R.id.button_invisible);
        btn_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //wordListTitle.setText(btn_inv_check);
                if (btn_inv_check){
                    //if button checked
                    btn_inv_check=false;
                    btn_inv.setText("뜻 가리기");
                    wordAdapter = new Wordadapter(arrayList, day,mContext, btn_inv_check);
                    recyclerView.setAdapter(wordAdapter);
                }
                else {
                    btn_inv_check=true;
                    btn_inv.setText("뜻 보이기");
                    wordAdapter = new Wordadapter(arrayList, day,mContext, btn_inv_check);
                    recyclerView.setAdapter(wordAdapter);

                }
            }

        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //fragmentTransaction.replace(R.id.frame_layout, homeFragment);
                //fragmentTransaction.commit();
            }
        });


    }

}