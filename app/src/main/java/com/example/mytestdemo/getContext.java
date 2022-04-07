package com.example.mytestdemo;

import android.app.Application;
import android.content.Context;

public class getContext extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }
}
