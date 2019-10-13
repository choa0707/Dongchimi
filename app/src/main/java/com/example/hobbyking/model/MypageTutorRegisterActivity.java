package com.example.hobbyking.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.server.ConnectServer;

import java.util.concurrent.ExecutionException;

public class MypageTutorRegisterActivity extends AppCompatActivity {

    String result = "";
    int userId;
    Button register;

    EditText phone, account;
    private String full_account, phonenumber, bank, s_account;
    private  String sendMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_tutor_register);

        phone = (EditText)findViewById(R.id.mypage_tutor_phone) ;
        account = (EditText)findViewById(R.id.mypage_tutor_account);
        register = (Button)findViewById(R.id.mypage_tutor_register_button);

        Spinner spinner = (Spinner) findViewById(R.id.mypage_tutor_bank);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.bank, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bank = spinner.getSelectedItem().toString();
                phonenumber = phone.getText().toString();
                s_account = account.getText().toString();
                full_account = bank+","+s_account;
                if (bank.equals("")) {
                    Toast.makeText(getApplicationContext(), "은행을 선택해주세요.", Toast.LENGTH_LONG);
                }
                else if (phonenumber.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 입력해주세요.", Toast.LENGTH_LONG);
                }
                else if (s_account.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "계좌번호를 입력해주세요.", Toast.LENGTH_LONG);
                }
                else
                {
                    SharedPreferences autoLogin = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                    userId = autoLogin.getInt("UID", 0);
                    sendMessage = "id="+userId+"&phone="+phonenumber+"&account="+full_account;
                    ConnectServer connectserver = new ConnectServer(sendMessage, "tutor_register.jsp");
                    try {
                         result = connectserver.execute().get();
                        Log.i("튜터등록", result);
                        if (result.equals("ok    "))
                        {
                            Toast.makeText(getApplicationContext(), "튜터로 등록되었습니다.", Toast.LENGTH_LONG);
                            SharedPreferences.Editor autoEditor = autoLogin.edit();
                            autoEditor.putInt("TUTOR", 1);
                            autoEditor.commit();
                            finish();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
