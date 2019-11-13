package com.example.hobbyking.model;

import android.content.Context;
import android.content.Intent;
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

public class MypageClasslistActivity extends AppCompatActivity {
    private RecyclerView myClassRecyclerView;
    private ArrayList<ClassData> classData = new ArrayList<>();
    private int uid;
    ImageButton back;
    private MyClassAdapter myclassAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_classlist);
        back = (ImageButton)findViewById(R.id.list_backbtn);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences autoLogin = getSharedPreferences("auto", Context.MODE_PRIVATE);
        uid = autoLogin.getInt("UID", 0);
        classData.clear();

        myClassRecyclerView = findViewById(R.id.rv_myclass);
        myclassAdapter = new MyClassAdapter(classData, getApplicationContext());

        RecyclerView.LayoutManager wishLayoutManager = new LinearLayoutManager(getApplicationContext());
        myClassRecyclerView.setLayoutManager(wishLayoutManager);
        myClassRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myClassRecyclerView.setAdapter(myclassAdapter);
    }

}
