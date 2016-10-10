package com.example.t.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t.myapplication.R;
import com.example.t.myapplication.model.parser.NewsList;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by T on 2016/8/30.
 */
public class ListViewAdapter<T> extends BaseAdapter {
    private List<T> newsList;
    private Context context;
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    public ListViewAdapter(List<T> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (newsList != null) {
            return newsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_show_message_item, null);
            vh = new ViewHolder();
            vh.stamp = (TextView) convertView.findViewById(R.id.tv_show_time);
            vh.title = (TextView) convertView.findViewById(R.id.tv_show_title);
            vh.summary = (TextView) convertView.findViewById(R.id.tv_show_summary);
            vh.imageView = (ImageView) convertView.findViewById(R.id.img_show_message);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        NewsList news = (NewsList) newsList.get(position);
        vh.title.setText(news.getTitle());
        vh.summary.setText(news.getSummary());
        vh.stamp.setText(news.getStamp());
        Picasso.with(context).load(news.getIcon()).fit()
                .into(vh.imageView);
//        DownPic(news.getIcon(), vh.imageView);
//        new ImageLoader(context).display(news.getIcon(),vh.imageView);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView title, summary, stamp;

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
                    if (code == 0) {
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
                "newsList=" + newsList +
                ", context=" + context +
                ", layoutInflater=" + layoutInflater +
                '}';
    }
}
