package com.example.t.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t.myapplication.R;
import com.example.t.myapplication.model.parser.NewsComment;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by T on 2016/9/12.
 */
public class NewsCommentAdapter<T> extends MyBaseAdapter<NewsComment> {

    public NewsCommentAdapter(Context context) {
        super(context);
    }


    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_show_comment_item, null);
            vh = new ViewHolder();
            vh.tv_comment_username = (TextView) convertView.findViewById(R.id.tv_comment_username);
            vh.tv_comment_stamp = (TextView) convertView.findViewById(R.id.tv_comment_stamp);
            vh.tv_userscomment = (TextView) convertView.findViewById(R.id.tv_userscomment);
            vh.img_comment = (ImageView) convertView.findViewById(R.id.img_comment);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        NewsComment news = (NewsComment) mylist.get(position);
        vh.tv_comment_username.setText(news.getUid() + "");
        vh.tv_comment_stamp.setText(news.getStamp());
        vh.tv_userscomment.setText(news.getContent());
        DownPic(news.getPortrait(), vh.img_comment);
//        new ImageLoader(context).display(news.getIcon(),vh.imageView);
        return convertView;
    }

    class ViewHolder {
        ImageView img_comment;
        TextView tv_comment_username, tv_comment_stamp, tv_userscomment;

    }

    public void DownPic(final String path, final ImageView imageView) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 200) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {
                        BufferedInputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message message = new Message();
                        message.what = 200;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    @Override
    public String toString() {
        return "ListViewAdapter{" +
                "newsList=" + mylist +
                ", context=" + context +
                ", layoutInflater=" + layoutInflater +
                '}';
    }
}
