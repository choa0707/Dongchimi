package com.example.hobbyking.model;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.fragment.WishRecyclerAdapter;
import com.example.hobbyking.model.ClassdetailActivity;
import com.example.hobbyking.server.ConnectServer;
import com.example.hobbyking.server.VolleyHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyClassAdapter extends RecyclerView.Adapter<MyClassAdapter.ItemViewHolder> {

    private ArrayList<ClassData> listData = new ArrayList<>();
    private int uid;
    ClassData currentData;
    Double rating;
    private RatingDialog ratingDialog;
    Context contextt;
    private ImageLoader mImageLoader;
    private String LOGIN_REQUEST_URL = "http://115.23.171.192:2180/HobbyKing/IMG_20191014_09533111.jpg";
    public MyClassAdapter(ArrayList<ClassData> myData, Context context){
        this.listData = myData;
        SharedPreferences autoLogin = context.getSharedPreferences("auto", Context.MODE_PRIVATE);
        uid = autoLogin.getInt("UID", 0);
        contextt = context;
        mImageLoader = VolleyHelper.getInstance(context).getImageLoader();
        connectServer();
    }
    private void connectServer() {
        String sendMessage = "userid="+uid;
        ConnectServer connectserver = new ConnectServer(sendMessage, "getClassData_myclass.jsp");
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
                temp.setState(Integer.parseInt(result_data[14]));
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myclass_item, parent, false);

        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if (listData.get(position).getState() == 2) {
                currentData = listData.get(position);
                ratingDialog = new RatingDialog(holder.ratingButton.getContext(), currentData, uid);
                ratingDialog.show();
           /*     }
                else
                {
                    Toast.makeText(holder.ratingButton.getContext(), "수강 승인 대기중인 강의는 평가할 수 없습니다.", Toast.LENGTH_LONG).show();
                }*/
            }
        });
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
        private Button ratingButton;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.myclass_tv1);
            textView2 = itemView.findViewById(R.id.myclass_state);
            imageView = itemView.findViewById(R.id.myclass_image);
            ratingButton = itemView.findViewById(R.id.rating_button);
            imageView.setImageResource(R.drawable.noimage);
        }

        void onBind(ClassData data) {
            textView1.setText(data.getName());
            if (data.getState() == 0)
            {
                textView2.setText("수강 승인 대기중");
            }else if (data.getState() == 1)
            {
                textView2.setText("수강중");
            }else if (data.getState() == 2)
            {
                textView2.setText("수강 완료");
            }

            imageView.setImageResource(R.drawable.noimage);
            LOGIN_REQUEST_URL = "http://115.23.171.192:2180/HobbyKing/"+data.getImage_url();
            Log.i("이미지주소", LOGIN_REQUEST_URL);
            imageView.setImageUrl(LOGIN_REQUEST_URL, mImageLoader);
        }
    }
}



