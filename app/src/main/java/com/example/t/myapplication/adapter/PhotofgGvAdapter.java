package com.example.t.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.t.myapplication.R;
import com.example.t.myapplication.base.BaseBaseAdapter;
import com.example.t.myapplication.model.parser.NewsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 2016/9/20.
 */
public class PhotofgGvAdapter extends BaseBaseAdapter<NewsList> {
    LayoutInflater layoutInflater = null;
    private List<NewsList> newsLists = new ArrayList<NewsList>();
    ;

    /**
     * 构造方法 加载布局时需要用到layoutinflater ，在构造方法中初始化
     *
     * @param context
     */
    public PhotofgGvAdapter(Context context) {
        super(context);
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return newsLists.size();
    }

    @Override
    public NewsList getItem(int position) {
        return newsLists.get(position);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = layoutInflater
                    .inflate(R.layout.layout_fragmentphoto_gv_item, null);
            vh.img_gv_item = (ImageView) convertView
                    .findViewById(R.id.img_gv_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
//        new ImageLoader(context).display(getItem(position).getIcon(), vh.img_gv_item);//三级缓存的方式加载图片
        Picasso.with(context).load(getItem(position).getIcon()).fit()
                .into(vh.img_gv_item);
        return convertView;
    }


    class ViewHolder {
        ImageView img_gv_item;
    }

    /**
     * 封装一个添加一条数据的方法
     *
     * @param t          添加的数据
     * @param isClearOld 是否清理旧数据
     */
    public void appendDate(List<NewsList> t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            newsLists.clear();
        newsLists.addAll(t);
    }


}
