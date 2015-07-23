package sun.tianyu.ijob;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import sun.tianyu.ijob.common.DefautValues;
import sun.tianyu.ijob.common.GlobalValues;

/**
 * Created by Developer on 15/07/22.
 */
public class IjobApplication extends Application{
    public  GlobalValues globalValues; // 変数、　初期化必須、クリア必要
    public  DefautValues defautValues; // static 定数、初期化不要
    private RequestQueue mRequestQueue; // 通信用キュー
    public ImageLoader.ImageCache mImageCache; //アプリ内画像保存用キャッシュ
    public ImageLoader mImageLoader;
    public LruCache<String, Bitmap> mMemoryCache;
    @Override
    public void onCreate() {
        super.onCreate();
        // グローバル値保存用のオブジェクト初期化
        globalValues = new GlobalValues();

        // ImageCache初期化
        initImageCache();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public void initImageCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024); // 206SH: 128M
        int cacheSize = maxMemory / (8*4); //　maxMemory / 32
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 使用キャッシュサイズ(ここではKB単位)
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };

        mImageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                if (null != mMemoryCache.get(url)) {
                }
                return mMemoryCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mMemoryCache.put(url, bitmap);
            }
        };
        mImageLoader = new com.android.volley.toolbox.ImageLoader(mRequestQueue, mImageCache);
    }

}
