package com.example.hobbyking.model;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.hobbyking.R;
import com.example.hobbyking.server.ConnectServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SignupActivity2 extends AppCompatActivity {

    ToggleButton classToggleButton1, classToggleButton2,classToggleButton3,classToggleButton4,classToggleButton5,classToggleButton6,classToggleButton7,classToggleButton8,classToggleButton9,classToggleButton10,classToggleButton11;
    String classCategoryString="";
    ImageButton signupback;
    Button signup_finish;
    long mNow;
    Date mDate;
    ArrayList<Integer> classCategory = new ArrayList<>();
    //SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        Intent intent = getIntent();

        String email = intent.getExtras().getString("email");
        String pwd = intent.getExtras().getString("pwd");
        String pwdcheck = intent.getExtras().getString("pwdcheck");
        String name = intent.getExtras().getString("name");
       // String birth = intent.getExtras().getString("birth");
        String gender = intent.getExtras().getString("gender");


        classToggleButton1 = (ToggleButton) findViewById(R.id.toggle_button1);
        classToggleButton2 = (ToggleButton)findViewById(R.id.toggle_button2);
        classToggleButton3 = (ToggleButton)findViewById(R.id.toggle_button3);
        classToggleButton4 = (ToggleButton)findViewById(R.id.toggle_button4);
        classToggleButton5 = (ToggleButton)findViewById(R.id.toggle_button5);
        classToggleButton6 = (ToggleButton)findViewById(R.id.toggle_button6);
        classToggleButton7 = (ToggleButton)findViewById(R.id.toggle_button7);
        classToggleButton8 = (ToggleButton)findViewById(R.id.toggle_button8);
        classToggleButton9 = (ToggleButton)findViewById(R.id.toggle_button9);
        classToggleButton10 = (ToggleButton)findViewById(R.id.toggle_button10);
        classToggleButton11 = (ToggleButton)findViewById(R.id.toggle_button11);



        signup_finish = (Button)findViewById(R.id.signup_finish);
        signupback = (ImageButton)findViewById(R.id.backbtn2);
        signupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signup_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    classCategory.clear();
                    classCategoryString = "";
                    getClassCatoeryCheked();
                    Log.i("선택된카테고리", Integer.toString(classCategory.size()));
                    for (int i = 0; i < classCategory.size(); i++)
                    {
                        classCategoryString += Integer.toString(classCategory.get(i));
                        if (i != classCategory.size()-1) classCategoryString += ",";
                    }
                    Log.i("클래스카테고리 테스트", classCategoryString);

                    String sendMessage = "id="+email+"&pwd="+pwd+"&name="+name+"&gender="+gender+"&classCategory="+classCategoryString+"&type=join";
                    ConnectServer connectserver = new ConnectServer(sendMessage, "Login.jsp");
                    String result = connectserver.execute().get().toString();

                        if(result.equals("exist    ")) {
                            Toast.makeText(SignupActivity2.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            finish();
                    } else if(result.equals("ok    ")) {
                        Log.i("실행여부", "ok");
                        Toast.makeText(SignupActivity2.this,"회원가입을 축하합니다.",Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                SignupActivity signupActivity = (SignupActivity) SignupActivity.SignupActivity;
                signupActivity.finish();
                finish();

            }
        });

    }

    private void getClassCatoeryCheked() {
        if (classToggleButton1.isChecked()) classCategory.add(1);
        if (classToggleButton2.isChecked()) classCategory.add(2);
        if (classToggleButton3.isChecked()) classCategory.add(3);
        if (classToggleButton4.isChecked()) classCategory.add(4);
        if (classToggleButton5.isChecked()) classCategory.add(5);
        if (classToggleButton6.isChecked()) classCategory.add(6);
        if (classToggleButton7.isChecked()) classCategory.add(7);
         if (classToggleButton8.isChecked()) classCategory.add(8);
         if (classToggleButton9.isChecked()) classCategory.add(9);
         if (classToggleButton10.isChecked()) classCategory.add(10);
         if (classToggleButton11.isChecked()) classCategory.add(11);

    }

}
