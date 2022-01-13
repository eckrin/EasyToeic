package com.example.toiquewordbook;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBQueryManager {


    private String TABLE;

    public DBQueryManager(String TABLE) {
        this.TABLE = TABLE;
    }

    public static String getRandomMeans(Context context) {
        String mean;
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        String query = "SELECT kor FROM MEANS ORDER BY RANDOM() LIMIT 1";
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();

        mean = cursor.getString(0);
        return mean;
    }

    public Word getWordByKey(Context context, int num){
        Word word = null;
        int key = (num%25)+1;
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        String query = "SELECT * FROM "+TABLE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        for(int i=0; i<key; i++) {
            word = new Word(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getInt(6));
        }
        return word;
    }


    public void copyWordToMyWord(Context context, String eng) {
        Word word;
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        word = getSameEngWord(context, eng);
        String input = word.getSentence();
        String[] array = input.split("'");
        String output="";
        for(int i=0;i<array.length;i++) {
            output+=array[i];
            output+="''";
        }

        // MYWORD table에 단어 추가하고, DAY table에서도 myword 변수값을 바꿔준다
        String query = "INSERT INTO MYWORD VALUES ("+word.day+",'"+word.getEng()+"', '"+word.getEngpron()+"', '"+word.getKor()+"', '"+output+"', "+word.getCheckedState()+", "+1+")";
        db.execSQL(query);
        String query2 = "UPDATE "+TABLE+" SET myword = "+1+" WHERE eng='"+eng+"'";
        db.execSQL(query2);
        db.close();
    }

    public void deleteWordFromMyWord(Context context, String eng, int myWordDay) {
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String strFilter = "eng = '" + eng+"'";
        String DAYTABLE = "DAY_"+myWordDay;
        Log.v("dbMYWORD", DAYTABLE);
        String query = "UPDATE "+DAYTABLE+" SET myword = "+0+" WHERE eng='"+eng+"'";
        db.delete("MYWORD", strFilter, null);
        //String query = "DELETE FROM MYWORD WHERE eng = '"+eng+"'";
        db.execSQL(query);
        db.close();
    }

    public ArrayList<Word> getWordList(Context context) {
        ArrayList<Word> resultList = new ArrayList<>();

        DBOpenHelper dbHelper = new DBOpenHelper(context);
        String query = "SELECT day, eng, engpron, kor, sentence, checked, myword FROM " + TABLE + " ORDER BY eng ";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                Word word = new Word(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6));
                resultList.add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbHelper.close();
        return resultList;
    }


    public void addWord(Context context, Word word) {
        DBOpenHelper dbHelper = new DBOpenHelper(context);

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues row = new ContentValues();

            int id = 0;
            if (word.day > 0) {
                id = word.day;
            } else {

            id = getMaxWordId(context)+1;
            }

            row.put("day", id);
            row.put("eng", word.eng);
            row.put("kor", word.kor);

            db.insert(TABLE, null, row);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbHelper.close();
    }


    public void updateWord(Context context, Word word) {
        DBOpenHelper dbHelper = new DBOpenHelper(context);

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues row = new ContentValues();

//            int id = 0;
//            if (word._id > 0) {
//                id = word._id;
//            } else {

            int id = getMaxWordId(context)+1;

            row.put("day", word.day);
            row.put("eng", word.eng);
            row.put("kor", word.kor);

            String strFilter = "eng = '" + word.eng+"'";
            db.update(TABLE, row, strFilter, null);
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        dbHelper.close();
    }


    public void deleteWord(Context context, Word word) {
        DBOpenHelper dbHelper = new DBOpenHelper(context);

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String strFilter = "eng = '" + word.eng+"'";

            db.delete(TABLE, strFilter, null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbHelper.close();
    }


    public int getMaxWordId(Context context) {
        int result = 0;
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE, null);

            while (cursor.moveToNext()) {
                result = cursor.getInt(0);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbHelper.close();

//        result += 1;

        return result;
    }

    public Word getSameEngWord(Context context, String eng) {

        DBOpenHelper dbHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT day, eng, engpron, kor, sentence, checked, myword FROM ";
        Cursor cursor = db.rawQuery(query + TABLE, null);
        Word word=null;

        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(eng)) {
                word = new Word(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6));
                dbHelper.close();
                db.close();
            }
        }

        return word;
    }

    public void deleteReviewTable(Context context){
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("REVIEW", null, null);
        db.close();
        dbHelper.close();
    }

    public void copyWordToReview(Context context, String eng) {
        Word word;
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        word = getSameEngWord(context, eng);

        // 영어 예문에 작은따옴표가 있으면 sql 에러가 생기기 때문에 작은따옴표 2개로 고친다
        String input = word.getSentence();
        String[] array = input.split("'");
        String output="";
        for(int i=0;i<array.length;i++) {
            output+=array[i];
            output+="''";
        }
        Log.v("answer",output);
        String query = "INSERT INTO REVIEW(eng, engpron, kor, sentence, checked, myword) VALUES ('"+word.getEng()+"', '"+word.getEngpron()+"', '"+word.getKor()+"', '"+output+"', "+word.getCheckedState()+", "+word.getMyWordState()+")";

        db.execSQL(query);
        db.close();
        dbHelper.close();
    }

    public int getWordCnt(Context context){
        int result = 0;
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from " + TABLE, null);
            cursor.moveToFirst();
            result = cursor.getInt(0);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbHelper.close();
        return result;
    }

    public int getCheckedCnt(Context context){
        int result = 0;
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from " + TABLE + " where checked=1", null);
            cursor.moveToFirst();
            result = cursor.getInt(0);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbHelper.close();
        return result;
    }

    public void updateCheckedTable(Context context, String eng, boolean checkChecked){
        Word word= getSameEngWord(context, eng);
        DBOpenHelper dbHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String strFilter = "eng = '" + eng+"'";

        if (TABLE=="MYWORD") {
            //나만의 단어장에서 체크 변경했으면 Day의 같은 단어도 찾아서 바꿔줘야 한다
            String DAYTABLE = "DAY_" + word.day;
            String query = "UPDATE " + DAYTABLE + " SET checked = " + checkChecked + " WHERE eng='" + eng + "'";
            db.execSQL(query);
        }else {
            // Day에서 체크 변경했으면 즐겨찾기에 같은 단어가 있는지 확인하고 바꾼다
            if (word.getMyWordState()){
                String query = "UPDATE MYWORD SET checked = "+checkChecked+" WHERE eng='"+eng+"'";
                db.execSQL(query);
            }
        }
        // MYWORD, Day table의 공통 부분 (자기 자신 테이블의 checked값 변경)
        String query = "UPDATE "+TABLE+" SET checked = "+checkChecked+" WHERE eng='"+eng+"'";
        db.execSQL(query);

        db.close();
        dbHelper.close();
    }

}
