package com.example.t.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.t.myapplication.R;
import com.example.t.myapplication.adapter.LoginAdapter;
import com.example.t.myapplication.glomble.API;
import com.example.t.myapplication.glomble.Contacts;
import com.example.t.myapplication.model.parser.BaseEntry;
import com.example.t.myapplication.model.parser.User;
import com.example.t.myapplication.model.parser.parser.ParserUser;
import com.example.t.myapplication.util.CommonUtil;
import com.example.t.myapplication.util.ImageLoader;
import com.example.t.myapplication.util.ShareUtil;

import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class UserActivity extends Activity {
    private RequestQueue requestQueue;
    private TextView name, integral, account_phone_icon, num;
    private ListView ll;
    private LoginAdapter adapter;
    private static final String TAG = "UserActivity";
    private ImageView iv_user_back, iv_touxiang;
    private Button bt_exit;
    private ImageLoader imageLoader;
    String icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        requestQueue = Volley.newRequestQueue(this);
        name = (TextView) findViewById(R.id.name);
        iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);

        iv_user_back = (ImageView) findViewById(R.id.iv_user_back);
        iv_user_back.setOnClickListener(onClickListener);
        integral = (TextView) findViewById(R.id.integral);
        account_phone_icon = (TextView) findViewById(R.id.account_phone_icon);
        num = (TextView) findViewById(R.id.num);
        ll = (ListView) findViewById(R.id.ll);
        bt_exit = (Button) findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(onClickListener);

        Intent intent = getIntent();
        int num = intent.getIntExtra("secuss", 1);

        ShareSDK.initSDK(UserActivity.this);
        Platform qzone = ShareSDK.getPlatform(this, QQ.NAME);
        if (qzone.isValid()) {
//                String accessToken = qzone.getDb().getToken(); // 获取授权token
//                String openId = qzone.getDb().getUserId(); // 获取用户在此平台的ID
//                String nickname = qzone.getDb().get("nickname"); // 获取用户昵称
            icon = qzone.getDb().getUserIcon();
            name.setText(ShareUtil.getString(UserActivity.this, "name"));

            Log.i(TAG, "onCreate: ---------------------" + icon);
            try {
                imageLoader = new ImageLoader(this);
                imageLoader.display(icon, iv_touxiang);
                iv_touxiang.setBackgroundResource(R.color.transparent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            adapter = new LoginAdapter(this);

            ll.setAdapter(adapter);
            String token = ShareUtil.getToken(this, "token");
            String url = API.USER_CENTER_DATA + "ver=" + Contacts.VER + "&imei=" + CommonUtil.getIMEI(this) + "&token=" + token;
            Log.i(TAG, "onCreate: -----------------" + url);
            requestUserData(url);
        }
    }


    private void requestUserData(final String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<User> userBaseEntry = ParserUser.getLoginSuccInfo(jsonObject.toString());
                        int result = userBaseEntry.getStatus();
                        Log.i(TAG, "onResponse: ======================" + result);
                        if (result != 0) {
                            Toast.makeText(UserActivity.this, "用户数据请求错误！", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = userBaseEntry.getData();
                            name.setText(user.getUid());
                            integral.setText("用户积分：" + user.getIntegration());
                            num.setText(user.getCommum() + "");
                            adapter.appendDate(user.getLoginlog(), true);
                            adapter.updateAdapter();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );
        requestQueue.add(jsonObjectRequest);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_user_back:
                    Intent intent = new Intent(UserActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_exit:
                    ShareSDK.initSDK(UserActivity.this);
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    if (qq.isValid()){
                        qq.removeAccount();
                    }
                    if (!(icon ==null) && !(imageLoader ==null)){
                        imageLoader.clearBitMap(icon);
                    }
                    ShareUtil.setIsLogin(UserActivity.this);//登录状态改为false
                    ShareUtil.ClearRegisterInfo(UserActivity.this);
                    ShareUtil.putString(UserActivity.this, "name", null);
                    Intent intent1 = new Intent(UserActivity.this, HomeActivity.class);
                    intent1.putExtra("fragment", 3);
                    startActivity(intent1);
                    finish();
                    break;

            }

        }
    };
}

