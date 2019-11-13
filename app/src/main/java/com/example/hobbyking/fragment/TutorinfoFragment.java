package com.example.hobbyking.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;

public class TutorinfoFragment extends Fragment {
    ClassData classData;
    TextView tutorinfo;
    String a;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        classData = (ClassData)getArguments().getSerializable("classdata");
        View view = inflater.inflate(R.layout.fragment_tutorinfo, null);
        tutorinfo = (TextView)view.findViewById(R.id.detail_tutorinfo);
        classData.setTutor_info(classData.getTutor_info().replace("$", "\n"));
        a = classData.getTutor_info().replace("$", "\n");
        Log.i("마짐", classData.getTutor_info());
        Log.i("마짐", a);
        tutorinfo.setText(classData.getTutor_info());
        return view;
    }
}
