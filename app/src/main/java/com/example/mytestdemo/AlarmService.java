package com.example.mytestdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AlarmService extends Service {
    PendingIntent pendingIntent;
    long start;
    long repeat;
    AlarmManager alarmManager;



    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("time", "run: alarm");
                Intent intent1 = new Intent("android.example.change");
                sendBroadcast(intent1);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                start = SystemClock.currentThreadTimeMillis()+1000*10;
                repeat =5*1000;
                pendingIntent = PendingIntent.getBroadcast(AlarmService.this,0,intent1,0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, start, pendingIntent);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, start, pendingIntent);
                } else {
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, start, repeat, pendingIntent);
                }

            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        alarmManager.cancel(pendingIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
