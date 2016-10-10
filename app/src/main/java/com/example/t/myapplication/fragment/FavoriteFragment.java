package com.example.t.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.t.myapplication.R;
import com.example.t.myapplication.activity.HomeActivity;
import com.example.t.myapplication.activity.NewsShowActivity;
import com.example.t.myapplication.adapter.NewsListAdapter;
import com.example.t.myapplication.model.parser.NewsList;
import com.example.t.myapplication.util.dbutil.DBTools;

import java.util.List;

public class FavoriteFragment extends Fragment {
    private ListView listView;
    private NewsListAdapter adapter;
    private DBTools dbTools;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        listView = (ListView) view.findViewById(R.id.ravoriteListView);
        adapter = new NewsListAdapter(getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        dbTools = new DBTools(getContext());
        getData();
        return view;
    }


    private void getData() {
        List<NewsList> list = dbTools.getLoaclFavorite();
        adapter.appendDate(list, true);
        adapter.updateAdapter();

    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            NewsList newsList = adapter.getItem(position);
            bundle.putSerializable("news", newsList);
            ((HomeActivity) getActivity()).openActivity(NewsShowActivity.class, bundle);

        }
    };
}
