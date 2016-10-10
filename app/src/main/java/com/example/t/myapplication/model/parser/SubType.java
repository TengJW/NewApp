package com.example.t.myapplication.model.parser;

import java.io.Serializable;

/**
 * Created by T on 2016/9/6.
 */
public class SubType  implements Serializable{
    private String subgroup;
    private int subid;

    @Override
    public String toString() {
        return "SubType{" +
                "subid=" + subid +
                ", subgroup='" + subgroup + '\'' +
                '}';
    }

    public SubType(String subgroup, int subid) {
        this.subgroup = subgroup;
        this.subid = subid;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }
}
