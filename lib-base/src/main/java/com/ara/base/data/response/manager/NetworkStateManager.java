package com.ara.base.data.response.manager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.Utils;

/**
 * Created by XieXin on 2022/2/15.
 * 网络状态管理
 * 让 NetworkStateManager 可观察页面生命周期，
 * 从而在页面失去焦点时，
 * 及时断开本页面对网络状态的监测，以避免重复回调和一系列不可预期的问题。
 */
public class NetworkStateManager implements DefaultLifecycleObserver {
    private static final NetworkStateManager S_INSTANCE = new NetworkStateManager();
    private final NetworkStateReceive mNetworkStateReceive = new NetworkStateReceive();

    private NetworkStateManager() {
    }

    public static NetworkStateManager getsInstance() {
        return S_INSTANCE;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.e("NetworkStateManager", owner + "\n-------------------->onCreate ");
    }

    //让 NetworkStateManager 可观察页面生命周期，
    // 从而在页面失去焦点时，
    // 及时断开本页面对网络状态的监测，以避免重复回调和一系列不可预期的问题。
    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.e("NetworkStateManager", owner + "\n-------------------->onResume " + mNetworkStateReceive);
        try {
            IntentFilter filter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
            Utils.getApp().getApplicationContext().registerReceiver(mNetworkStateReceive, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.e("NetworkStateManager", owner + "\n-------------------->onPause " + mNetworkStateReceive);
        try {
            Utils.getApp().getApplicationContext().unregisterReceiver(mNetworkStateReceive);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
