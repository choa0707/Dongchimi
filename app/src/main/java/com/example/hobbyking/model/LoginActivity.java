package com.example.hobbyking.model;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.server.ConnectServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    String email, pwd;
    String sendMessage;
    int userId ;
    int istutor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        SharedPreferences autoLogin = getSharedPreferences("auto", Activity.MODE_PRIVATE);


        userId = autoLogin.getInt("UID", 0);
        istutor = autoLogin.getInt("TUTOR", -1);

        if(userId != 0)
        {
            Toast.makeText(LoginActivity.this,"자동 로그인 되었습니다.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

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
                    sendMessage = "id="+email+"&pwd="+pwd+"&type=login";
                    ConnectServer connectserver = new ConnectServer(sendMessage, "Login.jsp");
                    String result = connectserver.execute().get().toString();
                    Log.i("서버결과", result);
                    result = result.replace(" ", "");
                    String[] result_set = result.split(",");

                    if(result_set[0].equals("false") || result_set[0].equals("noId")) {
                        Toast.makeText(LoginActivity.this,"정보가 틀립니다.",Toast.LENGTH_SHORT).show();
                        password.setText("");
                    } else if(result_set[0].equals("true")) {
                        Log.i("로그인", "로그인성공");

                        Toast.makeText(LoginActivity.this,"로그인 되었습니다.",Toast.LENGTH_SHORT).show();
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putInt("UID", Integer.parseInt(result_set[1]));
                        if (Integer.parseInt(result_set[2]) == 0)
                        {
                            autoLogin.putInt("TUTOR", 0);
                        } else if (Integer.parseInt(result_set[2]) == 1)
                        {
                            autoLogin.putInt("TUTOR", 1);
                        }
                        autoLogin.commit();

                        Log.i("자동로그인", result_set[1]);
                        Log.i("튜터여부", result_set[2]);
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
