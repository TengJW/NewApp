package com.example.t.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.t.myapplication.model.parser.BaseEntry;
import com.example.t.myapplication.model.parser.Reister;

import cn.sharesdk.framework.Platform;

/**
 * Created by T on 2016/9/9.
 */
public class ShareUtil {
    private static final String SHARED_PATH = "app_share";
    private static final String SHARED_PATH_REGISTER = "register";


    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PATH, Context.MODE_APPEND);
    }

    public static SharedPreferences getDefaultSharedPreferences_register(Context context) {
        return context.getSharedPreferences(SHARED_PATH_REGISTER, Context.MODE_APPEND);
    }

    /**
     * 保存用户的注册信息
     *
     * @param baseEntry
     * @param context
     */
    public static void saveRegisterInfo(BaseEntry<Reister> baseEntry, Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", true);
        Reister reister = baseEntry.getData();
        editor.putInt("result", reister.getResult());
        editor.putString("explain", reister.getExplain());
        editor.putString("token", reister.getToken());
        editor.commit();
    }


    /**
     * 保存用户的注册信息
     *
     * @param
     * @param context
     */
    public static void saveRegisterInfo2(Platform qzone, Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", true);
        editor.putString("icon", qzone.getDb().getUserIcon());
        editor.putString("token", qzone.getDb().getToken());
        editor.commit();
    }


    /**
     * 退出登录
     *
     * @param context
     */
    public static void setIsLogin(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", false);
        editor.commit();
    }


    public static void ClearRegisterInfo(Context context) {
        SharedPreferences sharedPreferences1 = context.getSharedPreferences(SHARED_PATH, Activity.MODE_APPEND);
        sharedPreferences1.edit().clear().commit();
    }


    /**
     * 判断用户是否登录
     *
     * @param context
     * @param key
     * @param defv
     */
    public static boolean getIsLogined(Context context, String key, boolean defv) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        return sharedPreferences.getBoolean(key, defv);
    }

    /**
     * 获取用户注册后的信息
     *
     * @param context
     * @param key
     * @return
     */
    public static String getToken(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        return sharedPreferences.getString(key, null);

    }


    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getint(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }


    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defv) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, defv);
    }

}
