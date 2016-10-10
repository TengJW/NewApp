package com.example.t.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片的三级缓存
 * Created by T on 2016/9/2.
 */
public class ImageLoader {
    private Context context;
    String imgpath;
    //Lrucache缓存
    private static LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().freeMemory() / 4));

    public ImageLoader(Context context) {
        this.context = context;
    }

    /**
     * 加载图片
     *
     * @param path      图片的地址
     * @param imageView imageview控件
     * @return
     */
    public Bitmap display(String path, ImageView imageView) {
        Bitmap bitmap = null;
        if (path == null && path.length() <= 0) {
            return bitmap;
        }
        //先去内存缓存中找图片
        bitmap = loadImageFromReference(path);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return bitmap;
        }
        //从本地文件缓存中取

        bitmap = loadImageFromCache(path);
        if (bitmap != null) {
            mCache.put(path, bitmap);//存入缓存区
            imageView.setImageBitmap(bitmap);
            return bitmap;
        }

        //村网络中取图片
        DownPic(path, imageView);


        return bitmap;
    }

    /**
     * 从网络下载图片
     *
     * @param path
     * @param iv
     */
    public void DownPic(final String path, final ImageView iv) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int code = msg.what;
                if (code == 1) {
                    Bitmap bm = (Bitmap) msg.obj;
                    iv.setImageBitmap(bm);
                }

            }
        };

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream is = con.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);//从网络上加载图片
                    //存入缓存
                    mCache.put(path, bitmap);
                    //存入本地

                    saveLocal(path, bitmap);

                    Message message = new Message();
                    message.what = 1;
                    message.obj = bitmap;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 从缓存中取图片
     * 根据存的时候的一个类似于keysss
     *
     * @param url
     * @return
     */
    private Bitmap loadImageFromReference(String url) {
        return mCache.get(url);
    }

    /**
     * 从本地取，也就是SD卡
     *
     * @param url
     * @return
     */
    private Bitmap loadImageFromCache(String url) {
        //http://www.baidu.com/text/aa.jpg
        String name = url.substring(url.lastIndexOf("/") + 1);
        if (name == null) {
            return null;
        }
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            return null;
        }
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return null;
        }

        File bitmapFile = null;
        for (File file : files) {
            if (file.getName().equals(name)) {
                bitmapFile = file;
            }
        }
        if (bitmapFile == null) {
            return null;
        }
        Bitmap fileBitmap = null;
        fileBitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
        return fileBitmap;
    }

    /**
     * 讲图片存入本地SD卡
     *
     * @param url
     */
    private void saveLocal(String url, Bitmap bitmap) {
        String nadme = url.substring(url.lastIndexOf("/") + 1);
        File cacheDir = context.getExternalCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        OutputStream stream = null;
        try {

            stream = new FileOutputStream(new File(cacheDir, nadme));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);//写入本地
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
                imgpath = cacheDir.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除本地存储的照片
     */
    public void clearBitMap(String url) {
        String nadme = url.substring(url.lastIndexOf("/") + 1);
        File cacheDir = context.getExternalCacheDir();
        File file = new File(cacheDir + "/" + nadme);
        if (file.exists()) {
            file.delete();
        }
    }

}
