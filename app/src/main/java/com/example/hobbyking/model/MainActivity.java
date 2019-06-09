package com.example.hobbyking.model;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hobbyking.R;
import com.example.hobbyking.fragment.CategoryFragment;
import com.example.hobbyking.fragment.HomeFragment;
import com.example.hobbyking.fragment.MypageFragment;
import com.example.hobbyking.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private HomeFragment homefragment = new HomeFragment();
    private MypageFragment mypageFragment = new MypageFragment();
    private CategoryFragment categoryfragment = new CategoryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.mainActivity_bottom_navigation_view);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainActivity_frame_layout, homefragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home: {
                        transaction.replace(R.id.mainActivity_frame_layout, homefragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_category: {
                        transaction.replace(R.id.mainActivity_frame_layout, categoryfragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_mypage: {
                        transaction.replace(R.id.mainActivity_frame_layout, mypageFragment).commitAllowingStateLoss();
                        break;
                    }


                }
                return true;
            }
        });

    }
}
