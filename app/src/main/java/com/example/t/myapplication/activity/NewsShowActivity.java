package com.example.t.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.t.myapplication.R;
import com.example.t.myapplication.glomble.API;
import com.example.t.myapplication.glomble.Contacts;
import com.example.t.myapplication.model.parser.NewsList;
import com.example.t.myapplication.model.parser.parser.ParserNews;
import com.example.t.myapplication.util.CommonUtil;
import com.example.t.myapplication.util.dbutil.DBTools;

import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsShowActivity extends Activity {
    private static final String TAG = "NewsShowActivity";
    private WebView webView;
    private PopupWindow popupWindow;
    private ImageView imageView, imageView_back;
    private DBTools dbTools;
    private NewsList newsList;
    private ProgressBar progressBar;
    private TextView tv_show_newsnum;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_show);
        if (!CommonUtil.isNetworkAvailable(this)) {
            setContentView(R.layout.no_network);
        } else {
            setContentView(R.layout.activity_news_show);
            webView = (WebView) findViewById(R.id.myWebView);
            imageView = (ImageView) findViewById(R.id.iv_show_menu);
            imageView.setOnClickListener(onClickListener);
            imageView_back = (ImageView) findViewById(R.id.imageView_back);
            imageView_back.setOnClickListener(onClickListener);
            progressBar = (ProgressBar) findViewById(R.id.progress);
            tv_show_newsnum = (TextView) findViewById(R.id.tv_show_newsnum);
            tv_show_newsnum.setOnClickListener(onClickListener);
            dbTools = new DBTools(this);
            requestQueue = Volley.newRequestQueue(this);//实例化一个Volley对象

            Bundle bundle = getIntent().getExtras();
            newsList = (NewsList) bundle.getSerializable("news");

            ssssss();

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);//关键点
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setDisplayZoomControls(false);
            webSettings.setAllowFileAccess(true); // 允许访问文件
            webSettings.setSupportZoom(true); // 支持缩放
            webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
            webSettings.setLoadWithOverviewMode(true);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int mDensity = metrics.densityDpi;
            Log.d("maomao", "densityDpi = " + mDensity);
            if (mDensity == 240) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == 160) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            } else if (mDensity == 120) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == DisplayMetrics.DENSITY_TV) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            }
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            WebChromeClient client = new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    progressBar.setProgress(newProgress);
                    if (newProgress >= 100)
                        progressBar.setVisibility(View.GONE);
                }
            };
            webView.setWebChromeClient(client);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webView.loadUrl(newsList.getLink());
            showPopupWindow();
        }
    }

    private void showPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.layout_favorite, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_favorite);
        TextView tv_Share = (TextView) view.findViewById(R.id.tv_Share);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                boolean isSaved = dbTools.savaLocalFavorite(newsList);
                if (isSaved) {
                    Toast.makeText(NewsShowActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewsShowActivity.this, "已经收藏过，重新添加", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showShare(newsList);
            }
        });

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView_back:
                    finish();
                    break;
                case R.id.iv_show_menu:
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else if (popupWindow != null) {
                        popupWindow.showAsDropDown(imageView, 0, 5);
                    }
                    break;

                case R.id.tv_show_newsnum:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news", newsList);
                    Intent intent = new Intent(NewsShowActivity.this, CommentActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;

            }

        }
    };

    private void ssssss() {
        int nid = newsList.getNid();
        String url = API.USER_CENTER_NUM + "&ver=" + Contacts.VER + "&nid=" + nid;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        int data = ParserNews.getNewsCommentNum(jsonObject.toString());
                        tv_show_newsnum.setText("跟帖:" + data);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }


    private void showShare(NewsList newsList) {
        ShareSDK.initSDK(NewsShowActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(newsList.getTitle());
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(newsList.getLink());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(newsList.getSummary());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(newsList.getIcon());//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(NewsShowActivity.this);
    }
}



