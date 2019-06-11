package com.example.hobbyking.fragment;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hobbyking.R;

import java.util.ArrayList;

public class WishRecyclerAdapter extends RecyclerView.Adapter<WishRecyclerAdapter.ItemViewHolder> {

    private ArrayList<WishListItem> listData = new ArrayList<>();

    public WishRecyclerAdapter(ArrayList<WishListItem> myData){
        this.listData = myData;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_item, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {

        return listData.size();
    }

    void addItem(WishListItem data) {

        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.wish_tv1);
            textView2 = itemView.findViewById(R.id.wish_tv2);
            imageView = itemView.findViewById(R.id.wish_image);
        }

        void onBind(WishListItem data) {
            textView1.setText(data.getClass_title());
            textView2.setText(data.getClass_price());
            imageView.setImageResource(data.getClass_image_id());
        }


    }
}



