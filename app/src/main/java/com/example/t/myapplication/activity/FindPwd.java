package com.example.t.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.t.myapplication.R;
import com.example.t.myapplication.glomble.API;
import com.example.t.myapplication.glomble.Contacts;
import com.example.t.myapplication.model.parser.BaseEntry;
import com.example.t.myapplication.model.parser.Forgetpwd;
import com.example.t.myapplication.model.parser.parser.ParserUser;
import com.example.t.myapplication.util.CommonUtil;

import org.json.JSONObject;

public class FindPwd extends Activity implements View.OnClickListener {
    private ImageView iv_user_back;
    private EditText find_email;
    private Button find_button;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        iv_user_back = (ImageView) findViewById(R.id.iv_user_back);
        iv_user_back.setOnClickListener(this);
        find_email = (EditText) findViewById(R.id.find_email);
        find_button = (Button) findViewById(R.id.find_button);
        find_button.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(FindPwd.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_back:
                finish();
                break;
            case R.id.find_button:
                findpwd();
                break;
        }
    }

    private void findpwd() {
        String email = find_email.getText().toString();
        if (!email.isEmpty()) {
            if (CommonUtil.verifyEmail(email)) {
                String url = API.USER_FORGETPASS + "ver=" + Contacts.VER + "&email=" + email;
                requstLogin(url);
            } else {
                Toast.makeText(FindPwd.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(FindPwd.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void requstLogin(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Forgetpwd> getForgetpwd = ParserUser.getForgetpwd(jsonObject.toString());
                        Forgetpwd forgetpwd = getForgetpwd.getData();
                        Toast.makeText(FindPwd.this, forgetpwd.getExplain(), Toast.LENGTH_SHORT).show();
                        if (forgetpwd.getResult()==0){
                            finish();
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
}
