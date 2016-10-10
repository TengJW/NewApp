package com.example.t.myapplication.model.parser.parser;

import com.example.t.myapplication.model.parser.BaseEntry;
import com.example.t.myapplication.model.parser.Forgetpwd;
import com.example.t.myapplication.model.parser.Reister;
import com.example.t.myapplication.model.parser.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by T on 2016/9/8.
 */
public class ParserUser {

    /**
     * 解析用户注册信息
     *
     * @param json
     * @return
     */
    public static BaseEntry<Reister> getRegisterInfo(String json) {
        Gson gson = new Gson();
        BaseEntry<Reister> registerBaseEntry = gson.fromJson(json, new TypeToken<BaseEntry<Reister>>() {
        }.getType());
        return registerBaseEntry;
    }

    /**
     * 解析登录成功的用户数据
     *
     * @param json
     * @return
     */
    public static BaseEntry<User> getLoginSuccInfo(String json) {
        Gson gson = new Gson();
        BaseEntry<User> registerBaseEntry = gson.fromJson(json, new TypeToken<BaseEntry<User>>() {
        }.getType());
        return registerBaseEntry;
    }

    /**
     * 解析登录成功的用户数据
     *
     * @param json
     * @return
     */
    public static BaseEntry<Forgetpwd> getForgetpwd(String json) {
        Gson gson = new Gson();
        BaseEntry<Forgetpwd> registerBaseEntry = gson.fromJson(json, new TypeToken<BaseEntry<Forgetpwd>>() {
        }.getType());
        return registerBaseEntry;
    }

//
//    /**
//     * 解析更新信息
//     *
//     * @param json
//     * @return
//     */
//    public static  BaseEntry<List<TestUpdata>> getUpdata(String json) {
//        Gson gson = new Gson();
//        BaseEntry<List<TestUpdata>> registerBaseEntry = gson.fromJson(json, new TypeToken<BaseEntry<TestUpdata>>() {
//        }.getType());
//        return registerBaseEntry;
//    }


}
