package com.example.toiquewordbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // 프래그먼트 객체 선언
    Fragment wordbookFragment= new WordbookFragment();
    Fragment homeFragment = new HomeFragment();
    Fragment mybookFragment = new MybookFragment();

    String themeColor;
    ImageView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, LoadingActivity.class);
//        startActivity(intent);

        DBOpenHelper helper = new DBOpenHelper(this);
        title = findViewById(R.id.title);

        //제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.

        title.setImageResource(R.drawable.title);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();

        // 바텀 네비게이션 객체 선언
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tab2);

        themeColor = ThemeUtil.modLoad(getApplicationContext());
        ThemeUtil.applyTheme(themeColor);

        // 바텀 네비게이션 클릭 리스너 설정
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case  R.id.tab1:
                        // replace(프레그먼트를 띄워줄 frameLayout, 교체할 fragment 객체)
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, wordbookFragment).commitAllowingStateLoss();
                        return  true;
                    case  R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
                        return  true;
                    case  R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mybookFragment).commitAllowingStateLoss();
                        return  true;
                    default:
                        return false;
                }
            }
        });

    }

}