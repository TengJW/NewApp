package com.example.t.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.t.myapplication.R;
import com.example.t.myapplication.activity.HomeActivity;
import com.example.t.myapplication.activity.NewsShowActivity;
import com.example.t.myapplication.adapter.NewTypeadapter;
import com.example.t.myapplication.adapter.NewsListAdapter;
import com.example.t.myapplication.glomble.API;
import com.example.t.myapplication.glomble.Contacts;
import com.example.t.myapplication.model.parser.NewsList;
import com.example.t.myapplication.model.parser.SubType;
import com.example.t.myapplication.model.parser.parser.ParserNews;
import com.example.t.myapplication.util.CommonUtil;
import com.example.t.myapplication.util.HorizontalListView;
import com.example.t.myapplication.util.dbutil.DBTools;

import org.json.JSONObject;

import java.util.List;


/**
 * Volley框架
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HorizontalListView horizontalListView;
    private RequestQueue requestQueue;
    private RequestQueue requestQueue2;
    private NewTypeadapter newTypeadapter;
    private ListView Fragment_listView;
    private NewsListAdapter newsListAdapter;
    private List<NewsList> list;
    private DBTools dbTools;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        horizontalListView = (HorizontalListView) view.findViewById(R.id.horizontalView);
        Fragment_listView = (ListView) view.findViewById(R.id.Fragment_listView);
        newTypeadapter = new NewTypeadapter(getContext());
        newsListAdapter = new NewsListAdapter(getContext());
        horizontalListView.setAdapter(newTypeadapter);
        Fragment_listView.setAdapter(newsListAdapter);
        Fragment_listView.setOnItemClickListener(onItemClickListener);
        requestQueue = Volley.newRequestQueue(getContext());//实例化一个Volley对象




        dbTools = new DBTools(getActivity());
        requestQueue2 = Volley.newRequestQueue(getContext());
        loadTitleTypeData();
        loadHomeList();

        return view;
    }


    /**
     * 加载标题栏
     */
    private void loadTitleTypeData() {
        if (CommonUtil.isNetworkAvailable(getActivity())) {
            sendRequestData();
        } else {
            List<SubType> list = dbTools.getLoaclType();
            if (list != null && list.size() > 0) {
                newTypeadapter.appendDate(list, true);
                newTypeadapter.updateAdapter();
            } else {
                Toast.makeText(((HomeActivity) getActivity()), "网络异常", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 加载首页数据
     */
    private void loadHomeList() {
        if (CommonUtil.isNetworkAvailable(getActivity())) {
            sendRequestNews();
        } else {
            list = dbTools.getLoaclNews();
            if (list != null && list.size() > 0) {
                newsListAdapter.appendDate(list, true);
                newsListAdapter.updateAdapter();
            } else {
                Toast.makeText(((HomeActivity) getActivity()), "网络异常", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 加载标题数据
     *
     * @param
     */
    private void sendRequestData() {
        String url = API.NEWS_SORT + "ver=" + Contacts.VER + "&imei=" + CommonUtil.getIMEI(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        List<SubType> list = ParserNews.getNewsType(jsonObject.toString());
                        for (int i = 0; i < list.size(); i++) {
                            dbTools.savaLocalType(list.get(i));
                        }
                        newTypeadapter.appendDate(list, true);
                        newTypeadapter.updateAdapter();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void sendRequestNews() {
        String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1dsf&subid=1&dir=1&nid=1&stamp=20160828&cnt=20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        list = ParserNews.getNewsList(jsonObject.toString());
                        for (int i = 0; i < list.size(); i++) {
                            dbTools.savaLocalNews(list.get(i));
                        }
                        newsListAdapter.appendDate(list, true);
                        newsListAdapter.updateAdapter();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            NewsList newsList = newsListAdapter.getItem(position);
            bundle.putSerializable("news", newsList);
            ((HomeActivity) getActivity()).openActivity(NewsShowActivity.class, bundle);

        }
    };

}
