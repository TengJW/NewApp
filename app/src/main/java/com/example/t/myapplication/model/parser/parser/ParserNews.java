package com.example.t.myapplication.model.parser.parser;

import android.util.Log;

import com.example.t.myapplication.model.parser.BaseEntry;
import com.example.t.myapplication.model.parser.NewsComment;
import com.example.t.myapplication.model.parser.NewsList;
import com.example.t.myapplication.model.parser.NewsSendComment;
import com.example.t.myapplication.model.parser.NewsType;
import com.example.t.myapplication.model.parser.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by T on 2016/9/6.
 */
public class ParserNews {
    private static final String TAG = "ParserNews";


    public static List<SubType> getNewsType(String json) {
        Gson gson = new Gson();

        BaseEntry<List<NewsType>> list = gson.fromJson(json, new TypeToken<BaseEntry<List<NewsType>
                >>() {
        }.getType());
        return list.getData().get(0).getSubgrp();
    }


    public static List<NewsList> getNewsList(String json) {
        Gson gson = new Gson();
        Log.i(TAG, "getNewsList: ================" + json);
        BaseEntry<List<NewsList>> list = gson.fromJson(json, new TypeToken<BaseEntry<List<NewsList>
                >>() {
        }.getType());
        return list.getData();
    }


    public static int getNewsCommentNum(String json) {
//        Gson gson = new Gson();
//        BaseEntry<NewsCommentNum> list = gson.fromJson(json, new TypeToken<BaseEntry<NewsCommentNum
//                >>() {
//        }.getType());
        int data=0;
        try {
            JSONObject obj = new JSONObject(json);
            int status = obj.getInt("status");
            String msg = obj.getString("message");
             data = obj.getInt("data");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取新闻评论
     * @param json
     * @return
     */
    public static List<NewsComment> getNewsComment(String json) {
        Gson gson = new Gson();
        Log.i(TAG, "getNewsList: ================" + json);
        BaseEntry<List<NewsComment>> list = gson.fromJson(json, new TypeToken<BaseEntry<List<NewsComment>
                >>() {
        }.getType());
        return list.getData();
    }



    /**
     * 发布新闻评论
     * @param json
     * @return
     */
    public static  BaseEntry<NewsSendComment> getComment(String json) {
        Gson gson = new Gson();
        Log.i(TAG, "getNewsList: ================" + json);
        BaseEntry<NewsSendComment> list = gson.fromJson(json, new TypeToken<BaseEntry<NewsSendComment
                >>() {
        }.getType());
        return list;
    }























//    public  static  List<NewsListAdapter> getlistJson(String json) {
//        List<NewsListAdapter> list = new ArrayList<NewsListAdapter>();
//        try {
//            JSONObject obj = new JSONObject(json);
//            int status = obj.getInt("status");
//            String msg = obj.getString("message");
//            if (status == 0) {
//                JSONArray arr = obj.getJSONArray("data");
//                for (int i = 0; i < arr.length(); i++) {
//                    JSONObject jsonObject = arr.getJSONObject(i);
//                    String summary = jsonObject.getString("summary");
//                    String icon = jsonObject.getString("icon");
//                    String satmp = jsonObject.getString("stamp");
//                    String title = jsonObject.getString("title");
//                    int nid = jsonObject.getInt("nid");
//                    String link = jsonObject.getString("link");
//                    int type = jsonObject.getInt("type");
//                    NewsListAdapter news = new NewsListAdapter(summary, icon, satmp, title, nid, link, type);
//                    list.add(news);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
