package com.example.t.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.t.myapplication.R;

public class FlashActivity extends AppCompatActivity {
    private ImageView img_flash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        img_flash = (ImageView) findViewById(R.id.img_flash);
        AlphaAnimation alp = new AlphaAnimation(0, 1);
        alp.setDuration(6000);
        img_flash.setAnimation(alp);
        alp.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(FlashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
