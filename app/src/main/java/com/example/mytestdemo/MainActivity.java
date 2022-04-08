package com.example.mytestdemo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    Timer timer = new Timer();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        if(textView.getText().equals("现在是晚上")){
                            textView.setText("现在是早上");
                        }else{
                            textView.setText("现在是晚上");
                        }
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
        start.setOnClickListener(this);
        end.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                },1000*10,1000*5);

                break;

            default:
                break;
        }
    }
}