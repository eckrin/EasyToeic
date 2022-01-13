package com.example.toiquewordbook;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static String TAG = "DataBaseHelper";
    private SQLiteDatabase mDatabase;
    private final Context mContext;
    private String mFolderPath = "";
    private final static String mDBFileName = "eng_word.db";
    private final static int mDBVersion = 1;


    public DBOpenHelper(Context context) {
        super(context, mDBFileName, null, mDBVersion);

        Log.e(TAG, "openhelper called");
        mFolderPath = context.getApplicationInfo().dataDir + "/databases/";;
        this.mContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG,"onCreate()");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG,"onUpgrade()");
    }


    //데이터베이스가 없는지 확인해서 없으면 asset 폴더에서 복사해온다.
//    public void createDatabase() {
//        Log.e(TAG, "Hello~");
//
//        boolean mDataBaseExist = checkDatabase();
//        if(!mDataBaseExist) {
//            this.getReadableDatabase();
//            this.close();
////            try {
//                //assets 에서 복사
//                copyDatabase();
//                Log.e(TAG, "database created");
////            } catch (IOException mIOException) {
////                throw new Error("ErrorCopyingDataBase");
////            }
//        }
//    }

    //경로(/data/data/package/databases/ 에 데이터베이스가 존재하는지 확인한다
    //존재하면 true, 아니면 false return



    private void checkFolderExist() throws IOException {
        File targetFolder = new File(mFolderPath);
        if(!targetFolder.exists()) {
            Log.e(TAG, "Folder not exist");
            targetFolder.mkdirs();
        }
    }

    //db존재유뮤 체크해서 없으면 복사해옴
    private void checkFileExist() throws IOException {
        File targetFile = new File(mFolderPath + mDBFileName);
        if(!targetFile.exists()) {
            Log.e(TAG, "File not exist");
            copyDatabase();
        }
    }

    private void checkDatabase() {
        try {
            checkFolderExist();
            checkFileExist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // precondition : db파일이 존재하지 않음
    // assets 폴더에서 파일 복사
    private void copyDatabase()
    {

        try
        {
            InputStream inputStream = mContext.getAssets().open(mDBFileName); //여기서 ioexception.
            String out_filename = mFolderPath + mDBFileName;
            OutputStream outputStream = new FileOutputStream(out_filename);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = inputStream.read(mBuffer)) > 0)
            {
                outputStream.write(mBuffer, 0, mLength);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.e(TAG, "DB Copy Finished");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //DB를 열어서 쿼리를 사용가능하게 한다.
    public void openDataBase() throws SQLException {
        Log.e(TAG, "openDB");
        mDatabase = SQLiteDatabase.openDatabase(mFolderPath + mDBFileName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //SQLiteDatabase.NO_LOCALIZED_COLLATORS 면 오류발생해서 바꿈
        Log.e(TAG, "database opened");
//        return mDatabase != null;
    }

    @Override
    public synchronized void close() {
        if(mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        checkDatabase();
        openDataBase();

        return mDatabase;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        Log.e(TAG, "writeableDB");
        checkDatabase();
        openDataBase();

        return mDatabase;
    }
}
