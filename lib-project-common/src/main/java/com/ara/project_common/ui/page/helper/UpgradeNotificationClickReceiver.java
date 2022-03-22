package com.ara.project_common.ui.page.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.ara.common.util.LoggerUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;

/**
 * Created by XieXin on 2019/3/11.
 * 通知栏点击回调
 */
public class UpgradeNotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        installApk(context);//开始安装
    }

    // 安装
    public void installApk(Context context) {
        LoggerUtils.i("点击状态栏安装");
        File apkFile = new File(UpgradeManager.getApkPath());
        if (apkFile.exists()) {
            openFile(context, apkFile);
        } else {
            ToastUtils.showShort(com.ara.base.R.string.download_failure);
        }
    }

    public void openFile(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //判断是否是AndroidN以及更高的版本
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //判断是否是AndroidN以及更高的版本
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

}

