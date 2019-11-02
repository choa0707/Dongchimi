package com.example.hobbyking.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;

public class ClassinfoFragment extends Fragment {
    ClassData classData;
    TextView classinfo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        classData = (ClassData)getArguments().getSerializable("classdata");
        Log.i("마지막테스트", classData.getName());
        View view = inflater.inflate(R.layout.fragment_classinfo, null);
        classinfo = (TextView)view.findViewById(R.id.detail_class);
        classinfo.setText(classData.getDetail());
        return view;
    }
}
