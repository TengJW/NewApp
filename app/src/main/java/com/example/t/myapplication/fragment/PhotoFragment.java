package com.example.t.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.example.t.myapplication.R;
import com.example.t.myapplication.adapter.PhotofgGvAdapter;
import com.example.t.myapplication.model.parser.NewsList;
import com.example.t.myapplication.util.dbutil.DBTools;

import java.util.List;

public class PhotoFragment extends Fragment {
    private PhotofgGvAdapter adapter;
    private GridView gv_photo;
    private RequestQueue requestQueue;
    private static final String TAG = "PhotoFragment";
    private List<NewsList> lists;
    private DBTools DBTools;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        gv_photo = (GridView) view.findViewById(R.id.gv_photo);
        adapter = new PhotofgGvAdapter(getContext());
        gv_photo.setAdapter(adapter);
//        requestQueue = Volley.newRequestQueue(getActivity());//实例化一个Volley对象
        DBTools = new DBTools(getContext());
        lists = DBTools.getIconList();
        sendRequestNews();
        return view;
    }


    private void sendRequestNews() {
        adapter.appendDate(lists, true);
        adapter.notifyDataSetChanged();
    }

}
