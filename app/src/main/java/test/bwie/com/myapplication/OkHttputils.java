package test.bwie.com.myapplication;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 荆泽阳 on 2017/3/6.
 * .::::.
 * .::::::::.
 * :::::::::::  Goddess bless, never bug
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 */

public class OkHttputils {
    private static OkHttputils httpUtils;
    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;
    private Handler mHandler;

    private OkHttputils() {
        mOkHttpClient = new OkHttpClient();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttputils getHttpUtils() {
        if (httpUtils == null) {
            synchronized (OkHttputils.class) {
                if (httpUtils == null) {
                    httpUtils = new OkHttputils();
                }
            }
        }
        return httpUtils;
    }
    public <T> void loadDataFromNet(final String url, final Class<T> clazz, final CallBackListener<T> callBack) {
        //2 实例化Request对象
        Request request = new Request.Builder().url(url).build();
        //3 请求网络数据
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("TAG", "请求失败");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //服务器返回数据类型为String类型
                //得到json串
                String json = response.body().string();
                final T result = mGson.fromJson(json, clazz);
                //线程转换
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //完成线程转换
                        callBack.onSuccess(result);
                    }
                });
            }
        });
    }



    public interface CallBackListener<T> {
        void onSuccess(T result);

        void onFail();
    }
}
