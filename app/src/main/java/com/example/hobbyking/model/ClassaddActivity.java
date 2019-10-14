package com.example.hobbyking.model;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.server.ConnectServer;
import com.example.hobbyking.server.ImageServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClassaddActivity extends AppCompatActivity {

    Button locationButton, dateButton, duedateButton, imageButton, registerButton;
    EditText nameEdit, date_hourEdit, date_minuateEdit, duedate_hourEdit, duedate_minuateEdit, priceEdit, detailEdit, tutorEdit, limitEdit;
    TextView locationTextView;
    String title, category, location, country, duehour, duemin, datehour,datemin, limit,detail, tutor, imageuri, price;
    Spinner categorySpinner, locationSpinner;
    List<String> locationList ;
    AlertDialog.Builder alertDialogBuilder;
    String sendMessage;
     CharSequence[] charSequenceItems;
    ImageView image1, image2, image3;
    String imageName;
    String img_path;
    final int PICTURE_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classadd);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        image1 = (ImageView)findViewById(R.id.cimg1);

        locationList = new ArrayList<String>();
        categorySpinner = (Spinner)findViewById(R.id.classadd_category);
        locationSpinner = (Spinner)findViewById(R.id.classadd_country);
        locationButton = (Button)findViewById(R.id.classadd_location_button);
        dateButton = (Button)findViewById(R.id.classadd_date_button);
        duedateButton = (Button)findViewById(R.id.classadd_duedate_button);
        imageButton = (Button)findViewById(R.id.classadd_image);
        registerButton = (Button)findViewById(R.id.classadd_register);

        limitEdit = (EditText)findViewById(R.id.classadd_limit);
        nameEdit = (EditText)findViewById(R.id.classadd_name);
        date_hourEdit = (EditText)findViewById(R.id.classadd_date_hour);
        date_minuateEdit = (EditText)findViewById(R.id.classadd_date_minuate);
        duedate_hourEdit = (EditText)findViewById(R.id.classadd_duedate_hour);
        duedate_minuateEdit = (EditText)findViewById(R.id.classadd_duedate_minuate);
        locationTextView = (TextView)findViewById(R.id.classadd_location);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        categorySpinner.setAdapter(adapter);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.country, android.R.layout.simple_spinner_item);
        locationSpinner.setAdapter(adapter2);



        //////location 데이터 가져온후



        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);

            }
        });


        locationButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getSpinner();
                int category_id = selectCategoryId();
                sendMessage = "category="+category_id+"&country="+country;
                Log.i("카테고리 선택 서버", sendMessage);
                ConnectServer connectserver = new ConnectServer(sendMessage, "get_location.jsp");
                try {
                    String result = connectserver.execute().get().toString();
                    if (!result.equals("fail    ")){
                        result = result.replace(" ", "");
                        String[] result_set = result.split("/");
                        for (int i = 0; i < result_set.length; i++)
                        {
                            locationList.add(result_set[i]);
                        }
                       charSequenceItems = locationList.toArray(new CharSequence[locationList.size()]);
                        alertDialogBuilder = new AlertDialog.Builder(ClassaddActivity.this);
                        alertDialogBuilder.setTitle("장소선택");
                        alertDialogBuilder.setItems(charSequenceItems, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                Toast.makeText(getApplicationContext(),
                                        charSequenceItems[id] + " 선택했습니다.",
                                        Toast.LENGTH_SHORT).show();
                                location = charSequenceItems[id].toString();
                                locationTextView.setText(location);
                                dialog.dismiss();
                            }
                        });
                        Log.i("다이얼로그", "세팅오케이");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "해당하는 장소가 없습니다.", Toast.LENGTH_LONG);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                get_data();
                check_ok();
            }

        });

    }

    private int selectCategoryId() {
        if (category.equals("뷰티"))return 1;
        else if(category.equals("프로그래밍")) return 2;
        else if(category.equals("여행")) return 3;
        else if(category.equals("영상제작")) return 4;
        else if(category.equals("운동")) return 5;
        else if(category.equals("영어회화")) return 6;
        else if(category.equals("요리")) return 7;
        else if(category.equals("포토샵")) return 8;
        else if(category.equals("음악")) return 9;
        else if(category.equals("중국어")) return 10;
        else if(category.equals("주식")) return 11;
        else return 0;

    }

    private void dialogSetting() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICTURE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                //기존 이미지 지우기
                image1.setImageResource(0);
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if(clipData!=null)
                {
                    Uri urione =  clipData.getItemAt(0).getUri();
                    image1.setImageURI(urione);

                }
                else if(uri != null)
                {
                    image1.setImageURI(uri);
                }

                try {
                    img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.
                    Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
                    //이미지를 비트맵형식으로 반환
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    int reWidth = (int) (getWindowManager().getDefaultDisplay().getWidth());
                    int reHeight = (int) (getWindowManager().getDefaultDisplay().getHeight());

                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                    Bitmap image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
                    ImageView image = (ImageView) findViewById(R.id.imageView);  //이미지를 띄울 위젯 ID값
                    image.setImageBitmap(image_bitmap_copy);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                //ClipData 또는 Uri를 가져온다

            }
        }
    }

    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(ClassaddActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;

        DoFileUpload("http://192.168.56.1:8080/HobbyKing/get_image.jsp", imgPath);  //해당 함수를 통해 이미지 전송.

        return imgPath;
    }

    public void DoFileUpload(String apiUrl, String absolutePath) {
        Log.i("이미지 테스트 ", "1");
        ImageServer imageServer = new ImageServer(absolutePath, "get_image.jsp", absolutePath);
        try{
            String result = imageServer.execute().get().toString();
            Log.i("이미지 서버", "성공");
        }catch (Exception e)
        {
            Log.i("이미지 서버", "실패");
        }

       // HttpFileUpload(apiUrl, "", absolutePath);

    }





    private  void getSpinner(){
        category = categorySpinner.getSelectedItem().toString();
        country = locationSpinner.getSelectedItem().toString();
    }
    private void get_data() {
        title = nameEdit.getText().toString();

        duehour = duedate_hourEdit.getText().toString();
        duemin = duedate_minuateEdit.getText().toString();
        datehour = date_hourEdit.getText().toString();
        datemin = date_minuateEdit.getText().toString();
        limit = limitEdit.getText().toString();
        price = priceEdit.getText().toString();
        detail = detailEdit.getText().toString();
        tutor = tutorEdit.getText().toString();
        imageuri = "";
    }

    private void check_ok() {

    }

  /*  public void HttpFileUpload(String urlString, String params, String fileName) {
        try {

            FileInputStream mFileInputStream = new FileInputStream(fileName);
            URL connectUrl = new URL(urlString);
            Log.d("Test", "mFileInputStream  is " + mFileInputStream);

            // HttpURLConnection 통신
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=");

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"");

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("Test", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            // close streams
            Log.e("Test", "File is written");
            mFileInputStream.close();
            dos.flush();
            // finish upload...

            // get response
            InputStream is = conn.getInputStream();

            StringBuffer b = new StringBuffer();
            for (int ch = 0; (ch = is.read()) != -1; ) {
                b.append((char) ch);
            }
            is.close();
            Log.e("Test", b.toString());

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
    } // end of HttpFileUpload()
*/
}
