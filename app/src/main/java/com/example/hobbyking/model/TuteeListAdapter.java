package com.example.hobbyking.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.server.ConnectServer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TuteeListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<TuteeData> m_oData = null;
    private int nListCnt = 0;

    public TuteeListAdapter(ArrayList<TuteeData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.tuteelist_item, parent, false);
        }

        TextView oTextTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.textDate);
        Button oButton = (Button) convertView.findViewById(R.id.approve);
        oButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_oData.get(position).state == 0)
                {
                    String sendMessage = "tuteeid="+m_oData.get(position).tuteeid+"&classid="+m_oData.get(position).class__id;
                    ConnectServer connectserver = new ConnectServer(sendMessage, "approveTutee.jsp");
                    try {
                        String result = connectserver.execute().get();
                        if (result.equals("success    "))
                        {
                            Toast.makeText(v.getContext(), "수강신청을 승인하였습니다.", Toast.LENGTH_SHORT).show();
                            oButton.setText("승인 취소");
                            m_oData.get(position).state = 1;
                        }else Toast.makeText(v.getContext(), "서버 문제로 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if (m_oData.get(position).state == 1)
                {
                    String sendMessage = "tuteeid="+m_oData.get(position).tuteeid+"&classid="+m_oData.get(position).class__id;
                    ConnectServer connectserver = new ConnectServer(sendMessage, "rejectTutee.jsp");
                    try {
                        String result = connectserver.execute().get();
                        if (result.equals("success    "))
                        {
                            m_oData.get(position).state = 0;
                            Toast.makeText(v.getContext(), "승인을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                            oButton.setText("수강 승인");
                        }else Toast.makeText(v.getContext(), "서버 문제로 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        if (m_oData.get(position).state == 0)
        {
            oButton.setText("수강 승인");

        }
        else if (m_oData.get(position).state == 1)
        {
            oButton.setText("승인 취소");

        }
        oTextTitle.setText(m_oData.get(position).strTitle);
        oTextDate.setText(m_oData.get(position).strDate);
        return convertView;
    }

}
