package com.example.hobbyking.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.server.ConnectServer;

import java.util.concurrent.ExecutionException;

public class ChangePasswordActivity extends AppCompatActivity {
    TextView nameT, ageT, genderT;
    Button edit;
    EditText  new_pw ,new_pw_check;
    int uid;
    String op="",np="",npc="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String age = intent.getExtras().getString("age");
        String gender = intent.getExtras().getString("gender");
        Log.i("변경", name+"/"+age+"/"+gender);
        setContentView(R.layout.activity_change_password);
        SharedPreferences autoLogin = this.getSharedPreferences("auto", Context.MODE_PRIVATE);
        uid = autoLogin.getInt("UID", 0);
        nameT = (TextView)findViewById(R.id.pc_name);
        ageT= (TextView)findViewById(R.id.pc_age);
        genderT = (TextView)findViewById(R.id.pc_gender);

        new_pw = (EditText)findViewById(R.id.new_pw);
        new_pw_check = (EditText)findViewById(R.id.new_pw_check);
        edit = (Button)findViewById(R.id.edit_button);
        nameT.setText(name);
        if (gender.equals("0")) genderT.setText("남자");
        else genderT.setText("여자");
        ageT.setText(age.trim());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                np = new_pw.getText().toString();
                npc = new_pw_check.getText().toString();

                if (np.equals("")|| npc.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "모든 항목을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else if (!np.equals(npc))
                {
                    Toast.makeText(getApplicationContext(), "새 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                }else
                {
                    String sendMessage = "userid="+uid+"&np="+np;
                    Log.i("변경", sendMessage);
                    ConnectServer connectserver = new ConnectServer(sendMessage, "changePassword.jsp");
                    try {
                        String result = connectserver.execute().get();
                        if(result.equals("success    ")){
                            Toast.makeText(getApplicationContext(), "비밀번호가 정상적으로 변경 되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "비밀번호가 변경되지 못했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
