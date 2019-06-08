package com.example.hobbyking.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hobbyking.R;

public class HomeFragment extends Fragment {
    View fragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fagment_home, container, false);

        ImageButton backbtn = (ImageButton)fragment.findViewById(R.id.category_cate_2_backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "클릭", Toast.LENGTH_SHORT).show();
            }
        });
        
        return fragment;
    }
}
