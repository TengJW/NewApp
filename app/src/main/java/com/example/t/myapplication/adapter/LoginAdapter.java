package com.example.t.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.t.myapplication.R;
import com.example.t.myapplication.model.parser.Loginlog;

/**
 * Created by T on 2016/9/9.
 */
public class LoginAdapter extends MyBaseAdapter<Loginlog> {
    public LoginAdapter(Context context) {
        super(context);
    }

    class ViewHolder {
        TextView tv_loginTime, tv_loginAddress, tv_loginDevice;
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_login_item, null);
            vh = new ViewHolder();
            vh.tv_loginTime = (TextView) convertView.findViewById(R.id.tv_loginTime);
            vh.tv_loginAddress = (TextView) convertView.findViewById(R.id.tv_loginAddress);
            vh.tv_loginDevice = (TextView) convertView.findViewById(R.id.tv_loginDevice);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_loginTime.setText(getItem(position).getTime());
        vh.tv_loginAddress.setText(getItem(position).getAddress());
        vh.tv_loginDevice.setText(getItem(position).getDevice()+"");
        return convertView;
    }
}
