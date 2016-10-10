package com.example.t.myapplication.glomble;

/**
 * Created by T on 2016/9/5.
 */
//http://118.244.212.82:9092/newsClient/news_sort?ver=1&imei=864394101849794
//http://118.244.212.82:9092/newsClient/news_list?ver=1dsf&subid=1&dir=1&nid=0&stamp=20160828&cnt=20
public class API {
    public static final String ServerIP = "http://118.244.212.82:9092/newsClient/";

    /**
     * 首页
     */
    public static final String NEWS_LIST = ServerIP + "news_list?";
    /**
     * 标题页
     */
    public static final String NEWS_SORT = ServerIP + "news_sort?";
    /**
     * 新闻评论
     */
//    http://118.244.212.82:9092/newsClient/cmt_list?&ver=1&nid=1&type=1&stamp=1&stamp=20160808&cid=1&dir=1&cnt=20
    public static final String NEWS_COMMENT = ServerIP + "cmt_list?";
    /**
     * 登录页接口
     */
    public static final String USER_LOGIN = ServerIP + "user_login?";

    /**
     * 用户中心数据
     */
    public static final String USER_REGISTER = ServerIP + "user_register?";


    public static final String USER_CENTER_DATA = ServerIP + "user_home?";

    /**
     * 忘记密码
     */
    public static final String USER_FORGETPASS = ServerIP + "user_forgetpass?";


    /**
     * 新闻评论数
     */
    public static final String USER_CENTER_NUM = ServerIP + "cmt_num?";


    /**
     * 用户评论
     */
    public static final String USER_COMMENT = ServerIP + "cmt_commit?";





}
