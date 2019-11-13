package com.example.hobbyking.model;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClassaddActivity extends AppCompatActivity {
    int uid;

    int d_year , d_month,d_day;

    int du_year, du_month, du_day;
    Button locationButton, dateButton, duedateButton, imageButton, registerButton;
    ToggleButton price0, price1, price2, price3;
    EditText nameEdit, date_hourEdit, date_minuateEdit, priceEdit, detailEdit, tutorEdit, limitEdit, class_timeEdit;
    TextView locationTextView, classWeek, classduedate;
    String title, category, location, country, duehour, duemin, datehour,datemin, limit,detail, tutor, imageuri, price, locationString, date_time, date_week, class_time, location_name;
    Spinner categorySpinner, locationSpinner;
    List<String> locationList ;
    AlertDialog.Builder alertDialogBuilder;
    String sendMessage, due_date, imageUrl, real_date;
     CharSequence[] charSequenceItems;
    ImageView image1, image2, image3;
    String imageName;
    String img_path;
    private DatePickerDialog.OnDateSetListener callbackMethod1, callbackMethod2;
    final int PICTURE_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classadd);
        d_year = du_year = Calendar.getInstance().get(Calendar.YEAR);
        d_month = du_month = Calendar.getInstance().get(Calendar.MONTH);
        d_day = du_day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        SharedPreferences autoLogin = this.getSharedPreferences("auto", Context.MODE_PRIVATE);
        uid = autoLogin.getInt("UID", 0);
        imageUrl="";
        this.InitializeListener();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        image1 = (ImageView)findViewById(R.id.cimg1);

        classWeek = (TextView)findViewById(R.id.classadd_week) ;
        locationList = new ArrayList<String>();
        categorySpinner = (Spinner)findViewById(R.id.classadd_category);
        locationSpinner = (Spinner)findViewById(R.id.classadd_country);
        locationButton = (Button)findViewById(R.id.classadd_location_button);
        dateButton = (Button)findViewById(R.id.classadd_date_button);
        duedateButton = (Button)findViewById(R.id.classadd_duedate_button);
        imageButton = (Button)findViewById(R.id.classadd_image);
        registerButton = (Button)findViewById(R.id.classadd_register);
        price0 = (ToggleButton) findViewById(R.id.price0);
        price1 = (ToggleButton)findViewById(R.id.price1);
        price2 = (ToggleButton)findViewById(R.id.price2);
        price3 = (ToggleButton)findViewById(R.id.price3);
        classduedate = (TextView)findViewById(R.id.classadd_duedate_text) ;
        limitEdit = (EditText)findViewById(R.id.classadd_limit);
        nameEdit = (EditText)findViewById(R.id.classadd_name);
        date_hourEdit = (EditText)findViewById(R.id.classadd_date_hour);
        tutorEdit = (EditText)findViewById(R.id.classadd_tutor);
        detailEdit = (EditText)findViewById(R.id.classadd_details);
        date_minuateEdit = (EditText)findViewById(R.id.classadd_date_minuate);
        priceEdit = (EditText)findViewById(R.id.classadd_price) ;
        locationTextView = (TextView)findViewById(R.id.classadd_location);
        class_timeEdit = (EditText)findViewById(R.id.classadd_date_time);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        categorySpinner.setAdapter(adapter);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.country, android.R.layout.simple_spinner_item);
        locationSpinner.setAdapter(adapter2);



        //////location 데이터 가져온후

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(ClassaddActivity.this, callbackMethod2, d_year, d_month, d_day);
                dialog.show();
            }
        });
        priceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              //  Toast.makeText(getApplicationContext(), "Editchanger test", Toast.LENGTH_LONG).show();
                //if (Integer.parseInt(priceEdit.getText().toString()) > price1.isChecked())
            }
        });
        duedateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(ClassaddActivity.this, callbackMethod1, du_year, du_month, du_day);
                dialog.show();
            }
        });
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
                locationList.clear();
                getSpinner();
                int category_id = selectCategoryId();
                sendMessage = "category="+category_id+"&country="+country;
                Log.i("카테고리 선택 서버", sendMessage);
                ConnectServer connectserver = new ConnectServer(sendMessage, "get_location.jsp");
                try {
                    String result = connectserver.execute().get().toString();

                    if (!result.equals("fail    ")){
                        String[] result_set = result.split("/");
                        int i;
                        for (i = 1; i < result_set.length; i++)
                        {
                            locationList.add(result_set[i]);
                            Log.i("24일 장소", result_set[i].toString());
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
                                String[] get_locationname = location.split("\"");
                                location_name = get_locationname[1];
                                Log.i("24일 장소이름", location_name);
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
                        Toast.makeText(getApplicationContext(), "해당하는 장소가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        price0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price0.isChecked())
                {allwhite();
                    price0.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Yellow));
                    price1.setChecked(false);
                    price2.setChecked(false);
                    price3.setChecked(false);
                    priceEdit.setText("0");
                }
                else{
                    price0.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.White));
                    price0.setBackgroundResource(R.drawable.btn_background_black);

                    priceEdit.setText("");
                }
            }
        });
        price1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price1.isChecked())
                {allwhite();
                    price1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Yellow));
                    price0.setChecked(false);
                    price2.setChecked(false);
                    price3.setChecked(false);
                    priceEdit.setHint("10,000~30,000");
                }
                else{
                    price1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.White));
                    price1.setBackgroundResource(R.drawable.btn_background_black);

                    priceEdit.setText("");
                }
            }
        });
        price2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price2.isChecked())
                {allwhite();
                    price2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Yellow));
                    priceEdit.setHint("30,000~70,000");
                    price0.setChecked(false);
                    price1.setChecked(false);
                    price3.setChecked(false);
                }
                else{
                    price2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.White));
                    price2.setBackgroundResource(R.drawable.btn_background_black);

                    priceEdit.setText("");
                }
            }
        });
        price3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price3.isChecked())
                {
                    allwhite();
                    price3.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Yellow));
                    priceEdit.setHint("70,000~100,000");
                    price1.setChecked(false);
                    price2.setChecked(false);
                    price0.setChecked(false);
                }
                else{
                    price3.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.White));
                    price3.setBackgroundResource(R.drawable.btn_background_black);

                    priceEdit.setText("");
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                get_data();
                Log.i("클래스등록서", "테스트1");
                if (check_ok())
                {
                    if (check_price())
                    {
                        Log.i("클래스등록서", "테스트");
                        int category_id = selectCategoryId();
                        Log.i("클래스등록서", sendMessage);
                        imageUrl = imageUrl.replace(" ","");
                        Log.i("마지막 이미지", imageUrl);
                        String sendMessage = "name="+title+"&category="+category_id+"&location="+location_name+"&duedate="+due_date+"&datehoure="+datehour +"&datemin="
                                +datemin+"&limit="+limit+"&price="+price+"&detail="+detail+"&tutor="+tutor+"&date_time="+date_time+"&real_date="+real_date+
                                "&class_time="+class_time+"&image="+imageUrl+"&tutorid="+uid;
                        Log.i("클래스등록서", sendMessage);
                        ConnectServer connectserver = new ConnectServer(sendMessage, "ClassRegister.jsp");
                        String result = null;
                        try {
                            result = connectserver.execute().get().toString();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(result.equals("success    ")) {
                            Toast.makeText(getApplicationContext(),"수업이 추가되었습니다.",Toast.LENGTH_SHORT).show();
                            finish();
                        } else if(result.equals("fail    ")) {

                            Toast.makeText(getApplicationContext(),"실패하였습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "범위에 맞는 금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if (imageUrl.equals("")) Toast.makeText(getApplicationContext(), "이미지를 추가해주세요.", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getApplicationContext(), "모든 사항을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private boolean check_price() {
         if ((price0.isChecked() && !priceEdit.getText().toString().equals("0")) || (price1.isChecked() && (Integer.parseInt(priceEdit.getText().toString()) < 0 || Integer.parseInt(priceEdit.getText().toString()) > 30000)) || (price2.isChecked() && (Integer.parseInt(priceEdit.getText().toString()) < 30000 || Integer.parseInt(priceEdit.getText().toString()) > 70000)) || (price3.isChecked() && (Integer.parseInt(priceEdit.getText().toString()) < 70000)))
         {
            return false;
         }else return true;
    }

    void allwhite()
    {price0.setBackgroundResource(R.drawable.btn_background_black);
        price1.setBackgroundResource(R.drawable.btn_background_black);
        price2.setBackgroundResource(R.drawable.btn_background_black);
        price3.setBackgroundResource(R.drawable.btn_background_black);
        price0.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.White));
        price1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.White));
        price2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.White));
        price3.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.White));
    }
  /*  void show()
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("월");
        ListItems.add("화");
        ListItems.add("수");
        ListItems.add("목");
        ListItems.add("금");
        ListItems.add("토");
        ListItems.add("일");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        final List SelectedItems  = new ArrayList();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            //사용자가 체크한 경우 리스트에 추가
                            SelectedItems.add(which);
                        } else if (SelectedItems.contains(which)) {
                            //이미 리스트에 들어있던 아이템이면 제거
                            SelectedItems.remove(Integer.valueOf(which));
                        }
                    }
                });
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="";
                        date_week = "";
                        for (int i = 0; i < SelectedItems.size(); i++) {
                            int index = (int) SelectedItems.get(i);
                            msg=msg+ListItems.get(index);
                            if (ListItems.get(index).equals("월")) date_week += 1;
                            else if (ListItems.get(index).equals("화")) date_week += 2;
                            else if (ListItems.get(index).equals("수")) date_week += 3;
                            else if (ListItems.get(index).equals("목")) date_week += 4;
                            else if (ListItems.get(index).equals("금")) date_week += 5;
                            else if (ListItems.get(index).equals("토")) date_week += 6;
                            else if (ListItems.get(index).equals("일")) date_week += 7;
                            if (i != SelectedItems.size()-1)
                            {
                                msg+= ",";
                                date_week += ",";
                            }
                        }
                        classWeek.setText("선택 요일 : "+msg);
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }*/
    public void InitializeListener()
    {
        callbackMethod1 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear += 1;
                due_date = year+"-"+monthOfYear+"-"+dayOfMonth;
                du_day = dayOfMonth;
                du_month = monthOfYear-1;
                du_year = year;
                classduedate.setText("마감 날짜 : "+year + "년" + monthOfYear + "월" + dayOfMonth + "일");
            }
        };
        callbackMethod2 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear += 1;
                d_day = dayOfMonth;
                d_month = monthOfYear-1;
                d_year = year;
                real_date = year+"-"+monthOfYear+"-"+dayOfMonth;
                classWeek.setText("수업 날짜 : "+year + "년" +monthOfYear+  "월" + dayOfMonth + "일");
            }
        };
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
                   // Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(ClassaddActivity.this, "이미지를 추가하였습니다.", Toast.LENGTH_SHORT).show();
        this.imageName = imgName;

        DoFileUpload("http://192.168.56.1:8080/HobbyKing/get_image.jsp", imgPath);  //해당 함수를 통해 이미지 전송.

        return imgPath;
    }

    public void DoFileUpload(String apiUrl, String absolutePath) {
        String result="";
        Log.i("이미지 테스트 ", "1");

        ImageServer imageServer = new ImageServer(absolutePath, "get_image.jsp", absolutePath, uid);
        try{
            result = imageServer.execute().get().toString();
            Log.i("이미지 서버", "성공");
        }catch (Exception e)
        {
            Log.i("이미지 서버", "실패");
        }
        imageUrl = result;
        Log.i("이미지 테스트", result);
       // HttpFileUpload(apiUrl, "", absolutePath);

    }





    private  void getSpinner(){
        category = categorySpinner.getSelectedItem().toString();
        country = locationSpinner.getSelectedItem().toString();
    }
    private void get_data() {
        locationString = locationTextView.getText().toString();
        title = nameEdit.getText().toString();
        datehour = date_hourEdit.getText().toString();
        datemin = date_minuateEdit.getText().toString();
        limit = limitEdit.getText().toString();
        price = priceEdit.getText().toString();
        detail = detailEdit.getText().toString();
        tutor = tutorEdit.getText().toString();
        class_time = class_timeEdit.getText().toString();
        imageuri = "";
        date_time = datehour+","+datemin;
    }

    private boolean check_ok() {
        if (title==null ||title.equals("") || datehour==null ||datehour.equals("") || datemin==null ||datemin.equals("")|| limit==null||limit.equals("") || price==null
                || price.equals("")|| detail.equals("")||
                tutor.equals("") ||locationString==null || locationString.equals("")|| date_time==null ||date_time.equals("")|| class_time==null || imageUrl.equals(""))
            return false;
        else {
            return true;
        }
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
