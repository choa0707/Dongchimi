package com.example.hobbyking.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hobbyking.R;

public class HomeViewpagerAdapter3 extends PagerAdapter {
    private int[] images = {R.drawable.i1, R.drawable.i1, R.drawable.i1};
    private LayoutInflater inflater;
    private Context context;

    public HomeViewpagerAdapter3(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return images.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.home_imageview);
        imageView.setImageResource(images[position]);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pos에따라 보여주기
                Intent intent = new Intent(v.getContext(), ClassdetailActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
