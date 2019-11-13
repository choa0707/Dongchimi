package com.example.hobbyking.server;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageServer extends AsyncTask<String, Void, String> {
    private String sendMsg, receiveMsg, serverUrl, fileName;
    private int uid;

    public ImageServer(String sendMsg, String serverUrl, String fileName, int uid) {
        this.sendMsg = sendMsg;
        this.serverUrl = "http://192.168.56.1:8080/HobbyKing/"+serverUrl;
        this.fileName = fileName;
        this.uid = uid;
    }

    @Override
    // doInBackground의 매개값이 문자열 배열. 보낼 값이 여러개일 경우를 위해 배열로.
    protected String doInBackground(String... strings) {
        try {

            Log.i("이미지 테스트 ", "2");
            String lineEnd = "\r\n";
            String root = Environment.getExternalStorageDirectory().toString();
            String twoHyphens = "--";

            String boundary = "*****";

            try {
                String[] split_fileName = fileName.split("/");
                String tempfileName = split_fileName[split_fileName.length-1];
                Log.i("파일이름", tempfileName);
                String realFileName = uid+"__"+tempfileName;
               fileName.replaceAll(tempfileName, realFileName);

                Log.i("이미지 테스트 ", "3");
                File sourceFile = new File(fileName);
                Log.i("이미지 테스트 ", "3");
                DataOutputStream dos;
                Log.i("이미지 테스트 ", "11");
                if (!sourceFile.isFile()) {
                    Log.e("uploadFile", "Source File not exist :" + fileName);

                } else {
                    Log.i("이미지 테스트 ", fileName);
                    FileInputStream mFileInputStream = new FileInputStream(sourceFile);
                    Log.i("이미지 테스트 ", "13");
                    URL connectUrl = new URL(serverUrl);
                    Log.i("이미지 테스트 ", "14");
                    // open connection
                    HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                    Log.i("이미지 테스트 ", "15");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    Log.i("이미지 테스트 ", "4");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    Log.i("이미지 테스트 ", "6");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    Log.i("이미지 테스트 ", "7");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    Log.i("이미지 테스트 ", "8");
                    ///"아이디값,파일이름"

                    Log.i("이미지 테스트", realFileName);
                    conn.setRequestProperty("uploaded_file", fileName);

                    // write data

                    dos = new DataOutputStream(conn.getOutputStream());
                    Log.i("이미지 테스트 ", "9");
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    Log.i("이미지 테스트 ", "10");
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                    Log.i("이미지 테스트 ", "11");
                    dos.writeBytes(lineEnd);


                    int bytesAvailable = mFileInputStream.available();

                    int maxBufferSize = 1024 * 1024;

                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);


                    byte[] buffer = new byte[bufferSize];

                    int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);


                    // read image

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);

                        bytesAvailable = mFileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);

                        bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                    }


                    dos.writeBytes(lineEnd);

                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    mFileInputStream.close();

                    dos.flush(); // finish upload...

                   /* if (conn.getResponseCode() == 200) {

                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");

                        BufferedReader reader = new BufferedReader(tmp);

                        StringBuffer stringBuffer = new StringBuffer();

                        String line;

                        while ((line = reader.readLine()) != null) {

                            stringBuffer.append(line);

                        }
                        receiveMsg = buffer.toString();
                    }*/
                    if(conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer2 = new StringBuffer();
                        String str2;

                        //jsp에서 보낸 값을 받음
                        while ((str2 = reader.readLine()) != null) {
                            buffer2.append(str2);
                        }
                        receiveMsg = buffer2.toString();
                        Log.i("통신 결과", receiveMsg);
                    } else {
                        Log.i("통신 결과", conn.getResponseCode()+"에러");
                        // 통신이 실패했을 때 실패한 이유를 알기 위해 로그를 찍습니다.
                    }

                    mFileInputStream.close();

                    dos.close();

                }

            } catch (Exception e) {
                Log.i("이미지 테스트 ", "5");
                e.printStackTrace();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("이미지 테스트", receiveMsg);
        return receiveMsg;
    }
}
