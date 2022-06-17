package com.caolambaokhanh.multithreadex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText edtNumb;
    Button btnDraw;
    TextView txtPercent;
    ProgressBar pgbPercent;
    LinearLayout layoutContainer;

    Random random = new Random();

    public final int MSG_UPDATE_UI = 1;
    public final int MSG_UPDATE_UI_DONE = 2;

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    //UI Thread C1
//    Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(@NonNull Message message) {
//            int percent = message.arg1;
//            if(percent == 100){
//                Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
//            }else{
//                //update UI
//                int numb= (int) message.obj;
//                ImageButton button = new ImageButton(MainActivity.this);
//                button.setLayoutParams(params);
//                if(numb % 2 ==0){
//
//                    button.setImageResource(R.drawable.ic_baseline_mood_smile);
//                }else{
//                    button.setImageResource(R.drawable.ic_baseline_mood_bad);
//                }
//                layoutContainer.addView(button);
//            }
//            txtPercent.setText(percent + "%");
//            pgbPercent.setProgress(percent);
//            return true;
//        }
//    });
    //UI Thread C2
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_UI:
                    int numb = (int) msg.obj;
                    //UPDATE UI ...
                    ImageButton button = new ImageButton(MainActivity.this);
                    button.setLayoutParams(params);
                    if(numb % 2 ==0){
                        button.setImageResource(R.drawable.ic_baseline_mood_smile);
                    }else{
                        button.setImageResource(R.drawable.ic_baseline_mood_bad);
                    }
                    layoutContainer.addView(button);
                    int percent =msg.arg1;
                    txtPercent.setText(percent + " %"); //percent
                    pgbPercent.setProgress(percent);
                    break;
                case MSG_UPDATE_UI_DONE:
                    //DONE
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkviews();
        addEvent();
    }

    private void linkviews() {
        edtNumb= findViewById(R.id.edtNumbOfViews);
        btnDraw=findViewById(R.id.btnDraw);
        txtPercent=findViewById(R.id.txtPer);
        pgbPercent=findViewById(R.id.pgbShow);
        layoutContainer=findViewById(R.id.layoutContainer);
    }

    private void addEvent() {
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runBackgroundThread();
            }
        });
    }

    private void runBackgroundThread() {
//        layoutContainer.removeAllViews();
//        int numb = Integer.parseInt(edtNumb.getText().toString());
//        Thread thread =new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //code here
//                for(int i=0;i<=numb;i++){
//                    Message message= handler.obtainMessage();
//                    message.arg1 = i*100/numb; //Percent
//                    message.obj = random.nextInt(100);
//                    handler.sendMessage(message);
//                    SystemClock.sleep(100);
//                }
//            }
//        });
//        thread.start();
        layoutContainer.removeAllViews();
        int numb = Integer.parseInt(edtNumb.getText().toString());
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1; i<=numb; i++){
                    Message message = new Message();
                    message.what = MSG_UPDATE_UI;
                    message.arg1 = i*100/numb; //Percent
                    message.obj = random.nextInt(100);
                    handler.sendMessage(message);
                    SystemClock.sleep(100);
                }
                handler.sendEmptyMessage(MSG_UPDATE_UI_DONE);
            }
        });
        thread.start();
    }
}