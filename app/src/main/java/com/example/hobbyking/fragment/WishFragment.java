package com.example.hobbyking.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.server.ConnectServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

// Fragment
public class WishFragment extends Fragment {
    private ArrayList<ClassData> classData = new ArrayList<>();
    private RecyclerView wishRecyclerView;
    private int uid;
    private WishRecyclerAdapter wishAdapter;
//    private RecyclerView.LayoutManager wishLayoutManager;

    public WishFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wish, container, false);
        classData.clear();
        wishRecyclerView = view.findViewById(R.id.rv_wish);
        wishAdapter = new WishRecyclerAdapter(classData, getContext());

        RecyclerView.LayoutManager wishLayoutManager = new LinearLayoutManager(getActivity());
        wishRecyclerView.setLayoutManager(wishLayoutManager);
        wishRecyclerView.setItemAnimator(new DefaultItemAnimator());
        wishRecyclerView.setAdapter(wishAdapter);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences autoLogin = getContext().getSharedPreferences("auto", Context.MODE_PRIVATE);
        uid = autoLogin.getInt("UID", 0);
        getData();
    }

    private void getData(){
        connectServer();
    }
    private void connectServer() {
        String sendMessage = "userid="+uid;
        ConnectServer connectserver = new ConnectServer(sendMessage, "getClassData_wish.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("클래스데이터", result);
            String result_set[] = result.split("@#");

            for (int i = 0; i < result_set.length-1; i++)
            {
                //Log.i("위시리스트 ",  Integer.toString(result_set.length));
                //Log.i("위시리스트 ",  Integer.toString(i));
                String result_data[] = result_set[i].split("#@");
                Log.i("위시리스트", result_data[0]+result_data[1]+result_data[2]+result_data[3]+result_data[4]+result_data[5]+result_data[6]+result_data[7]+result_data[8]+result_data[9]+result_data[10]+result_data[11]+result_data[12]+result_data[13]);
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
