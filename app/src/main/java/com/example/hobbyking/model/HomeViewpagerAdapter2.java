package com.example.hobbyking.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.fragment.HomeFragment;
import com.example.hobbyking.server.ConnectServer;
import com.example.hobbyking.server.ImageDownActivity;
import com.example.hobbyking.server.VolleyHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class HomeViewpagerAdapter2 extends PagerAdapter {
    TextView priceText;
    private NetworkImageView mNetworkImageView;
    private ImageLoader mImageLoader;
    ClassData classData[] = new ClassData[4];
    private String LOGIN_REQUEST_URL = "http://192.168.56.1:8080/HobbyKing/IMG_20191014_09533111.jpg";
    int uid;
    private LayoutInflater inflater;
    private Context context;

    public HomeViewpagerAdapter2(Context context){
        this.context = context;
        connectServer();
        mImageLoader = VolleyHelper.getInstance(context).getImageLoader();
    }



    private void connectServer() {
        String sendMessage = "";
        ConnectServer connectserver = new ConnectServer(sendMessage, "getClassData_rank.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("클래스데이터", result);
            String result_set[] = result.split("@#");

            for (int i = 0; i < 3; i++)
            {
                String result_data[] = result_set[i].split("#@");
                Log.i("클래스데이터", result_data[0]);
                //Log.i("클래스데이터", result_data[0]);
                //Log.i("클래스데이터", classData[0]);
                classData[i]  = new ClassData(result_data[0],result_data[1],result_data[2],Integer.parseInt(result_data[3]), result_data[4],Double.parseDouble(result_data[5]),Integer.parseInt(result_data[6]), result_data[7], Integer.parseInt(result_data[8]), result_data[9], result_data[10], Integer.parseInt(result_data[11]),Integer.parseInt(result_data[12]), Integer.parseInt(result_data[13]));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return 3;
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
//        Thread th =new Thread();
//        // 동작 수행
//        th.start();
        View v = inflater.inflate(R.layout.slider, container, false);
        //ImageView imageView = (ImageView)v.findViewById(R.id.home_imageview);
        priceText =(TextView)v.findViewById(R.id.homefragment_price);
        mNetworkImageView = (NetworkImageView)v.findViewById(R.id.networkImageView);

        LOGIN_REQUEST_URL = "http://192.168.56.1:8080/HobbyKing/"+classData[position].getImage_url();
        Log.i("이미지주소", LOGIN_REQUEST_URL);
        mNetworkImageView.setImageUrl(LOGIN_REQUEST_URL, mImageLoader);
        priceText.setText(Integer.toString(classData[position].getPrice())+"원/회당");


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pos에따라 보여주기
                Intent intent = new Intent(v.getContext(), ClassdetailActivity.class);
                intent.putExtra("ClassData", classData[position]);
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
