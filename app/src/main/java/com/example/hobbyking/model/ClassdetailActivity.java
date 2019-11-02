package com.example.hobbyking.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.fragment.ClassinfoFragment;

import com.example.hobbyking.fragment.ClasstimeFragment;
import com.example.hobbyking.fragment.TutorinfoFragment;
import com.example.hobbyking.server.ConnectServer;
import com.example.hobbyking.server.VolleyHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClassdetailActivity extends AppCompatActivity {
    private static final String LOGIN_REQUEST_URL = "http://192.168.56.1:8080/HobbyKing/IMG_20191014_09533111.jpg";
    TextView classname, classprice;
    private ClassData classData;
    Button class_apply_button;
    int isfavorit = 0;
    ImageButton back, favorit;
    private ImageLoader mImageLoader;
    CustomSwipeAdapter adapter;
    ViewPager viewPager;
    int fail = 0;
    int over = 0;
    private CircleAnimIndicator circleAnimIndicator;
//detailnetworkImageView
NetworkImageView mNetworkImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classdetail);
        class_apply_button = (Button)findViewById(R.id.classapply);
        favorit = (ImageButton)findViewById(R.id.addfavorit);
        back = (ImageButton)findViewById(R.id.backbtn);
        mImageLoader = VolleyHelper.getInstance(getApplicationContext()).getImageLoader();
        mNetworkImageView = (NetworkImageView) findViewById(R.id.detailnetworkImageView);
        mNetworkImageView.setImageUrl(LOGIN_REQUEST_URL, mImageLoader);
        classname = (TextView)findViewById(R.id.classTitle);
        classprice = (TextView)findViewById(R.id.classPrice);
        classData = (ClassData)getIntent().getSerializableExtra("ClassData");
        classname.setText(classData.getName());
        classprice.setText(classData.getPrice()+" 원/회당");
        if (classData.getPeople_num() >= classData.getLimit_people_num())fail = 1;
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Log.i("날짜비교", today);
        String[] caltoday = today.split("-");
        String[] duedate = classData.getDuedate().split("-");
        Log.i("날짜비교", duedate[0]);
        Log.i("날짜비교", duedate[1]);
        Log.i("날짜비교", duedate[2]);
        Log.i("날짜비교", caltoday[0]);
        Log.i("날짜비교", caltoday[1]);
        Log.i("날짜비교", caltoday[2]);

        if (Integer.parseInt(duedate[0]) < Integer.parseInt(caltoday[0])) over = 1;
        else if (Integer.parseInt(duedate[0]) == Integer.parseInt(caltoday[0]))
        {
            if (Integer.parseInt(duedate[1])< Integer.parseInt(caltoday[1])) over = 1;
            else if (Integer.parseInt(duedate[1]) == Integer.parseInt(caltoday[1]))
            {
                if (Integer.parseInt(duedate[2])< Integer.parseInt(caltoday[2])) over = 1;
            }
        }

       /* viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        circleAnimIndicator = (CircleAnimIndicator)findViewById(R.id.circleAnimIndicator);
        circleAnimIndicator.setItemMargin(15);
        circleAnimIndicator.setAnimDuration(300);
        circleAnimIndicator.createDotPanel(3, R.drawable.baseline_favorite_border_black_18 , R.drawable.baseline_favorite_black_18);
*/

       getFavorit();
        Button button_classinfo = (Button)findViewById(R.id.classInfo);
        Button button_tutorinfo = (Button)findViewById(R.id.tutorInfo);
        Button button_classtime = (Button)findViewById(R.id.classTime);


        Bundle bundle=new Bundle();
        bundle.putSerializable("classdata", classData);
        ClassinfoFragment classinfoFragment = new ClassinfoFragment();
        classinfoFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.classFrame, classinfoFragment).commit();
        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isfavorit == 1)
                {
                    isfavorit = 0;
                    favorit.setImageResource(R.drawable.baseline_favorite_border_black_36);
                    deleteFavorit();
                }
                else
                {
                    isfavorit = 1;
                    favorit.setImageResource(R.drawable.baseline_favorite_black_36);
                    addFavorit();
                }

            }
        });
        class_apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fail == 1)
                {
                    Toast.makeText(getApplicationContext(), "수강 인원이 가득찼습니다.", Toast.LENGTH_LONG).show();
                }
                else if (over == 1)
                {
                    Toast.makeText(getApplicationContext(), "수강신청 기간이 지났습니다.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_classinfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ClassinfoFragment classinfoFragment = new ClassinfoFragment();
                classinfoFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.classFrame, classinfoFragment).commit();

            }
        });



        button_classtime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ClasstimeFragment classtimeFragment = new ClasstimeFragment();
                classtimeFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.classFrame, classtimeFragment).commit();
            }
        });

        button_tutorinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutorinfoFragment tutorinfoFragment = new TutorinfoFragment();
                tutorinfoFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.classFrame, tutorinfoFragment).commit();
            }
        });
    }

    private void deleteFavorit() {
        SharedPreferences autoLogin = this.getSharedPreferences("auto", Context.MODE_PRIVATE);
        int uid = autoLogin.getInt("UID", 0);
        String sendMessage = "uid="+uid+"&classid="+classData.getClassid();
        ConnectServer connectserver = new ConnectServer(sendMessage, "deletefavorit.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("favorit", result);

            if (result.equals("success    "))
            {
                isfavorit = 0;
                favorit.setImageResource(R.drawable.baseline_favorite_border_black_36);
                Toast.makeText(getApplicationContext(), "위시리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }else{

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addFavorit() {
        SharedPreferences autoLogin = this.getSharedPreferences("auto", Context.MODE_PRIVATE);
        int uid = autoLogin.getInt("UID", 0);
        String sendMessage = "uid="+uid+"&classid="+classData.getClassid();
        ConnectServer connectserver = new ConnectServer(sendMessage, "addfavorit.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("favorit", result);

            if (result.equals("success    "))
            {
                isfavorit = 1;
                favorit.setImageResource(R.drawable.baseline_favorite_black_36);
                Toast.makeText(getApplicationContext(), "위시리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }else{

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getFavorit() {
        SharedPreferences autoLogin = this.getSharedPreferences("auto", Context.MODE_PRIVATE);
        int uid = autoLogin.getInt("UID", 0);
        String sendMessage = "uid="+uid+"&classid="+classData.getClassid();
        ConnectServer connectserver = new ConnectServer(sendMessage, "isfavorit.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("favorit", result);

            if (result.equals("yes    "))
            {
                isfavorit = 1;
                favorit.setImageResource(R.drawable.baseline_favorite_black_36);
            }else{
                favorit.setImageResource(R.drawable.baseline_favorite_border_black_36);
                isfavorit = 0;
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("클래스 신청");
        builder.setMessage(classData.getName()+" 클래스를 신청하겠습니까?"+"\n현재 수강인원("+classData.getPeople_num()+"/"+classData.getLimit_people_num()+")");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"수강신청이 되었습니다.",Toast.LENGTH_LONG).show();
                        Apply();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
    void Apply()
    {
        SharedPreferences autoLogin = this.getSharedPreferences("auto", Context.MODE_PRIVATE);
        int uid = autoLogin.getInt("UID", 0);
        String sendMessage = "tuteeid="+uid+"&classid="+classData.getClassid()+"&tutorid="+classData.getTutor_id();
        ConnectServer connectserver = new ConnectServer(sendMessage, "apply_class.jsp");
        try {
            String result = connectserver.execute().get();
            Log.i("수강신청", result);

            if (result.equals("success    "))
            {
                Toast.makeText(getApplicationContext(), "수강신청이 완료 되었습니다.", Toast.LENGTH_LONG).show();
                classData.setPeople_num(classData.getPeople_num()+1);
            }else if (result.equals("fail    "))Toast.makeText(getApplicationContext(), "이미 신청한 클래스입니다.", Toast.LENGTH_LONG).show();
            else
            {
                Toast.makeText(getApplicationContext(), "서버문제로 수강신청에 실패하였습니다.", Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
