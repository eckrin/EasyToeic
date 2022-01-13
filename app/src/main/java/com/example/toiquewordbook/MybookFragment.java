package com.example.toiquewordbook;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import java.util.ArrayList;

public class MybookFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Wordadapter adapter;
    private ArrayList<Word> list;
    private DBQueryManager checkedWordList = new DBQueryManager("MYWORD");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mybook, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        list = checkedWordList.getWordList(getContext());
        recyclerView.setHasFixedSize(true);

        adapter = new Wordadapter(list, "MYWORD", getContext(),false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);



        Log.e("Frag", "MainFragment");
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }
}