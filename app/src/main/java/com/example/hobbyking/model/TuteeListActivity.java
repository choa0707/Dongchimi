package com.example.hobbyking.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.server.ConnectServer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TuteeListActivity extends AppCompatActivity {
    private ListView m_oListView = null;
    int classid;
    ArrayList<TuteeData> oData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutee_list);
        Intent intent = getIntent();
        classid = intent.getExtras().getInt("classid");


// 데이터 생성--------------------------------


        connectServer();

// ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView)findViewById(R.id.listView);
        TuteeListAdapter oAdapter = new TuteeListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

    }
    private void connectServer() {
        String sendMessage = "classid="+classid;
        ConnectServer connectserver = new ConnectServer(sendMessage, "getTuteeData.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("튜티데이터", result);
            String result_set[] = result.split("@#");

            for (int i = 0; i < result_set.length-1; i++)
            {
                String result_data[] = result_set[i].split("#@");
                TuteeData tmp = new TuteeData();
                tmp.strTitle = result_data[0];
                tmp.strDate = result_data[1];
                tmp.state = Integer.parseInt(result_data[2]);
                tmp.tuteeid = Integer.parseInt(result_data[3]);
                tmp.class__id = classid;
                oData.add(tmp);
                //Log.i("클래스데이터", result_data[0]);
                //Log.i("클래스데이터", classData[0]);

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
