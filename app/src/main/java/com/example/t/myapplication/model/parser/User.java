package com.example.t.myapplication.model.parser;

import java.io.Serializable;
import java.util.List;

/**
 * Created by T on 2016/9/9.
 */
public class User implements Serializable{
    private String uid;
    private String portrait;
    private int integration;
    private int commum;
    private List<Loginlog> loginlog;

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", portrait='" + portrait + '\'' +
                ", integration=" + integration +
                ", commum=" + commum +
                ", loginlog=" + loginlog +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public int getCommum() {
        return commum;
    }

    public void setCommum(int commum) {
        this.commum = commum;
    }

    public List<Loginlog> getLoginlog() {
        return loginlog;
    }

    public void setLoginlog(List<Loginlog> loginlog) {
        this.loginlog = loginlog;
    }

    public User(String uid, String portrait, int integration, int commum, List<Loginlog> loginlog) {

        this.uid = uid;
        this.portrait = portrait;
        this.integration = integration;
        this.commum = commum;
        this.loginlog = loginlog;
    }
}
