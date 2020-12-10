package com.testsandroid.mitwittertest.commons;

import android.app.Application;
import android.content.Context;

public class MyAPP extends Application {
    private static MyAPP instance;

    public static  MyAPP getInstance(){
        return instance;
    }
    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

    }
}
