package com.caolambaokhanh.multithreadex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    EditText edtNumb;
    Button btnDraw;
    TextView txtPercent;
    ProgressBar pgbPercent;
    LinearLayout layoutContainer;

    int numb, percent, randomNumb;

    Random random = new Random();
    LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(200,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    Handler handler = new Handler();
    Runnable UIThread =new Runnable() {
        @Override
        public void run() {
            if(percent == 100 ){
                Toast.makeText(MainActivity2.this, "DONE", Toast.LENGTH_SHORT).show();
            }else{
                ImageButton button =new ImageButton(MainActivity2.this);
                button.setLayoutParams(layoutParams);
                if(randomNumb % 2 ==0){
                    button.setImageResource(R.drawable.ic_baseline_mood_smile);
                }else{
                    button.setImageResource(R.drawable.ic_baseline_mood_bad);
                }
                layoutContainer.addView(button);
            }
            txtPercent.setText(percent + "%");
            pgbPercent.setProgress(percent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
        layoutContainer.removeAllViews();
        numb = Integer.parseInt(edtNumb.getText().toString());
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=numb;i++){
                    percent = i*100/numb;
                    randomNumb =random.nextInt(100);
                    handler.post(UIThread);
                    SystemClock.sleep(100);
                }
            }
        });
        thread.start();
    }


}