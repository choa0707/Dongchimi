package com.example.hobbyking.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hobbyking.R;
import com.example.hobbyking.server.ConnectServer;

import java.util.concurrent.ExecutionException;

public class MypageActivity extends AppCompatActivity {
    int uid;
    TextView name, age, gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        SharedPreferences autoLogin = this.getSharedPreferences("auto", Context.MODE_PRIVATE);
        uid = autoLogin.getInt("UID", 0);
        name = (TextView)findViewById(R.id.rmypage_name);
        age = (TextView)findViewById(R.id.rmypage_age);
        gender = (TextView)findViewById(R.id.rmypage_gender);
        getUserData();
    }
    private void getUserData() {
        String sendMessage = "userid="+uid;
        ConnectServer connectserver = new ConnectServer(sendMessage, "getUserData.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("클래스데이터", result);
            String result_set[] = result.split("/");
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
