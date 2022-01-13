package com.example.toiquewordbook;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Dayadapter extends RecyclerView.Adapter<Dayadapter.CustomViewHolder> {
    private Context mContext;


    private ArrayList<dayData> arrayList;
    private Intent intent;
    private DBQueryManager manager;

    public Dayadapter(Context context, ArrayList<dayData> arrayList) {
        this.arrayList = arrayList;
        this.mContext=context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dayitem,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Dayadapter.CustomViewHolder holder, int position) {
        manager = new DBQueryManager(arrayList.get(position).getDayString());

        int checkedCnt=manager.getCheckedCnt(mContext)*100;
        int wordCnt=manager.getWordCnt(mContext);
        int percent = checkedCnt/wordCnt;
        Log.v("progress", "checkCnt"+position+" : "+ manager.getCheckedCnt(mContext)+" ");
        Log.v("progress", "wordCnt "+position+": "+ manager.getWordCnt(mContext)+" ");
        Log.v("progress", "percent "+position+": "+ percent);

        holder.day.setText(arrayList.get(position).getDayString());
        holder.itemView.setTag(position);
        holder.dailyProgressBar.setProgress((int)percent);
        holder.dailyProgressPercentage.setText(percent+"%");
    }


    @Override
    public int getItemCount() {

        return (null != arrayList ? arrayList.size():0);
    }

    public ArrayList<com.example.toiquewordbook.dayData>getArrayList(){
        return arrayList;
    }

    public void setArrayList(ArrayList<com.example.toiquewordbook.dayData>dayData){
        this.arrayList=dayData;
    }

    public void addItem(com.example.toiquewordbook.dayData data){

        arrayList.add(data);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView day;
        protected Button btn;
        protected ProgressBar dailyProgressBar;
        protected TextView dailyProgressPercentage;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.day=(TextView) itemView.findViewById(R.id.day);
            this.btn=(Button) itemView.findViewById(R.id.btn_quiz);
            this.dailyProgressBar = (ProgressBar) itemView.findViewById(R.id.daily_progressbar);
            this.dailyProgressPercentage=(TextView) itemView.findViewById(R.id.daily_progress_percentage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Handler mHandler = new Handler();
                    Thread goWordList = new Thread("Go Word List Thread"){
                        public void run(){
                            int pos = getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION){
                                Intent intent = new Intent (mContext, CallWord.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                // 어떤 날짜의 단어장 인텐트로 전달
                                intent.putExtra("day", arrayList.get(pos).getDayString());
                                mContext.startActivity(intent);
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });

                        }
                    };
                    goWordList.start();

                }
            });

            this.btn.setOnClickListener(new View.OnClickListener() {

                // 퀴즈 액티비티로 이동하는 함수
                @Override
                public void onClick(View view) {
                    Handler mHandler = new Handler();
                    Thread goQuiz = new Thread("Go to Quiz Thread"){
                        public void run(){
                            int pos = getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION){
                                Intent intent = new Intent (mContext, QuizActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                // 어떤 날짜의 퀴즈인지 퀴즈 액티비티에 넘겨야 함
                                intent.putExtra("day", arrayList.get(pos).getDay());
                                mContext.startActivity(intent);
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });

                        }
                    };
                    goQuiz.start();

                }
            }
            );
        }
    }
}