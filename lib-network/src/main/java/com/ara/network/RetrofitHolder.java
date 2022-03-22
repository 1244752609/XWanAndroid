package com.ara.network;

import android.text.TextUtils;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XieXin on 2018/12/10.
 * RetrofitClient持有人
 */
public class RetrofitHolder {
    private static final String TAG = RetrofitHolder.class.getSimpleName();
    /*** 全局token key */
    public final static String TOKEN_KEY = "_token_key";

    private static String baseUrl = "";
    private static Retrofit instance;

    private RetrofitHolder() {
    }

    public static Retrofit getInstance() {
        if (TextUtils.isEmpty(baseUrl)) {
            Log.e(TAG, "BaseUrl is initialized");
        }
        if (instance == null) {
            instance = new Retrofit.Builder()
                    //设置OKHttpClient
                    .client(OkHttpHolder.getInstance())
                    //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加一个RxJava的adapter
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return instance;
    }

    /**
     * @param timeOut 超时时间(s)
     * @return
     */
    public static Retrofit getInstance(int timeOut) {
        if (TextUtils.isEmpty(baseUrl)) {
            Log.e(TAG, "BaseUrl is initialized");
        }
        if (instance == null) {
            instance = new Retrofit.Builder()
                    //设置OKHttpClient
                    .client(OkHttpHolder.getInstance(timeOut))
                    //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加一个RxJava的adapter
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return instance;
    }

    /**
     * 在Application初始化BaseUrl
     */
    public static void initBaseUrl(String baseUrl) {
        RetrofitHolder.baseUrl = baseUrl;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 重置
     */
    public static void reset() {
        RetrofitHolder.instance = null;
        OkHttpHolder.reset();
    }
}
