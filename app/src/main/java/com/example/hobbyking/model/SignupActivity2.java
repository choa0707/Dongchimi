package com.example.hobbyking.model;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.example.hobbyking.R;

public class SignupActivity2 extends AppCompatActivity {

    ToggleButton button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11;
    ImageButton signupback;
    Button signup_finish;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        button1 = (ToggleButton)findViewById(R.id.toggle_button1);
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
