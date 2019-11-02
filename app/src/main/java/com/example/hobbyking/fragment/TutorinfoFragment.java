package com.example.hobbyking.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;

public class TutorinfoFragment extends Fragment {
    ClassData classData;
    TextView tutorinfo;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        classData = (ClassData)getArguments().getSerializable("classdata");
        View view = inflater.inflate(R.layout.fragment_tutorinfo, null);
        tutorinfo = (TextView)view.findViewById(R.id.detail_tutorinfo);
        tutorinfo.setText(classData.getDetail());
        return view;
    }
}
