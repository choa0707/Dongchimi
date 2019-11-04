package com.example.hobbyking.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.server.ConnectServer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

// Fragment
public class CategoryListFragment extends Fragment {
    private ArrayList<ClassData> classData = new ArrayList<>();
    private RecyclerView categoryRecyclerView;
    private int categoryId=999;

    private CategoryRecyclerAdapter categoryAdapter;
//    private RecyclerView.LayoutManager wishLayoutManager;

    public CategoryListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        classData.clear();
        categoryRecyclerView = view.findViewById(R.id.rv_category);
        categoryAdapter = new CategoryRecyclerAdapter(categoryId, getContext());

        RecyclerView.LayoutManager categoryLayoutManager = new LinearLayoutManager(getActivity());
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryRecyclerView.setAdapter(categoryAdapter);

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
        getData();
    }

    private void getData(){
        connectServer();
    }
    private void connectServer() {
        Log.i("카테고리", "요청 카테고리id"+categoryId);
        String sendMessage = "category="+categoryId;
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
}
