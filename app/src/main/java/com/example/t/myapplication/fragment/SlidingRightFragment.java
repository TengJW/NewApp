package com.example.t.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.myapplication.R;
import com.example.t.myapplication.activity.HomeActivity;
import com.example.t.myapplication.activity.UpdataActivity;
import com.example.t.myapplication.activity.UserActivity;
import com.example.t.myapplication.util.ImageLoader;
import com.example.t.myapplication.util.ShareUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

public class SlidingRightFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_loginImageView, fun_qq, iv_loginedImageView, fun_weibo;
    private TextView tv_loginTextView, tv_loginedName, tv_updata;
    private RelativeLayout layout_login, layout_logined;
    private static final String TAG = "SlidingRightFragment";
    private boolean isLogin;
    private LinearLayout ll_updata, ll_updata_all;
    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;
    private Platform qq;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sliding_right, container, false);
        iv_loginImageView = (ImageView) view.findViewById(R.id.iv_loginImageView);
        tv_loginTextView = (TextView) view.findViewById(R.id.tv_loginTextView);
        layout_login = (RelativeLayout) view.findViewById(R.id.layout_login);
        layout_logined = (RelativeLayout) view.findViewById(R.id.layout_logined);
        iv_loginedImageView = (ImageView) view.findViewById(R.id.iv_loginedImageView);

        fun_weibo = (ImageView) view.findViewById(R.id.fun_weibo);
        fun_weibo.setOnClickListener(this);


        fun_qq = (ImageView) view.findViewById(R.id.fun_qq);
        fun_qq.setOnClickListener(this);

        ll_updata = (LinearLayout) view.findViewById(R.id.ll_updata);
        ll_updata_all = (LinearLayout) view.findViewById(R.id.ll_updata_all);
        ll_updata_all.setOnClickListener(this);
        ll_updata.setOnClickListener(this);
        tv_loginedName = (TextView) view.findViewById(R.id.tv_loginedName);
        tv_updata = (TextView) view.findViewById(R.id.tv_updata);
        tv_updata.setOnClickListener(this);
        layout_logined.setOnClickListener(this);
        iv_loginImageView.setOnClickListener(this);
        tv_loginTextView.setOnClickListener(this);
        isLogin = ShareUtil.getIsLogined(getActivity(), "isLogin", false);

        changeView();
        return view;
    }

    public void changeView() {
        isLogin = ShareUtil.getIsLogined(getActivity(), "isLogin", false);
        String username = ShareUtil.getString(getActivity(), "name");
        if (isLogin) {
            tv_loginedName.setText(username);
            String icon=ShareUtil.getString(getActivity(), "icon");
            if (!(icon ==null)){

                new ImageLoader(getActivity()).display(icon, iv_loginedImageView);
            }
            layout_logined.setVisibility(View.VISIBLE);
            layout_login.setVisibility(View.GONE);
        } else {
            layout_login.setVisibility(View.VISIBLE);
            layout_logined.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_loginImageView || v.getId() == R.id.tv_loginTextView) {
            ((HomeActivity) getActivity()).showLoginFragment();
        }

        if (v.getId() == R.id.layout_logined) {
            startActivity(new Intent(getActivity(), UserActivity.class));
        }
        if (v.getId() == R.id.tv_updata || v.getId() == R.id.ll_updata) {
            Log.i(TAG, "onClick: -----------------------------------");
            startActivity(new Intent(getActivity(), UpdataActivity.class));
        }

        if (v.getId() == R.id.fun_qq) {
            ShareSDK.initSDK(getActivity());
            qq = ShareSDK.getPlatform(QQ.NAME);
            if (qq.isValid()) {
                qq.removeAccount();
            }
            qq.setPlatformActionListener(new PlatformActionListener() {

                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    if (i == Platform.ACTION_USER_INFOR) {
                        Message msg = new Message();
                        msg.what = MSG_AUTH_COMPLETE;
                        msg.arg2 = i;
                        msg.obj = new Object[]{platform.getName(), hashMap};
                        handler.sendMessage(msg);
                    }

                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    if (i == Platform.ACTION_USER_INFOR) {
                        Message msg = new Message();
                        msg.what = MSG_AUTH_ERROR;
                        msg.arg2 = i;
                        msg.obj = throwable;
                        handler.sendMessage(msg);
                    }
                    throwable.printStackTrace();

                }

                @Override
                public void onCancel(Platform platform, int action) {
                    if (action == Platform.ACTION_USER_INFOR) {
                        Message msg = new Message();
                        msg.what = MSG_AUTH_CANCEL;
                        msg.arg2 = action;
                        msg.obj = platform;
                        handler.sendMessage(msg);
                    }

                }
            });
            qq.authorize();//单独授权
            qq.showUser(null);//授权并获取用户信息
            //authorize与showUser单独调用一个即可
            //移除授权
            //qq.removeAccount(true);


        }
        if (v.getId() == R.id.fun_weibo) {
            showShare();
        }

    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUTH_CANCEL: {
                    // 取消
                    Toast.makeText(getActivity(), "canceled", Toast.LENGTH_SHORT).show();
                }
                break;
                case MSG_AUTH_ERROR: {
                    // 失败
                    Throwable t = (Throwable) msg.obj;
                    String text = "caught error: " + t.getMessage();
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
                break;
                case MSG_AUTH_COMPLETE: {
                    // 成功
                    ShareUtil shareUtil = new ShareUtil();
                    shareUtil.saveRegisterInfo2(qq, getActivity());
                    ShareUtil.putString(getActivity(), "name", qq.getDb().getUserName());//保存用户名在本地
                    ShareUtil.putString(getActivity(), "icon", qq.getDb().getUserIcon());
                    changeView();
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    intent.putExtra("secuss", 3);
                    startActivity(intent);

                }
                break;
            }
        }
    };


    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(getActivity());
    }
}
