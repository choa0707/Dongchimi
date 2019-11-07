package com.example.hobbyking.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.model.ClassdetailActivity;
import com.example.hobbyking.server.ConnectServer;
import com.example.hobbyking.server.VolleyHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ItemViewHolder> {

    private ArrayList<ClassData> listData = new ArrayList<>();
    private int uid;
    private ImageLoader mImageLoader;
    int category;
    private String LOGIN_REQUEST_URL = "http://115.23.171.192:2180/HobbyKing/IMG_20191014_09533111.jpg";
    public CategoryRecyclerAdapter(int category, Context context){
        this.category = category;
        SharedPreferences autoLogin = context.getSharedPreferences("auto", Context.MODE_PRIVATE);
        uid = autoLogin.getInt("UID", 0);
        mImageLoader = VolleyHelper.getInstance(context).getImageLoader();
       connectServer();
    }
    private void connectServer() {
        Log.i("카테고리", "요청 카테고리id"+category);
        String sendMessage = "category="+category;
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
                listData.add(temp);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);

        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ClassdetailActivity.class);
                intent.putExtra("ClassData", listData.get(position));
                v.getContext().startActivity(intent);
            }
        });
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {

        return listData.size();
    }

    void addItem(ClassData data) {

        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private NetworkImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.cate_tv1);
            textView2 = itemView.findViewById(R.id.cate_tv2);
            imageView = itemView.findViewById(R.id.cate_image);
            imageView.setImageResource(R.drawable.noimage);
        }

        void onBind(ClassData data) {
            textView1.setText(data.getName());
            textView2.setText(data.getPrice()+"원/회당");
            imageView.setImageResource(R.drawable.noimage);
            LOGIN_REQUEST_URL = "http://115.23.171.192:2180/HobbyKing/"+data.getImage_url();
            Log.i("이미지주소", LOGIN_REQUEST_URL);
            imageView.setImageUrl(LOGIN_REQUEST_URL, mImageLoader);
        }
    }
}



