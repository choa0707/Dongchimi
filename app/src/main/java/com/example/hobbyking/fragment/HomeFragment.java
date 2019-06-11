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
import com.example.hobbyking.model.CircleAnimIndicator;
import com.example.hobbyking.model.HomeViewpagerAdapter;

public class HomeFragment extends Fragment {
    View fragment;
    HomeViewpagerAdapter adapter;
    ViewPager viewPager1,viewPager2,viewPager3;
    private CircleAnimIndicator circleAnimIndicator,circleAnimIndicator2,circleAnimIndicator3;

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
        viewPager1.addOnPageChangeListener(mOnPageChangeListener1);
        viewPager2.addOnPageChangeListener(mOnPageChangeListener2);
        viewPager3.addOnPageChangeListener(mOnPageChangeListener3);

        circleAnimIndicator = (CircleAnimIndicator)fragment.findViewById(R.id.circleAnimIndicator);
        circleAnimIndicator2 = (CircleAnimIndicator)fragment.findViewById(R.id.circleAnimIndicator2);
        circleAnimIndicator3 = (CircleAnimIndicator)fragment.findViewById(R.id.circleAnimIndicator3);
        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        circleAnimIndicator2.setItemMargin(15);
        circleAnimIndicator3.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        circleAnimIndicator2.setAnimDuration(300);
        circleAnimIndicator3.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(3, R.drawable.baseline_favorite_border_black_18 , R.drawable.baseline_favorite_black_18);
        circleAnimIndicator2.createDotPanel(3, R.drawable.baseline_favorite_border_black_18 , R.drawable.baseline_favorite_black_18);
        circleAnimIndicator3.createDotPanel(3, R.drawable.baseline_favorite_border_black_18 , R.drawable.baseline_favorite_black_18);

        return fragment;
    }
    private ViewPager.OnPageChangeListener mOnPageChangeListener1 = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    private ViewPager.OnPageChangeListener mOnPageChangeListener2 = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator2.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    private ViewPager.OnPageChangeListener mOnPageChangeListener3 = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator3.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
