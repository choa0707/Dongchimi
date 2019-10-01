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

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        // doInBackground의 매개값이 문자열 배열. 보낼 값이 여러개일 경우를 위해 배열로.
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.56.1:8080/HobbyKing/Login.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&name="+strings[2]+"&gender="+strings[3]+"&classCategory="+strings[4]+"&type="+strings[5];//보낼 정보 id=rain483&pwd=1234";

                osw.write(sendMsg);//OutputStreamWriter에 담아 전송
                osw.flush();
                //jsp와 통신이 정상적으로 되었을 때 할 코드
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    //jsp에서 보낸 값을 받음
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("통신 결과", receiveMsg);
                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                    // 통신이 실패했을 때 실패한 이유를 알기 위해 로그를 찍습니다.
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //jsp로부터 받은 리턴 값입니다.
            return receiveMsg;
        }
    }
    /*private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }*/

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
                    String result = new CustomTask().execute(email, pwd, name, gender, classCategoryString, "join").get().toString();

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
