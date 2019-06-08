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
import android.widget.ListView;

import com.example.hobbyking.R;
import com.example.hobbyking.model.MainActivity;


public class CategoryFragment extends Fragment {

    CategoryMainFragment categoryMainFragment = new CategoryMainFragment();
    View fragment;
    FragmentTransaction transaction;
    FragmentManager fragmentManager;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment = inflater.inflate(R.layout.fragment_category, container, false);

        String[] cate ={"OnLine","OffLine"};
        String[] cateOff = {"요리","디저트","스포츠", "언어", "뷰티", "컴퓨터" };



        ArrayAdapter<String> m1Adapter = new ArrayAdapter<String>(fragment.getContext(),android.R.layout.simple_list_item_1,cate);
        ListView cate1 = (ListView)fragment.findViewById(R.id.category_actmain_cate1);
        cate1.setAdapter(m1Adapter);

        ArrayAdapter<String> m2Adapter = new ArrayAdapter<String>(fragment.getContext(),android.R.layout.simple_list_item_1,cateOff);
        final ListView cate2 = (ListView)fragment.findViewById(R.id.cate2);
        cate2.setAdapter(m2Adapter);



        cate1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position ==1 )
                    cate2.setVisibility(View.VISIBLE);

            }
        });

        cate2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            String[] cateCook = {"한식", "양식", "중식", "일식" };
            String[] cateDesert = {"마카롱", "케이크","초콜릿","젤리", "사탕", "다쿠아즈", "빵" };
            String[] catesports = {"축구", "야구","배드민턴","농구", "플라잉디스크", "티볼", "족구","스케이트","수영"};
            String[] catelanguage = {"한국어", "영어","스페인어","불어", "중국어", "일본어"};
            String[] catebeauty = {"메이크업", "피부관리","패션",};
            String[] catestudy = {"엑셀", "파워포인트","java","안드로이드 스튜디오", "정보처리기사"};




            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){

                    ArrayAdapter<String> m3Adapter = new ArrayAdapter<String>(fragment.getContext(),android.R.layout.simple_list_item_1,cateCook);
                    final ListView cate3 = (ListView)fragment.findViewById(R.id.category_actmain_cate3);
                    cate3.setAdapter(m3Adapter);cate3.setVisibility(View.VISIBLE);

                    cate3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                fragmentManager = getFragmentManager();
                                assert fragmentManager != null;
                                transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.mainActivity_frame_layout, categoryMainFragment).commitAllowingStateLoss();

                            }
                        }
                    });



                }else if (position == 1){

                    ArrayAdapter<String> m3Adapter = new ArrayAdapter<String>(fragment.getContext(),android.R.layout.simple_list_item_1,cateDesert);
                    final ListView cate3 = (ListView)fragment.findViewById(R.id.category_actmain_cate3);
                    cate3.setAdapter(m3Adapter);cate3.setVisibility(View.VISIBLE);


                } else if (position == 2) {

                    ArrayAdapter<String> m3Adapter = new ArrayAdapter<String>(fragment.getContext(),android.R.layout.simple_list_item_1,catesports);
                    final ListView cate3 = (ListView)fragment.findViewById(R.id.category_actmain_cate3);
                    cate3.setAdapter(m3Adapter);cate3.setVisibility(View.VISIBLE);

                } else if (position == 3) {

                    ArrayAdapter<String> m3Adapter = new ArrayAdapter<String>(fragment.getContext(),android.R.layout.simple_list_item_1,catelanguage);
                    final ListView cate3 = (ListView)fragment.findViewById(R.id.category_actmain_cate3);
                    cate3.setAdapter(m3Adapter);cate3.setVisibility(View.VISIBLE);

                }  else if (position == 4) {

                    ArrayAdapter<String> m3Adapter = new ArrayAdapter<String>(fragment.getContext(),android.R.layout.simple_list_item_1,catebeauty);
                    final ListView cate3 = (ListView)fragment.findViewById(R.id.category_actmain_cate3);
                    cate3.setAdapter(m3Adapter);cate3.setVisibility(View.VISIBLE);

                } else if (position == 5) {

                    ArrayAdapter<String> m3Adapter = new ArrayAdapter<String>(fragment.getContext(),android.R.layout.simple_list_item_1,catestudy);
                    final ListView cate3 = (ListView)fragment.findViewById(R.id.category_actmain_cate3);
                    cate3.setAdapter(m3Adapter);cate3.setVisibility(View.VISIBLE);

                }
            }
        });

        return fragment;
    }
}
