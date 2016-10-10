package com.example.t.myapplication.base;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.t.myapplication.R;


/**
 * Created by T on 2016/9/5.
 */
public class BaseActivity extends FragmentActivity {
    private static final String TAG = "BaseActivity";
    private Toast toast;
    public static int screenW, screeH;
    public Dialog dialog;//界面弹出框

    /**
     * 生命周期调试
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screeH = getWindowManager().getDefaultDisplay().getWidth();
        screenW = getWindowManager().getDefaultDisplay().getHeight();
    }


    public void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    public void openActivity(Class<?> aClass) {
        openActivitu(aClass, null, null);

    }

    public void openActivity(Class<?> aClass, Bundle bundle) {
        openActivitu(aClass, bundle, null);

    }

    public void openActivitu(Class<?> aClass, Bundle bundle, Uri uri) {
        Intent intent = new Intent(this, aClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
        //增加动画
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_bottom_out);
    }




}
