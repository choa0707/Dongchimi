package com.example.hobbyking.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.hobbyking.R;

public class CategoryMainFragment extends Fragment {

    View fragment;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_category_main, container, false);

        ImageButton backbtn = (ImageButton)fragment.findViewById(R.id.category_cate_2_backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return fragment;
    }
}
