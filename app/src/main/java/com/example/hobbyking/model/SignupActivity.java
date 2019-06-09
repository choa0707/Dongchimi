package com.example.hobbyking.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.hobbyking.R;

import java.util.Date;

public class SignupActivity extends AppCompatActivity {

    DatePicker mDate;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    String s_birth;
    Button signupNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                Intent intent = new Intent(getApplicationContext(), SignupActivity2.class);
                startActivity(intent);
            }
        });
    }
}
