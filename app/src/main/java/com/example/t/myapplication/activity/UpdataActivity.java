package com.example.t.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.t.myapplication.R;
import com.example.t.myapplication.util.CommonUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdataActivity extends Activity {
    private Button m_btnCheckNewestVersion;
    private long m_newVerCode; //最新版的版本号
    private String m_newVerName; //最新版的版本名
    private String m_appNameStr; //下载到本地要给这个APP命的名字
    //    String link = "http://192.168.3.80:8080/apk/update.apk";
    private String link;
    private Handler m_mainHandler;
    private ProgressDialog m_progressDlg;
    private static final String TAG = "UpdataActivity";
    private boolean istrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        istrue = postCheckNewestVersionCommand2Server();

        //初始化相关变量
        initVariable();

        m_btnCheckNewestVersion.setOnClickListener(btnClickListener);
    }

    private void initVariable() {
        m_btnCheckNewestVersion = (Button) findViewById(R.id.chek_newest_version);
        m_mainHandler = new Handler();
        m_progressDlg = new ProgressDialog(this);
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        m_progressDlg.setIndeterminate(false);
        m_appNameStr = "haha.apk";
    }

    OnClickListener btnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new checkNewestVersionAsyncTask().execute();
        }
    };

    class checkNewestVersionAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO Auto-generated method stub
            if (istrue) {
                int vercode = CommonUtil.getVerCode(getApplicationContext()); // 用到前面第一节写的方法
                Log.i(TAG, "doInBackground: ------vercode-" + vercode);
                Log.i(TAG, "doInBackground: =======m_newVerCode=" + m_newVerCode);
                if (m_newVerCode > vercode) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            if (result) {//如果有最新版本
                doNewVersionUpdate(); // 更新新版本
            } else {
                notNewVersionDlgShow(); // 提示当前为最新版本
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
    }

    /**
     * 从服务器获取当前最新版本号，如果成功返回TURE，如果失败，返回FALSE
     *
     * @return
     */
    private Boolean postCheckNewestVersionCommand2Server() {

        String url = "http://192.168.3.80:8080/test/update.json";
        try {
            sendQuesone();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                try {
                    String ccon = (String) msg.obj;
                    JSONObject listdata = getlistJson(ccon);
                    m_newVerName = listdata.getString("pkgName");
                    m_newVerCode = listdata.getInt("version");
                    link = listdata.getString("link");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void sendQuesone() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpURLConnection httpURLConnection;
                try {
                    URL url = new URL("http://192.168.3.80:8080/test/update.json");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Message message = new Message();
                    message.what = 1;
                    message.obj = stringBuilder.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * Json解析
     *
     * @param json
     * @return
     */
    public JSONObject getlistJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject object = obj.getJSONObject("data");
            return object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 提示更新新版本
     */
    private void doNewVersionUpdate() {
        int verCode = CommonUtil.getVerCode(getApplicationContext());
        String verName = CommonUtil.getVerName(getApplicationContext());

        String str = "当前版本：" + verName + " Code:" + verCode + " ,发现新版本：" + m_newVerName +
                " Code:" + m_newVerCode + " ,是否更新？";
        Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                m_progressDlg.setTitle("正在下载");
                                m_progressDlg.setMessage("请稍候...");
                                downFile(link);  //开始下载
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                finish();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    /**
     * 提示当前为最新版本
     */
    private void notNewVersionDlgShow() {
        int verCode = CommonUtil.getVerCode(this);
        String verName = CommonUtil.getVerName(this);
        String str = "当前版本:" + verName + " Code:" + verCode + ",/n已是最新版,无需更新!";
        Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新")
                .setMessage(str)// 设置内容
                .setPositiveButton("确定",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    private void downFile(final String url) {
        m_progressDlg.show();
        new Thread() {
            public void run() {


                try {
                    URL url1 = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.connect();
                    m_progressDlg.setMax(httpURLConnection.getContentLength());//设置进度条的最大值

                    InputStream is = httpURLConnection.getInputStream();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                m_appNameStr);
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (httpURLConnection.getContentLength() > 0) {
                                m_progressDlg.setProgress(count);
                            }
                        }
                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    down();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void down() {
        m_mainHandler.post(new Runnable() {
            public void run() {
                m_progressDlg.cancel();
                update();
            }
        });
    }

    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), m_appNameStr)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

}
