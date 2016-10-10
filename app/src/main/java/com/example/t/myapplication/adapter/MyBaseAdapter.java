package com.example.t.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Apater基类
 * Created by T on 2016/9/6.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> mylist = new ArrayList<T>();


    public MyBaseAdapter(Context context) {
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);

    }



    /**
     * 获得adapter的所有数据
     *
     * @return
     */
    public List<T> getAdapterData() {
        return mylist;
    }

    /**
     * 封装一个添加一条数据的方法
     *
     * @param t          添加的数据
     * @param isClearOld 是否清理旧数据
     */
    public void appendDate(T t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            mylist.clear();
        mylist.add(t);
    }

    /**
     * 封装一个添加多条数据的方法
     *
     * @param t          添加的数据
     * @param isClearOld 是否清理旧数据
     */
    public void appendDate(List<T> t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            mylist.clear();
        mylist.addAll(t);
    }

    /**
     * 在开头添加一条数据的方法
     *
     * @param t          添加的数据
     * @param isClearOld 是否清理旧数据
     */
    public void appendDateTop(T t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            mylist.clear();
        mylist.add(0, t);
    }

    /**
     * 在开头添加多条数据的方法
     *
     * @param t          添加的数据
     * @param isClearOld 是否清理旧数据
     */
    public void appendDateTop(List<T> t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            mylist.clear();
        mylist.addAll(0, t);
    }

    public void isClear() {
        mylist.clear();
    }

    public void updateAdapter() {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mylist != null)
            return mylist.size();
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (mylist == null || mylist.size() < 0)
            return null;
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getMyView(position, convertView, parent);
    }

    public abstract View getMyView(int position, View convertView, ViewGroup parent);
}
