package com.example.t.myapplication.util;

import com.baidu.mapapi.SDKInitializer;
import com.example.t.myapplication.myexception.CrashHandler;

/**
 * Created by T on 2016/9/21.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
