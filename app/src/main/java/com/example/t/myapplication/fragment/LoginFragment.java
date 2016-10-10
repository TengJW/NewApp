package com.example.t.myapplication.fragment;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.t.myapplication.R;
import com.example.t.myapplication.activity.FindPwd;
import com.example.t.myapplication.activity.HomeActivity;
import com.example.t.myapplication.activity.UserActivity;
import com.example.t.myapplication.glomble.API;
import com.example.t.myapplication.glomble.Contacts;
import com.example.t.myapplication.model.parser.BaseEntry;
import com.example.t.myapplication.model.parser.Reister;
import com.example.t.myapplication.model.parser.parser.ParserUser;
import com.example.t.myapplication.util.CommonUtil;
import com.example.t.myapplication.util.ShareUtil;

import org.json.JSONObject;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button btn_login_regist, btn_login_login;
    private EditText et_loginName, et_loginPwd;
    private RequestQueue requestQueue;
    private static final String TAG = "LoginFragment";
    private Button bt_losepwd;
    private PopupWindow popupWindow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login_regist = (Button) view.findViewById(R.id.btn_login_regist);
        btn_login_login = (Button) view.findViewById(R.id.btn_login_login);
        et_loginName = (EditText) view.findViewById(R.id.et_loginName);
        et_loginPwd = (EditText) view.findViewById(R.id.et_loginPwd);
        btn_login_regist.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(getActivity());
        bt_losepwd = (Button) view.findViewById(R.id.bt_losepwd);
        bt_losepwd.setOnClickListener(this);
        showPopupWindow();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_regist:
                ((HomeActivity) getActivity()).showRegisFragment();
                break;
            case R.id.btn_login_login:
                String name = et_loginName.getText().toString();
                String pwd = et_loginPwd.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "用户名不能空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!CommonUtil.verifyPassword(pwd)) {
                    Toast.makeText(getActivity(), "密码格式为6-16位字母与数字组合", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = API.USER_LOGIN + "ver=" + Contacts.VER + "&uid=" + name + "&pwd=" + pwd + "&device=" + "0";
                requstLogin(url, name);
                break;
            case R.id.bt_losepwd:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else if (popupWindow != null) {
                    popupWindow.showAsDropDown(bt_losepwd, 0, 2);
                }
                break;
        }
    }

    private void showPopupWindow() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.login_popwin_item, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_losepwd);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(getActivity(), FindPwd.class);
                startActivity(intent);

            }
        });
    }


    public void requstLogin(String url, final String name) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Reister> baseLogin = ParserUser.getRegisterInfo(jsonObject.toString());
                        int result = baseLogin.getStatus();
                        if (result == 0) {
                            ShareUtil.putString(getActivity(), "name", name);//保存用户名在本地
                            ShareUtil.saveRegisterInfo(baseLogin, getActivity());
                            startActivity(new Intent(getActivity(), UserActivity.class));
                        }
                        if (result == -1) {
                            Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
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
