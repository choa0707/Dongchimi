package com.example.hobbyking.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.model.MainActivity;


public class CategoryFragment extends Fragment {

    CategoryMainFragment categoryMainFragment = new CategoryMainFragment();
    CategoryListFragment categoryListFragment = new CategoryListFragment();
    View fragment;

    FragmentTransaction transaction;
    FragmentManager fragmentManager;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment = inflater.inflate(R.layout.fragment_category, container, false);


        String[] cateOff = {"뷰티","프로그래밍","여행", "영상제작", "운동", "영어회화" , "요리",  "포토샵" , "음악" , "중국어","주식" };




        ArrayAdapter<String> m2Adapter = new ArrayAdapter<String>(fragment.getContext(),R.layout.gridview_item,cateOff);
        final GridView cate2 = (GridView)fragment.findViewById(R.id.cate2);

        cate2.setAdapter(m2Adapter);
        cate2.setVisibility(View.VISIBLE);

        cate2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainActivity_frame_layout, categoryListFragment.newInstance(position)).commitAllowingStateLoss();
            }
        });

        return fragment;
    }

}
