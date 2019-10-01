package com.example.hobbyking.model;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hobbyking.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    String email, pwd;
    class CustomTask2 extends AsyncTask<String, Void, String> {
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
                sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&type="+strings[2];//보낼 정보 id=rain483&pwd=1234";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        EditText id = (EditText)findViewById(R.id.loginActivity_edittext_id);
        EditText password = (EditText)findViewById(R.id.loginActivity_edittext_password);
        Button login = (Button)findViewById(R.id.loginActivity_button_login);
        Button signup = (Button)findViewById(R.id.loginActivity_button_signup);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    email = id.getText().toString();
                    pwd = password.getText().toString();
                    String result = new CustomTask2().execute(email, pwd, "login").get().toString();

                    if(result.equals("false    ") || result.equals("noId    ")) {
                        Toast.makeText(LoginActivity.this,"정보가 틀립니다.",Toast.LENGTH_SHORT).show();
                        password.setText("");
                    } else if(result.equals("true    ")) {
                        Log.i("실행여부", "ok");

                        Toast.makeText(LoginActivity.this,"로그인 되었습니다.",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {

                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);

            }
        });

    }

}
