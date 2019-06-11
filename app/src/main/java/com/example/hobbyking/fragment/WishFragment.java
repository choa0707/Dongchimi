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
    /*private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("김치", "레몬", "엑셀");
        List<String> listContent = Arrays.asList("10000원", "5000원", "20000원");
        List<Integer> listResId = Arrays.asList(R.drawable.kimchi, R.drawable.lemon, R.drawable.excel);

        WishListItem data = new WishListItem();
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.

            data.setClass_title(listTitle.get(i));
            data.setClass_price(listContent.get(i));
            data.setClass_image_id(listResId.get(i));


            // 각 값이 들어간 data를 adapter에 추가합니다.
            wishAdapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        wishAdapter.notifyDataSetChanged();
    }*/
}
