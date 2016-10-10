package com.example.t.myapplication.util.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.t.myapplication.model.parser.NewsList;
import com.example.t.myapplication.model.parser.SubType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 2016/9/12.
 */
public class DBTools {
    private Context context;
    private SQLiteDatabase sd;
    private SQLiteOpenHelper helper;
    private DBManager dbManager;

    public DBTools(Context context) {
        this.context = context;
        dbManager = new DBManager(context);
    }

    /********
     * 收藏
     *************/
    public boolean savaLocalFavorite(NewsList newsList) {
        sd = dbManager.getReadableDatabase();
        String sql = "select nid from " + DBManager.NEWSFAVORITE_NAME + " where nid = ?";
        Cursor c = sd.rawQuery(sql, new String[]{newsList.getNid() + ""});
        if (c.moveToNext() && c.getCount() > 0) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", newsList.getType());
        contentValues.put("nid", newsList.getNid());
        contentValues.put("icon", newsList.getIcon());
        contentValues.put("title", newsList.getTitle());
        contentValues.put("stamp", newsList.getStamp());
        contentValues.put("link", newsList.getLink());
        contentValues.put("summary", newsList.getSummary());

        sd.insert(DBManager.NEWSFAVORITE_NAME, null, contentValues);
        return true;
    }

    public List<NewsList> getLoaclFavorite() {
        List<NewsList> newsLists = new ArrayList<NewsList>();
        sd = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWSFAVORITE_NAME;
        Cursor c = sd.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                int type = c.getInt(c.getColumnIndex("type"));
                int nid = c.getInt(c.getColumnIndex("nid"));
                String icon = c.getString(c.getColumnIndex("icon"));
                String title = c.getString(c.getColumnIndex("title"));
                String stamp = c.getString(c.getColumnIndex("stamp"));
                String link = c.getString(c.getColumnIndex("link"));
                String summary = c.getString(c.getColumnIndex("summary"));
                NewsList newsList = new NewsList(summary, icon, stamp, title, nid, link, type);
                newsLists.add(newsList);
            } while (c.moveToNext());
            c.close();
            sd.close();
        }
        return newsLists;
    }


    /**
     * 获取图片的url
     *
     * @return
     */
    public List<NewsList> getIconList() {
        List<NewsList> iconList = new ArrayList<NewsList>();
        sd = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWS_NAME;
        Cursor c = sd.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                String icon = c.getString(c.getColumnIndex("icon"));
                NewsList List = new NewsList(null, icon, null, null, 0, null, 0);
                iconList.add(List);
            } while (c.moveToNext());
            c.close();
            sd.close();
        }
        return iconList;
    }


    /********
     * 新闻首页
     *************/

    public boolean savaLocalNews(NewsList newsList) {
        sd = dbManager.getReadableDatabase();
        String sql = "select nid from " + DBManager.NEWS_NAME + " where nid = ?";
        Cursor c = sd.rawQuery(sql, new String[]{newsList.getNid() + ""});
        if (c.moveToNext() && c.getCount() > 0) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", newsList.getType());
        contentValues.put("nid", newsList.getNid());
        contentValues.put("icon", newsList.getIcon());
        contentValues.put("title", newsList.getTitle());
        contentValues.put("stamp", newsList.getStamp());
        contentValues.put("link", newsList.getLink());
        contentValues.put("summary", newsList.getSummary());

        sd.insert(DBManager.NEWS_NAME, null, contentValues);
        return true;
    }

    public List<NewsList> getLoaclNews() {
        List<NewsList> newsLists = new ArrayList<NewsList>();
        sd = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWS_NAME;
        Cursor c = sd.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                int type = c.getInt(c.getColumnIndex("type"));
                int nid = c.getInt(c.getColumnIndex("nid"));
                String icon = c.getString(c.getColumnIndex("icon"));
                String title = c.getString(c.getColumnIndex("title"));
                String stamp = c.getString(c.getColumnIndex("stamp"));
                String link = c.getString(c.getColumnIndex("link"));
                String summary = c.getString(c.getColumnIndex("summary"));
                NewsList newsList = new NewsList(summary, icon, stamp, title, nid, link, type);
                newsLists.add(newsList);
            } while (c.moveToNext());
            c.close();
            sd.close();
        }
        return newsLists;
    }

    /********
     * 新闻标题
     *************/


    public boolean savaLocalType(SubType newsList) {
        sd = dbManager.getReadableDatabase();
        String sql = "select subid from " + DBManager.NEWSTYPE_NAME + " where subid = ?";
        Cursor c = sd.rawQuery(sql, new String[]{newsList.getSubid() + ""});
        if (c.moveToNext() && c.getCount() > 0) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("subid", newsList.getSubid());
        contentValues.put("subgroup", newsList.getSubgroup());
        sd.insert(DBManager.NEWSTYPE_NAME, null, contentValues);
        return true;
    }

    public List<SubType> getLoaclType() {
        List<SubType> newsLists = new ArrayList<SubType>();
        sd = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWSTYPE_NAME;
        Cursor c = sd.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                int subid = c.getInt(c.getColumnIndex("subid"));
                String subgroup = c.getString(c.getColumnIndex("subgroup"));
                SubType subType = new SubType(subgroup, subid);
                newsLists.add(subType);
            } while (c.moveToNext());
            c.close();
            sd.close();
        }
        return newsLists;
    }


}

