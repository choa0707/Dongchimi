package com.example.hobbyking.model;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hobbyking.R;

import java.util.Date;

public class SignupActivity extends AppCompatActivity {

    DatePicker mDate;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    String s_birth;
    Button signupNext;
    String gender;
    ImageButton signupback;
    public static Activity SignupActivity;
    EditText email, pwd, pwdcheck, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignupActivity = SignupActivity.this;

        email=(EditText)findViewById(R.id.signupActivity_edittext_email);
        pwd = (EditText)findViewById(R.id.signupActivity_edittext_password);
        pwdcheck=(EditText)findViewById(R.id.signupActivity_edittext_password_check);
        name = (EditText)findViewById(R.id.signupActivity_edittext_name);

        signupback = (ImageButton) findViewById(R.id.backbtn1);
        signupNext = (Button)findViewById(R.id.signup_next);
        mDate = (DatePicker)findViewById(R.id.datepicker);
        final Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                s_birth = String.format("%d/%d/%d", year,monthOfYear + 1, dayOfMonth);
            }
        });
        signupNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!pwdcheck.getText().toString().equals(pwd.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    pwdcheck.setText("");
                    pwd.setText("");
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), SignupActivity2.class);
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("pwd", pwd.getText().toString());
                    intent.putExtra("pwdcheck", pwdcheck.getText().toString());
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("birth", s_birth);
                    if (spinner.getSelectedItem().toString().equals("여자")) {
                        gender = "women";
                    } else gender = "man";
                    intent.putExtra("gender", gender);
                    startActivity(intent);
                }
            }
        });

        signupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
