package com.example.hobbyking.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.model.HomeViewpagerAdapter;

public class HomeFragment extends Fragment {
    View fragment;
    HomeViewpagerAdapter adapter;
    ViewPager viewPager1,viewPager2,viewPager3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager1 = (ViewPager)fragment.findViewById(R.id.view1);
        viewPager2 = (ViewPager)fragment.findViewById(R.id.view2);
        viewPager3 = (ViewPager)fragment.findViewById(R.id.view3);
        adapter = new HomeViewpagerAdapter(fragment.getContext());


        viewPager1.setAdapter(adapter);
        viewPager2.setAdapter(adapter);
        viewPager3.setAdapter(adapter);
        
        return fragment;
    }
}
