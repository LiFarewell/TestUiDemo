package com.example.mytestdemo;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;
import android.os.Handler;

import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    PendingIntent pendingIntent;
    long start = SystemClock.elapsedRealtime() + 1000 * 10;
    long repeat = 5 * 1000;
    AlarmManager alarmManager;
    TextView textView;
    IntentFilter intentFilter;
    myReceiver myReceiver = new myReceiver();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("time", "run: alarm");
                    Intent intent1 = new Intent("android.example.change");
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent1, PendingIntent.FLAG_NO_CREATE);
                    sendBroadcast(intent1);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = findViewById(R.id.start);
        Button end = findViewById(R.id.end);
        textView = findViewById(R.id.tv);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentFilter = new IntentFilter("android.example.change");
                registerReceiver(myReceiver, intentFilter);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000 * 10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();


            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmManager.cancel(pendingIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);

    }

    public class myReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, start, repeat, pendingIntent);
                            Log.d("time", "run: 3");
                            Log.d("time", "run: " + SystemClock.elapsedRealtime());
                            Toast.makeText(MainActivity.this, "到了这里", Toast.LENGTH_SHORT).show();
                            if (textView.getText().equals("现在是晚上")) {
                                textView.setText("现在是早上");
                            } else {
                                textView.setText("现在是晚上");
                            }
                        }
                    });
                }
            }).start();

        }
    }


}