package com.example.hobbyking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hobbyking.R;
import com.example.hobbyking.model.ClassaddActivity;
import com.example.hobbyking.model.HomeViewpagerAdapter;
import com.example.hobbyking.model.MypageMyinfoActivity;

public class MypageFragment extends Fragment {

    View fragment;
    Button classadd, myinfo, classlist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_mypage, container, false);
        classadd = (Button)fragment.findViewById(R.id.mypage_classadd);
        myinfo = (Button)fragment.findViewById(R.id.mypage_myinfo);
        classlist = (Button)fragment.findViewById(R.id.mypage_classlist);
        classadd.setOnClickListener(new View.OnClickListener() {
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
        classlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClassaddActivity.class);
                startActivity(intent);
            }
        });

        return fragment;
    }
}
