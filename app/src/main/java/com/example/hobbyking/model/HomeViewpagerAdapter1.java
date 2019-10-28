package com.example.hobbyking.model;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hobbyking.R;
import com.example.hobbyking.server.ImageDownActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeViewpagerAdapter1 extends PagerAdapter {
    private int[] images = {R.drawable.i1, R.drawable.i1, R.drawable.i1};
    private LayoutInflater inflater;
    private Context context;
    Handler handler;
    Bitmap bitmap1, bitmap2, bitmap3;
    public HomeViewpagerAdapter1(Context context){
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
        //여기서 이미지 받아옴
        Thread th =new Thread((Runnable) HomeViewpagerAdapter1.this);
        // 동작 수행
        th.start();
        View v = inflater.inflate(R.layout.slider, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.home_imageview);
        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 서버에서 받아온 이미지를 핸들러를 경유해 이미지뷰에 비트맵 리소스 연결
                if (position == 0) imageView.setImageBitmap(bitmap1);
                else if (position == 1) imageView.setImageBitmap(bitmap2);
                else if (position == 2) imageView.setImageBitmap(bitmap3);
            }
        };
       // imageView.setImageResource(images[position]);

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

    public void run() {
        URL url =null;
        try{
            url =new URL("http://192.168.0.127/resources/images/like1.png");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap1 = BitmapFactory.decodeStream(is);
            // 핸들러에게 화면 갱신을 요청.
            handler.sendEmptyMessage(0);
            // 연결 종료
            is.close();
            conn.disconnect();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
