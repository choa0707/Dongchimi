package com.example.hobbyking.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.server.ConnectServer;

import java.util.concurrent.ExecutionException;

public class MypageMyinfoActivity extends AppCompatActivity {
    int uid;
    Button pc;
    TextView name, age, gender;
    ImageButton back;
    String result_set[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_myinfo);
        SharedPreferences autoLogin = this.getSharedPreferences("auto", Context.MODE_PRIVATE);
        pc = (Button)findViewById(R.id.password_change);
        uid = autoLogin.getInt("UID", 0);
        name = (TextView)findViewById(R.id.mypage_name);
        age = (TextView)findViewById(R.id.mypage_age);
        gender = (TextView)findViewById(R.id.mypage_gender);
        back = (ImageButton)findViewById(R.id.myinfo_back);
        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                intent.putExtra("name", result_set[0]);
                intent.putExtra("age", result_set[2]);
                intent.putExtra("gender", result_set[1]);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getUserData();
    }

    private void getUserData() {
        String sendMessage = "userid="+uid;
        ConnectServer connectserver = new ConnectServer(sendMessage, "getUserData.jsp");
        try {
           String result = connectserver.execute().get();
            Log.i("클래스데이터", result);
             result_set= result.split("/");
            name.setText(result_set[0]);
            if (result_set[1].equals("0")) gender.setText("남자");
            else gender.setText("여자");
            age.setText(result_set[2]);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
