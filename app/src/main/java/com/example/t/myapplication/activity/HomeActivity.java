package com.example.t.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.t.myapplication.R;
import com.example.t.myapplication.adapter.ListViewAdapter;
import com.example.t.myapplication.base.BaseActivity;
import com.example.t.myapplication.fragment.CommentFragment;
import com.example.t.myapplication.fragment.FavoriteFragment;
import com.example.t.myapplication.fragment.HomeFragment;
import com.example.t.myapplication.fragment.LocalFragment;
import com.example.t.myapplication.fragment.LoginFragment;
import com.example.t.myapplication.fragment.PhotoFragment;
import com.example.t.myapplication.fragment.RegisFragment;
import com.example.t.myapplication.fragment.SlidingMenuFragment;
import com.example.t.myapplication.fragment.SlidingRightFragment;
import com.igexin.sdk.PushManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private HomeFragment fragment;
    private SlidingMenuFragment slidingfragment;
    private SlidingRightFragment slidingRightFragment;
    private LoginFragment loginFragment;
    private RegisFragment RegisFragment;
    private FavoriteFragment favoritefragment;
    private PhotoFragment photoFragment;
    private LocalFragment localFragment;
    private CommentFragment commentFragment;
    private RequestQueue requestQueue;
    private ListView home_lv;
    private ListViewAdapter adapter;
    private static final String TAG = "HomeActivity";
    private ImageView iv_home_left, iv_home_right;
    private SlidingMenu menu;
    private TextView tv_home_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        home_lv = (ListView) findViewById(R.id.home_lv);
        tv_home_title = (TextView) findViewById(R.id.tv_home_title);
        iv_home_left = (ImageView) findViewById(R.id.iv_home_left);
        iv_home_right = (ImageView) findViewById(R.id.iv_home_right);
        iv_home_left.setOnClickListener(this);
        iv_home_right.setOnClickListener(this);
        PushManager.getInstance().initialize(this.getApplicationContext());

        Intent intent = getIntent();
        int num = intent.getIntExtra("fragment", 0);
        switch (num) {
            case 3:
                initSlidingMenu();
                showLoginFragment();
                break;
            default:
                loadHomeFragment();
                initSlidingMenu();
                break;
        }

        // configure the SlidingMenu

//        sendQuesone();
//        requestQueue = Volley.newRequestQueue();
//        sendRequestData("http://118.244.212.82:9092/newsClient/news_list?ver=1dsf&subid=1&dir=1&nid=0&stamp=20160828&cnt=20");
    }


    private void initSlidingMenu() {
        slidingfragment = new SlidingMenuFragment();
        slidingRightFragment = new SlidingRightFragment();
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置可滑动区域为全屏
        menu.setBehindOffset(R.dimen.slidingmenu_offset);
        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.bd);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.leftmenu);

        menu.setSecondaryMenu(R.layout.rightmenu);

        getSupportFragmentManager().beginTransaction().add(R.id.slingMenu_contianer, slidingfragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_right_contianer, slidingRightFragment).commit();

    }

    private void loadHomeFragment() {
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contianer, fragment).commit();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_left:
                if (menu != null && menu.isMenuShowing()) {
                    menu.showContent();
                } else if (menu != null) {
                    menu.showMenu();
                }
                break;

            case R.id.iv_home_right:
                if (menu != null && menu.isMenuShowing()) {
                    menu.showContent();
                } else if (menu != null) {
                    menu.showSecondaryMenu();
                }
                break;
        }
    }


    public void changeFragmentStatus() {
        ((SlidingRightFragment) slidingRightFragment).changeView();
    }

    /**
     * 设置标题栏显示内容
     *
     * @param name
     */
    public void setTitle(String name) {
        tv_home_title.setText(name);
    }


    public void showHomeFragment() {
        setTitle("资讯");
        menu.showContent();
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, fragment).commit();
    }

    public void showFrvoriteFragment() {
        setTitle("我的收藏");
        menu.showContent();
        if (favoritefragment == null) {
            favoritefragment = new FavoriteFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, favoritefragment).commit();
    }

    public void showphotoFragment() {
        setTitle("图片");
        menu.showContent();
        if (photoFragment == null) {
            photoFragment = new PhotoFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, photoFragment).commit();
    }

    public void showLocalFragment() {
        setTitle("本地");
        menu.showContent();
        Intent intent = new Intent(HomeActivity.this, LocalActivity.class);
        startActivity(intent);
        finish();
//        if (localFragment == null) {
//            localFragment = new LocalFragment();
//        }
//        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, localFragment).commit();
    }

    public void showcommentFragment() {
        setTitle("我的跟帖");
        menu.showContent();
        if (commentFragment == null) {
            commentFragment = new CommentFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, commentFragment).commit();
    }


    /**
     * 登录界面
     */
    public void showLoginFragment() {
        setTitle("用户登陆");
        if (loginFragment == null)
            loginFragment = new LoginFragment();
        menu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, loginFragment).commit();
    }

    /**
     * 用户注册页面
     */
    public void showRegisFragment() {
        setTitle("用户注册");
        if (RegisFragment == null)
            RegisFragment = new RegisFragment();
        menu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, RegisFragment).commit();
    }
}
