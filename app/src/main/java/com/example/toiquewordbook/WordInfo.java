package com.example.toiquewordbook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class WordInfo extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordinfo);

        TextView eng = findViewById(R.id.eng);
        TextView engpron = findViewById(R.id.engpron);
        TextView kor = findViewById(R.id.kor);
        TextView sentence = findViewById(R.id.sentence);

        Word targetWord;

        Intent intent = getIntent();
        DBQueryManager checkedDBManager = new DBQueryManager(intent.getStringExtra("table"));
        targetWord = checkedDBManager.getSameEngWord(this, intent.getStringExtra("WORDINFO"));
        Log.d("day_WORDINFO", intent.getStringExtra("table"));

        eng.setText(targetWord.getEng());
        engpron.setText("["+targetWord.getEngpron()+"]");
        kor.setText(targetWord.getKor());
        sentence.setText(targetWord.getSentence());

    }
}
