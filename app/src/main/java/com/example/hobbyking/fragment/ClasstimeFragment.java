package com.example.hobbyking.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.model.ClassaddActivity;
import com.example.hobbyking.server.ConnectServer;

import java.util.concurrent.ExecutionException;

public class ClasstimeFragment extends Fragment {

    ClassData classData;
    TextView classtime;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        classData = (ClassData)getArguments().getSerializable("classdata");
        View view = inflater.inflate(R.layout.fragment_classtime, null);
        classtime = (TextView)view.findViewById(R.id.detail_date);
        String sendMessage = "location="+Integer.toString(classData.getClassid());
        String[] datetime, dateweek;
        int time;
        int hour=0, minuate=0;
        String datetime_s="", dateweek_s="";
        ConnectServer connectserver = new ConnectServer(sendMessage, "location_name.jsp");
        try {
            String result = connectserver.execute().get().toString();
            Log.i("장소", result);
            datetime = classData.getDate_time().split(",");
            datetime_s = datetime[0]+"시 "+datetime[1]+"분";
            time = classData.getClass_time();
            if (time >= 60){ hour = time/60; minuate = time-hour*60;}
            else minuate = time;
            if (Integer.parseInt(datetime[1])+minuate >= 60) {hour += 1; minuate-=60;}
            hour += Integer.parseInt(datetime[0]);
            minuate  += Integer.parseInt(datetime[1]);
            datetime_s += " ~ "+ Integer.toString(hour) + "시 "+ minuate + "분 까지";
            dateweek = classData.getDate_week().split(",");
            for (int i = 0; i < dateweek.length; i++)
            {
                if (Integer.parseInt(dateweek[i]) == 1)dateweek_s += "월";
                else if (Integer.parseInt(dateweek[i]) == 2)dateweek_s += "화";
                else if (Integer.parseInt(dateweek[i]) == 3)dateweek_s += "수";
                else if (Integer.parseInt(dateweek[i]) == 4)dateweek_s += "목";
                else if (Integer.parseInt(dateweek[i]) == 5)dateweek_s += "금";
                else if (Integer.parseInt(dateweek[i]) == 6)dateweek_s += "토";
                else if (Integer.parseInt(dateweek[i]) == 7)dateweek_s += "일";
                if (i != dateweek.length-1) dateweek_s += ", ";
            }
            classtime.setText("진행장소: "+result+"\n"+"진행 요일: "+dateweek_s+"\n"+"진행시간: "+datetime_s);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }


}
