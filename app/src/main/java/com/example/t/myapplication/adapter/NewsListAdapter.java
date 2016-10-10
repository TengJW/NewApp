package com.example.t.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t.myapplication.R;
import com.example.t.myapplication.model.parser.NewsList;
import com.example.t.myapplication.util.ImageLoader;

/**
 * Created by T on 2016/9/9.
 */
public class NewsListAdapter extends MyBaseAdapter<NewsList> {






    public NewsListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_newslist_item, null);
            vh = new ViewHolder();
            vh.tv_news_title = (TextView) convertView.findViewById(R.id.tv_news_title);
            vh.tv_news_stamp = (TextView) convertView.findViewById(R.id.tv_news_stamp);
            vh.im_news_icon = (ImageView) convertView.findViewById(R.id.im_news_icon);
            vh.tv_news_summary = (TextView) convertView.findViewById(R.id.tv_news_summary);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
//        NewsList news = newsList.get(position);
        String summary = getItem(position).getSummary();
        String icon = getItem(position).getIcon();
        String stamp = getItem(position).getStamp();
        String title = getItem(position).getTitle();
        int nid = getItem(position).getNid();
        String link = getItem(position).getLink();
        int type = getItem(position).getType();

        vh.tv_news_stamp.setText(stamp);
        vh.tv_news_title.setText(title);
        vh.tv_news_summary.setText(summary);

//        DownPic(news.getIcon(),vh.imageView);
        new ImageLoader(context).display(icon, vh.im_news_icon);//三级缓存的方式加载图片

        return convertView;
    }

    class ViewHolder {
        TextView tv_news_title, tv_news_stamp, tv_news_summary;
        ImageView im_news_icon;

    }
}
