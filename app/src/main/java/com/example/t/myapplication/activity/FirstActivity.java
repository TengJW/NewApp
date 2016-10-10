package com.example.t.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.t.myapplication.R;
import com.example.t.myapplication.adapter.first_pager_adapter;

import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends Activity {
    private ViewPager viewpager;
    private first_pager_adapter first_pager_adapter;
    private ImageView[] images = new ImageView[4];
    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initUI();
        init();
        initData();

    }

    private void initUI() {
        images[0] = (ImageView) findViewById(R.id.imageviewtwofirst);
        images[1] = (ImageView) findViewById(R.id.imageviewtwofirst2);
        images[2] = (ImageView) findViewById(R.id.imageviewtwofirst3);
        images[3] = (ImageView) findViewById(R.id.imageviewtwofirst4);
        viewpager = (ViewPager) findViewById(R.id.viewpagertwofirst);
        images[1].setAlpha(0.5f);
        images[2].setAlpha(0.5f);
        images[3].setAlpha(0.5f);
    }

    private void init() {
        first_pager_adapter = new first_pager_adapter(this);
        viewpager.setAdapter(first_pager_adapter);
        viewpager.setOnPageChangeListener(listener);
    }

    private void initData() {
        ImageView imageView = null;
        imageView = (ImageView) getLayoutInflater().inflate(
                R.layout.layout_first_item, null);
        imageView.setBackgroundResource(R.drawable.bd);
        first_pager_adapter.addToAdpterView(imageView);
        imageView = (ImageView) getLayoutInflater().inflate(
                R.layout.layout_first_item, null);
        imageView.setBackgroundResource(R.drawable.small);
        first_pager_adapter.addToAdpterView(imageView);
        imageView = (ImageView) getLayoutInflater().inflate(
                R.layout.layout_first_item, null);
        imageView.setBackgroundResource(R.drawable.wy);
        first_pager_adapter.addToAdpterView(imageView);
        imageView = (ImageView) getLayoutInflater().inflate(
                R.layout.layout_first_item, null);
        imageView.setBackgroundResource(R.drawable.welcome);
        first_pager_adapter.addToAdpterView(imageView);
        first_pager_adapter.notifyDataSetChanged();// 是将Adapter进行一个刷新
    }


    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            int num = 0;
            for (int i = 0; i < images.length; i++) {
                images[i].setAlpha(0.5f);
                num = i;

            }
            images[arg0].setAlpha(1f);
            if (num == arg0) {
                setAngleWidthAnim();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    public void setAngleWidthAnim() {

        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                switch (state) {
                    case 0:
                        state = 1;
                        break;
                    case 1:
                        timer.cancel();
                        Intent intent = new Intent(FirstActivity.this, FlashActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

}
