package com.example.t.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.t.myapplication.R;
import com.example.t.myapplication.adapter.NewsCommentAdapter;
import com.example.t.myapplication.glomble.API;
import com.example.t.myapplication.glomble.Contacts;
import com.example.t.myapplication.model.parser.BaseEntry;
import com.example.t.myapplication.model.parser.NewsComment;
import com.example.t.myapplication.model.parser.NewsList;
import com.example.t.myapplication.model.parser.NewsSendComment;
import com.example.t.myapplication.model.parser.parser.ParserNews;
import com.example.t.myapplication.util.CommonUtil;
import com.example.t.myapplication.util.ShareUtil;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CommentActivity extends Activity {
    private ImageView imageView_back_comment;
    private NewsList newsList;
    private List<NewsComment> list;
    private ListView lv_newscomment;
    private NewsCommentAdapter adapter;
    private RequestQueue requestQueue;
    private static final String TAG = "CommentActivity";
    private EditText et_comment;
    private ImageView img_send_comment;
    private int nid = 0;
    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Bundle bundle = getIntent().getExtras();
        newsList = (NewsList) bundle.getSerializable("news");
        nid = newsList.getNid();
        requestQueue = Volley.newRequestQueue(this);//实例化一个Volley对象
        imageView_back_comment = (ImageView) findViewById(R.id.imageView_back_comment);
        imageView_back_comment.setOnClickListener(onClickListener);

        et_comment = (EditText) findViewById(R.id.et_comment);
        img_send_comment = (ImageView) findViewById(R.id.img_send_comment);
        img_send_comment.setOnClickListener(onClickListener);

        lv_newscomment = (ListView) findViewById(R.id.lv_newscomment);
        adapter = new NewsCommentAdapter(CommentActivity.this);
        lv_newscomment.setAdapter(adapter);
        newsComment();
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView_back_comment:
                    finish();
                    break;
                case R.id.img_send_comment:
                    String commenttext = et_comment.getText().toString();
                    if (commenttext == null)
                        break;

                    sendComment(commenttext);

                    break;
            }
        }
    };

    private void sendComment(String comment) {
        String ctx = null;
        try {
            ctx = URLEncoder.encode(comment, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = ShareUtil.getToken(this, "token");
        String url = API.USER_COMMENT + "&ver=" + Contacts.VER + "&nid=" + nid + "&token=" + token + "&imei=" + CommonUtil.getIMEI(this) + "&ctx=" + ctx;
        Log.i(TAG, "sendComment: ============--------------" + url);
        Log.i(TAG, "sendComment: ===============token" + token);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<NewsSendComment> list = ParserNews.getComment(jsonObject.toString());
                        int status = list.getStatus();
                        switch (status) {
                            case 0:
                                NewsSendComment newsSendComment = (NewsSendComment) list.getData();
                                String explain = newsSendComment.getExplain();
                                Toast.makeText(CommentActivity.this, explain, Toast.LENGTH_SHORT).show();
                                newsComment();
                                et_comment.setText("");
                                break;
                            case -3:
                                Toast.makeText(CommentActivity.this, "未登录!", Toast.LENGTH_SHORT).show();
                                setAngleWidthAnim();
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }


    private void newsComment() {

        String stamp = CommonUtil.getSystime();
        final String url = API.NEWS_COMMENT + "&ver=" + Contacts.VER + "&nid=" + nid + "&type=1" + "&stamp=" + stamp + "&cid=1" + "&dir=1" + "&cnt=20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(TAG, "onResponse: ============" + url);
                        list = ParserNews.getNewsComment(jsonObject.toString());
                        adapter.appendDate(list, true);
                        adapter.updateAdapter();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void setAngleWidthAnim() {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                switch (state) {
                    case 0:
                        state = 1;
                        break;
                    case 1:
                        Intent intent = new Intent(CommentActivity.this, HomeActivity.class);
                        timer.cancel();
                        intent.putExtra("fragment", 3);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        };
        timer.schedule(task, 500, 500);
    }
}
