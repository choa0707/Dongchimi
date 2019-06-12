package com.example.hobbyking.fragment;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Fragment
public class WishFragment extends Fragment {
    private ArrayList<WishListItem> wishList = new ArrayList<>();
    private RecyclerView wishRecyclerView;
    private WishRecyclerAdapter wishAdapter;
//    private RecyclerView.LayoutManager wishLayoutManager;

    public WishFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wish, container, false);

        wishRecyclerView = view.findViewById(R.id.rv_wish);
        wishAdapter = new WishRecyclerAdapter(wishList);

        RecyclerView.LayoutManager wishLayoutManager = new LinearLayoutManager(getActivity());
        wishRecyclerView.setLayoutManager(wishLayoutManager);
        wishRecyclerView.setItemAnimator(new DefaultItemAnimator());
        wishRecyclerView.setAdapter(wishAdapter);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    private void getData(){
        wishList.add(new WishListItem("김치", "10000원",R.drawable.kimchi));
        wishList.add(new WishListItem("레몬", "5000원",R.drawable.lemon));
        wishList.add(new WishListItem("엑셀", "20000원",R.drawable.excel));
    }
}
