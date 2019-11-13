package com.example.hobbyking.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.model.ClassaddActivity;
import com.example.hobbyking.model.MypageClasslistActivity;
import com.example.hobbyking.model.MypageMyinfoActivity;
import com.example.hobbyking.model.MypageTutorRegisterActivity;
import com.example.hobbyking.model.SplashActivity;
import com.example.hobbyking.model.TutorClasslistActivity;

public class MypageFragmentTutor extends Fragment {
    View fragment;
    Button classadd, myinfo, classlist, logout, tutor, delete;
    Button tclasslist;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        fragment = inflater.inflate(R.layout.fragment_mypage_tutor, container, false);

        classadd = (Button)fragment.findViewById(R.id.mypage_classadd) ;
        myinfo = (Button)fragment.findViewById(R.id.mypage_myinfo);
        logout =(Button)fragment.findViewById(R.id.mypage_logout);
        classlist = (Button)fragment.findViewById(R.id.mypage_classlist);
        tclasslist=(Button)fragment.findViewById(R.id.mypage_classlist_tutor);
        delete = (Button)fragment.findViewById(R.id.delete_user_tutor);
        classadd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClassaddActivity.class);
                startActivity(intent);
            }
        });

        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMyinfoActivity.class);
                startActivity(intent);
            }
        });
        tclasslist.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TutorClasslistActivity.class);
                startActivity(intent);
            }
        });

        classlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageClasslistActivity.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SplashActivity.class);
                startActivity(intent);

                SharedPreferences auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        return fragment;
    }
}
