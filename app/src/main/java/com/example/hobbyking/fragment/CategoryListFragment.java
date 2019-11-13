package com.example.hobbyking.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.model.BackPressCloseHandler;
import com.example.hobbyking.server.ConnectServer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

// Fragment
public class CategoryListFragment extends Fragment {
    private ArrayList<ClassData> classData = new ArrayList<>();
    private RecyclerView categoryRecyclerView;
    private int categoryId=999;
    private BackPressCloseHandler backPressCloseHandler;
    ImageButton back;
    String[] name_set = {"뷰티", "프로그래밍", "여행", "영상제작", "운동","영어회화","요리","포토샵","음악","중국어","주식"};
    CategoryFragment categoryFragment;
    TextView category_name;
    Spinner sort_spinner;
    int current_sort=0;
    FragmentTransaction transaction;    FragmentManager fragmentManager;
    private CategoryRecyclerAdapter categoryAdapter;
//    private RecyclerView.LayoutManager wishLayoutManager;

    public CategoryListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
         sort_spinner = (Spinner)view.findViewById(R.id.category_sort_spinner);
        back = (ImageButton)view.findViewById(R.id.backbtn);
        classData.clear();
        categoryRecyclerView = view.findViewById(R.id.rv_category);
        categoryAdapter = new CategoryRecyclerAdapter(categoryId, getContext(), current_sort);
        category_name = (TextView)view.findViewById(R.id.category_name);
        categoryFragment = new CategoryFragment();
        RecyclerView.LayoutManager categoryLayoutManager = new LinearLayoutManager(getActivity());
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        category_name.setText(name_set[categoryId-1]);
        categoryRecyclerView.setAdapter(categoryAdapter);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort, android.R.layout.simple_spinner_item);
        sort_spinner.setAdapter(adapter);
        sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_sort = position;
                categoryAdapter = new CategoryRecyclerAdapter(categoryId, getContext(), current_sort);
                Log.i("sort", Integer.toString(current_sort));
                categoryRecyclerView.setAdapter(categoryAdapter);
                adapter.notifyDataSetChanged();
              //  connectServer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                current_sort = 0;
              //  connectServer();
            }
        });


        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainActivity_frame_layout, categoryFragment).commitAllowingStateLoss();
            }
        });
        return view;

    }
    public static Fragment newInstance(int param1) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putInt("position", param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt("position")+1;
        }
       // connectServer();
    }

    private void connectServer() {
        Log.i("카테고리", "요청 카테고리id"+categoryId);
        String sendMessage = "category="+categoryId+"&sort="+current_sort;
        ConnectServer connectserver = new ConnectServer(sendMessage, "getClassData_category.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("카테고리", result);
            String result_set[] = result.split("@#");

            for (int i = 0; i < result_set.length-1; i++)
            {
                //Log.i("위시리스트 ",  Integer.toString(result_set.length));
                //Log.i("위시리스트 ",  Integer.toString(i));
                String result_data[] = result_set[i].split("#@");
                Log.i("카테고리", result_data[0]+result_data[1]+result_data[2]+result_data[3]+result_data[4]+result_data[5]+result_data[6]+result_data[7]+result_data[8]+result_data[9]+result_data[10]+result_data[11]+result_data[12]+result_data[13]);
                //Log.i("클래스데이터", result_data[0]);
                //Log.i("클래스데이터", classData[0]);
                ClassData temp;
                temp = new ClassData(result_data[0],result_data[1],result_data[2],Integer.parseInt(result_data[3]), result_data[4],Double.parseDouble(result_data[5]),Integer.parseInt(result_data[6]), result_data[7], Integer.parseInt(result_data[8]), result_data[9], result_data[10], Integer.parseInt(result_data[11]),Integer.parseInt(result_data[12]), Integer.parseInt(result_data[13]));
                classData.add(temp);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void onBackPressed() { //super.onBackPressed();
         backPressCloseHandler.onBackPressed(); }

}
