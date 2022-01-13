package com.example.toiquewordbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class WordbookFragment extends Fragment {

    private ArrayList<dayData> arrayList;
    private Dayadapter dayadapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    boolean check = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wordbook,container,false);

        super.onCreate(savedInstanceState);

        recyclerView=(RecyclerView) v.findViewById(R.id.rv);
        linearLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList=new ArrayList<>();

        for (int i =1;i<=12;i++){
            arrayList.add(new dayData(i));
        }

        dayadapter=new Dayadapter(getContext(),arrayList);

        recyclerView.setAdapter(dayadapter);

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (check){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, new WordbookFragment()).addToBackStack("crop_type")
                    .commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        check=true;
    }
}