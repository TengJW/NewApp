package com.example.t.myapplication.model.parser;

import java.io.Serializable;

/**
 * Created by T on 2016/9/7.
 */
public class NewsList<T>  implements Serializable {
    private String summary;
    private String icon;
    private String stamp;
    private String title;
    private int nid;
    private String link;
    private int type;

    @Override
    public String toString() {
        return "NewsListAdapter{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", stamp='" + stamp + '\'' +
                ", title='" + title + '\'' +
                ", nid=" + nid +
                ", link='" + link + '\'' +
                ", type=" + type +
                '}';
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public NewsList(String summary, String icon, String stamp, String title, int nid, String link, int type) {

        this.summary = summary;
        this.icon = icon;
        this.stamp = stamp;
        this.title = title;
        this.nid = nid;
        this.link = link;
        this.type = type;
    }
}
