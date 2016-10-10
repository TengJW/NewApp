package com.example.t.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.t.myapplication.R;
import com.example.t.myapplication.activity.HomeActivity;

public class SlidingMenuFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout relayout_news, relayout_FAYORITE, relayout_LOACL, relayout_COMMENT, relayout_PHOTO;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding_menu, container, false);
        relayout_news = (RelativeLayout) view.findViewById(R.id.relayout_news);
        relayout_FAYORITE = (RelativeLayout) view.findViewById(R.id.relayout_FAYORITE);
        relayout_LOACL = (RelativeLayout) view.findViewById(R.id.relayout_LOACL);
        relayout_COMMENT = (RelativeLayout) view.findViewById(R.id.relayout_COMMENT);
        relayout_PHOTO = (RelativeLayout) view.findViewById(R.id.relayout_PHOTO);
        relayout_news.setOnClickListener(this);
        relayout_FAYORITE.setOnClickListener(this);
        relayout_LOACL.setOnClickListener(this);
        relayout_COMMENT.setOnClickListener(this);
        relayout_PHOTO.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relayout_news:
                ((HomeActivity) getActivity()).showHomeFragment();
                relayout_news.setBackgroundColor(0x33c85555);
                relayout_FAYORITE.setBackgroundColor(0xc3f3fd);
                relayout_LOACL.setBackgroundColor(0xc3f3fd);
                relayout_COMMENT.setBackgroundColor(0xc3f3fd);
                relayout_PHOTO.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.relayout_FAYORITE:
                ((HomeActivity) getActivity()).showFrvoriteFragment();
                relayout_news.setBackgroundColor(0xc3f3fd);
                relayout_FAYORITE.setBackgroundColor(0x33c85555);
                relayout_LOACL.setBackgroundColor(0xc3f3fd);
                relayout_COMMENT.setBackgroundColor(0xc3f3fd);
                relayout_PHOTO.setBackgroundColor(0xc3f3fd);

                break;
            case R.id.relayout_LOACL:
                ((HomeActivity) getActivity()).showLocalFragment();
                relayout_news.setBackgroundColor(0xc3f3fd);
                relayout_FAYORITE.setBackgroundColor(0xc3f3fd);
                relayout_LOACL.setBackgroundColor(0x33c85555);
                relayout_COMMENT.setBackgroundColor(0xc3f3fd);
                relayout_PHOTO.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.relayout_COMMENT:
                ((HomeActivity) getActivity()).showcommentFragment();
                relayout_news.setBackgroundColor(0xc3f3fd);
                relayout_FAYORITE.setBackgroundColor(0xc3f3fd);
                relayout_LOACL.setBackgroundColor(0xc3f3fd);
                relayout_COMMENT.setBackgroundColor(0x33c85555);
                relayout_PHOTO.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.relayout_PHOTO:
                ((HomeActivity) getActivity()).showphotoFragment();
                relayout_news.setBackgroundColor(0xc3f3fd);
                relayout_FAYORITE.setBackgroundColor(0xc3f3fd);
                relayout_LOACL.setBackgroundColor(0xc3f3fd);
                relayout_COMMENT.setBackgroundColor(0xc3f3fd);
                relayout_PHOTO.setBackgroundColor(0x33c85555);
                break;
        }

    }
}
