package com.example.hobbyking.server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownActivity extends AsyncTask<String, Void, String> {
    public String sendMsg, receiveMsg, serverUrl;

    public ImageDownActivity(String sendMsg, String serverUrl) {
        this.sendMsg = sendMsg;
        this.serverUrl = "http://192.168.56.1:8080/HobbyKing/"+serverUrl;
    }
    @Override
    // doInBackground의 매개값이 문자열 배열. 보낼 값이 여러개일 경우를 위해 배열로.
    protected String doInBackground(String... strings) {

        try {
            String str;
            URL url = new URL(serverUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            osw.write(sendMsg);//OutputStreamWriter에 담아 전송
            osw.flush();
            //jsp와 통신이 정상적으로 되었을 때 할 코드
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                //jsp에서 보낸 값을 받음
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("통신 결과", receiveMsg);
            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
                // 통신이 실패했을 때 실패한 이유를 알기 위해 로그를 찍습니다.
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //jsp로부터 받은 리턴 값입니다.
        return receiveMsg;
    }
}
