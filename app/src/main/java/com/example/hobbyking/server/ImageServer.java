package com.example.hobbyking.server;

import android.os.AsyncTask;
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
    public String sendMsg, receiveMsg, serverUrl, fileName;

    public ImageServer(String sendMsg, String serverUrl, String fileName) {
        this.sendMsg = sendMsg;
        this.serverUrl = "http://192.168.56.1:8080/HobbyKing/"+serverUrl;
        this.fileName = fileName;
    }
    @Override
    // doInBackground의 매개값이 문자열 배열. 보낼 값이 여러개일 경우를 위해 배열로.
    protected String doInBackground(String... strings) {
        try {
            Log.i("이미지 테스트 ", "2");
            String lineEnd = "\r\n";

            String twoHyphens = "--";

            String boundary = "*****";

            try {
                Log.i("이미지 테스트 ", "3");
                File sourceFile = new File(fileName);
                Log.i("이미지 테스트 ", "3");
                DataOutputStream dos;

                if (!sourceFile.isFile()) {
                    Log.e("uploadFile", "Source File not exist :" + fileName);

                } else {
                    FileInputStream mFileInputStream = new FileInputStream(sourceFile);
                    URL connectUrl = new URL(serverUrl);
                    // open connection
                    HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
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

                    if (conn.getResponseCode() == 200) {

                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");

                        BufferedReader reader = new BufferedReader(tmp);

                        StringBuffer stringBuffer = new StringBuffer();

                        String line;

                        while ((line = reader.readLine()) != null) {

                            stringBuffer.append(line);

                        }

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
        String result = "";
        return result;
    }
}
