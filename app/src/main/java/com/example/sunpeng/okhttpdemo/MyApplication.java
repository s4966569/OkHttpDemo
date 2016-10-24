package com.example.sunpeng.okhttpdemo;

import android.app.Application;

/**
 * Created by sunpeng on 2016/10/24.
 */

public class MyApplication extends Application {
    private static MyApplication application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MyApplication getApplication() {
        return application;
    }
}
