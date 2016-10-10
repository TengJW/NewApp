package com.example.t.myapplication.model.parser;

/**
 * Created by T on 2016/9/12.
 */
public class NewsComment {
    private String uid;
    private String content;
    private String stamp;
    private int cid;
    private String portrait;

    @Override
    public String toString() {
        return "NewsComment{" +
                "uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", stamp='" + stamp + '\'' +
                ", cid=" + cid +
                ", portrait='" + portrait + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public NewsComment(String uid, String content, String stamp, int cid, String portrait) {

        this.uid = uid;
        this.content = content;
        this.stamp = stamp;
        this.cid = cid;
        this.portrait = portrait;
    }
}
