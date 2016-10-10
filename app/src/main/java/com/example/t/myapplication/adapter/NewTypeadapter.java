package com.example.t.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.t.myapplication.R;
import com.example.t.myapplication.model.parser.SubType;

/**
 * Created by T on 2016/9/6.
 */
public class NewTypeadapter extends MyBaseAdapter<SubType> {


    public NewTypeadapter(Context context) {
        super(context);
    }

    class ViewHolder {
        TextView tv_newtype_item;

    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_newtype_item, null);
            vh = new ViewHolder();
            vh.tv_newtype_item = (TextView) convertView.findViewById(R.id.tv_newtype_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        String ss = getItem(position).getSubgroup();
        vh.tv_newtype_item.setText(ss);

        return convertView;
    }
}
