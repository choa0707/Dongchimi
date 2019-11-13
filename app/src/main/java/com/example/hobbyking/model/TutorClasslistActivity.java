package com.example.hobbyking.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.fragment.WishRecyclerAdapter;
import com.example.hobbyking.server.ConnectServer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TutorClasslistActivity extends AppCompatActivity {
    private RecyclerView tutorClassRecyclerView;
    private ArrayList<ClassData> classData = new ArrayList<>();
    private int uid;
    ImageButton back;
    private TutorClassAdapter tutorClassAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_classlist);
        back = (ImageButton)findViewById(R.id.tutor_class_back);
        SharedPreferences autoLogin = getSharedPreferences("auto", Context.MODE_PRIVATE);
        uid = autoLogin.getInt("UID", 0);
        classData.clear();
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tutorClassRecyclerView = findViewById(R.id.rv_tutorclass);
        tutorClassAdapter = new TutorClassAdapter(classData, getApplicationContext());

        RecyclerView.LayoutManager tutorLayoutManager = new LinearLayoutManager(getApplicationContext());
        tutorClassRecyclerView.setLayoutManager(tutorLayoutManager);
        tutorClassRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tutorClassRecyclerView.setAdapter(tutorClassAdapter);
    }

}
