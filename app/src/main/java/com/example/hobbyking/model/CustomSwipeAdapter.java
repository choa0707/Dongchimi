package com.example.hobbyking.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hobbyking.R;

public class CustomSwipeAdapter extends PagerAdapter {

    private int[] images = {R.drawable.kimchi, R.drawable.kimchi, R.drawable.kimchi};
    private LayoutInflater inflater;
    private Context context;

    public CustomSwipeAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((LinearLayout)o);
    }

    public Object instantiateItem(ViewGroup container, int position)
    {
        inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        container.addView(v);
        return v;
    }

    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.invalidate();
    }
}
