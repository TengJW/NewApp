package com.example.t.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.t.myapplication.R;
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

public class RegisFragment extends Fragment {
    private EditText et_rigis_loginName, et_regis_nickname, et_regis_loginPwd;
    private Button btn_rigis_regist;
    private CheckBox ck_regis;
    private RequestQueue requestQueue;
    private static final String TAG = "RegisFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regis, container, false);
        et_rigis_loginName = (EditText) view.findViewById(R.id.et_rigis_loginName);
        et_regis_nickname = (EditText) view.findViewById(R.id.et_regis_nickname);
        et_regis_loginPwd = (EditText) view.findViewById(R.id.et_regis_loginPwd);
        btn_rigis_regist = (Button) view.findViewById(R.id.btn_rigis_regist);
        ck_regis = (CheckBox) view.findViewById(R.id.ck_regis);
        requestQueue = Volley.newRequestQueue(getContext());
        btn_rigis_regist.setOnClickListener(onClickListener);
        return view;
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email, name, pwd;
            email = et_rigis_loginName.getText().toString();
            name = et_regis_nickname.getText().toString();
            pwd = et_regis_loginPwd.getText().toString();

            if (!ck_regis.isChecked()) {
                Toast.makeText(getActivity(), "请阅读条款", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommonUtil.verifyEmail(email)) {
                Toast.makeText(getActivity(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getActivity(), "用户名不能空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommonUtil.verifyPassword(pwd)) {
                Toast.makeText(getActivity(), "密码格式为6-16位字母与数字组合", Toast.LENGTH_SHORT).show();
                return;
            }
            String url = API.USER_REGISTER + "ver=" + Contacts.VER + "&uid=" + name + "&email=" + email + "&pwd=" + pwd;
            Log.i(TAG, "onClick: " + url);
            requestregister(url);
        }
    };


    public void requestregister(final String url) {
        Log.i(TAG, "onResponse: -----------------"+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Reister> baseRegister = ParserUser.getRegisterInfo(jsonObject.toString());
                        Reister reister=baseRegister.getData();
                        int status=reister.getResult();
                        if (status == -2) {
                            Toast.makeText(getActivity(), reister.getExplain(), Toast.LENGTH_SHORT).show();
                        }
                        if (status == -3) {
                            Toast.makeText(getActivity(), reister.getExplain(), Toast.LENGTH_SHORT).show();
                        }
                        if (status == 0) {
                            startActivity(new Intent(getActivity(), UserActivity.class));
                            ShareUtil.saveRegisterInfo(baseRegister, getContext());
                            ((HomeActivity) getActivity()).changeFragmentStatus();


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
