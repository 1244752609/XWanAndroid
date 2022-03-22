package com.ara.base;

import android.app.Application;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.ara.network.RetrofitHolder;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

/**
 * Created by XieXin on 2022/2/14.
 */
public abstract class BaseApplication extends Application implements ViewModelStoreOwner {
    private BaseApplication instance;
    private ViewModelStore mAppViewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppViewModelStore = new ViewModelStore();

        DeviceID.register(this);
        instance = this;
        //初始化腾讯存储功能
        MMKV.initialize(this);
        //初始化BaseUrl
        RetrofitHolder.initBaseUrl(getBaseUrl());
        //初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BaseApplication.this.isLoggable();//如果是Debug模式，打印日志
            }
        });
        init();
    }

    protected abstract void init();

    /**
     * 设置Logger日志是否打印
     *
     * @return boolean
     */
    protected abstract boolean isLoggable();

    protected abstract String getBaseUrl();

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    /**
     * 获取BaseApplication实例
     *
     * @return BaseApplication
     */
    public BaseApplication getInstance() {
        if (instance == null) throw new NullPointerException("please inherit BaseApplication.");
        return instance;
    }

    /**
     * 重写 getResource 方法，防止系统字体影响
     */
    @Override
    public Resources getResources() {//禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

}
