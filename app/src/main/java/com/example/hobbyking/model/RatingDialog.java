package com.example.hobbyking.model;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.hobbyking.R;
import com.example.hobbyking.data.ClassData;
import com.example.hobbyking.server.ConnectServer;

import java.util.concurrent.ExecutionException;

public class RatingDialog extends Dialog{

    private Button positiveButton;
    private Button negativeButton;
    private ClassData classData;
    private int uid;
    public RatingBar ratingBar;
    private Context context;

    private RatingDialogListener ratingDialogListener;

    public RatingDialog(Context context, ClassData classData, int uid) {
        super(context);
        this.uid = uid;
        this.classData = classData;
        this.context = context;
    }


    //인터페이스 설정
    interface RatingDialogListener{
        void onPositiveClicked(String name, String age, String addr);
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(RatingDialogListener customDialogListener){
        this.ratingDialogListener = ratingDialogListener;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating_dialog);
        ratingBar = (RatingBar)findViewById(R.id.dialogRb);
        positiveButton = (Button)findViewById(R.id.pbutton);
        negativeButton = (Button)findViewById(R.id.nbutton);
        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);


        //init
        positiveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ratingBar.getRating();
                String sendMessage = "tutee_id="+uid+"&class_id="+classData.getClassid()+"&rate="+ratingBar.getRating();
             ConnectServer connectserver = new ConnectServer(sendMessage, "getClassData_setRating.jsp");
            try {
                String result = connectserver.execute().get();
                Log.i("클래스데이터", result);
                if (result.equals("success    "))
                {
                    Toast.makeText(getContext(), "평가를 완료 하였습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "서버 오류로 평가를 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    Log.i("별점", result);
                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "평가를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });


    }

}
