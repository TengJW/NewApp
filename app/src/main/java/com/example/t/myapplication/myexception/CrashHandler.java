package com.example.t.myapplication.myexception;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by T on 2016/9/26.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler crashHandler;
    private Context context;


    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    /**
     * 初始化操作
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleExcepTion(e) && crashHandler != null) {
            crashHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {

            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }

    private boolean handleExcepTion(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "程序出现了点小问题，将要退出", Toast.LENGTH_LONG).show();
                Looper.loop();
                //获取程序的信息以及取出异常信息以文件的形式保存到本地
            }
        }.start();
        return true;
    }
}
