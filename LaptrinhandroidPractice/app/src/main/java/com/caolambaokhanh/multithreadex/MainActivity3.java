package com.caolambaokhanh.multithreadex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
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

public class MainActivity3 extends AppCompatActivity {
    EditText edtNumb;
    Button btnDraw;
    TextView txtPercent;
    ProgressBar pgbPercent;
    LinearLayout layoutContainer;

    Random random = new Random();

    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        linkviews();
        addEvents();
    }

    private void linkviews() {
        edtNumb= findViewById(R.id.edtNumbOfViews);
        btnDraw=findViewById(R.id.btnDraw);
        txtPercent=findViewById(R.id.txtPer);
        pgbPercent=findViewById(R.id.pgbShow);
        layoutContainer=findViewById(R.id.layoutContainer);
    }

    private void addEvents() {
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numb = Integer.parseInt(edtNumb.getText().toString());
                myAsyncTask myAsyncTask  = new myAsyncTask();
                myAsyncTask.execute(numb);
            }
        });
    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtPercent.setText("00%");
            layoutContainer.removeAllViews();
        }

        @Override
        protected void onPostExecute(Void unused) {

            super.onPostExecute(unused);
            Toast.makeText(MainActivity3.this, "DONE", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
            int percent = values[0], randomNumb = values[1];
            txtPercent.setText(percent + "%");
            pgbPercent.setProgress(percent);

            ImageButton button =new ImageButton(MainActivity3.this);
            button.setLayoutParams(layoutParams);
            if(randomNumb % 2 == 0){
                button.setImageResource(R.drawable.ic_baseline_mood_smile);
            }else{
                button.setImageResource(R.drawable.ic_baseline_mood_bad);
            }
            layoutContainer.addView(button);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int numb = integers[0], percent, randomNumb;
            for(int i=1;i<=numb;i++){
                percent =i*100/numb;
                randomNumb = random.nextInt(100);
                publishProgress(percent, randomNumb);
                SystemClock.sleep(100);
            }
            return null;
        }
    }
}