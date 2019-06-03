package com.example.hobbyking.model;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hobbyking.R;
import com.example.hobbyking.fragment.ClassinfoFragment;
import com.example.hobbyking.fragment.ClassreviewFragment;
import com.example.hobbyking.fragment.ClasstimeFragment;
import com.example.hobbyking.fragment.TutorinfoFragment;

import java.util.List;

public class ClassdetailActivity extends AppCompatActivity {

    CustomSwipeAdapter adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classdetail);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        Button button_classinfo = (Button)findViewById(R.id.classInfo);
        Button button_tutorinfo = (Button)findViewById(R.id.tutorInfo);
        Button button_classtime = (Button)findViewById(R.id.classTime);
        Button button_classreview = (Button)findViewById(R.id.classReview);

        button_classinfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.classFrame, new ClassinfoFragment()).commit();
            }
        });

        button_classreview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.classFrame, new ClassreviewFragment()).commit();
            }
        });

        button_classtime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.classFrame, new ClasstimeFragment()).commit();
            }
        });

        button_tutorinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.classFrame, new TutorinfoFragment()).commit();
            }
        });
    }
}
