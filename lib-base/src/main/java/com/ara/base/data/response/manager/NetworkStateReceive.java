package com.ara.base.data.response.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.ara.network.R;
import com.ara.network.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by XieXin on 2022/2/15.
 * 网络变化监听
 */
public class NetworkStateReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), ConnectivityManager.EXTRA_NO_CONNECTIVITY)) {
            if (!NetworkUtils.isConnected()) {
                ToastUtils.showShort(R.string.net_not_helpful);
            }
        }
    }
}
