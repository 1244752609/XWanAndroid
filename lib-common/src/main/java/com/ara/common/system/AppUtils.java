package com.ara.common.system;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import com.ara.common.util.LoggerUtils;
import com.ara.common.util.MMKVUtils;
import com.github.gzuliyujiang.oaid.DeviceID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

/**
 * Created by XieXin on 2018/12/10.
 * App相关工具类
 */
public final class AppUtils {
    private static final String PREF_KEY_ID = "pref_key_uuid";

    private AppUtils() {
    }

    /**
     * 获取应用程序名称
     *
     * @param context Context
     * @return App版本号
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
        }
        return "";
    }

    /**
     * 获取App版本号
     *
     * @param context Context
     * @return App版本号
     */
    public static String getAppVersionName(Context context) {
        return getAppVersionName(context, context.getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param context     Context
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LoggerUtils.e(e.getMessage());
        }
        return null;
    }

    /**
     * 获取App版本码
     *
     * @param context Context
     * @return App版本码
     */
    public static long getAppVersionCode(Context context) {
        return getAppVersionCode(context, context.getPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param context     Context
     * @param packageName 包名
     * @return App版本码
     */
    public static long getAppVersionCode(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            if (SdkVersionUtils.hasP()) {
                return pi.getLongVersionCode();
            } else {
                return pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            LoggerUtils.e(e.getMessage());
        }
        return -1;
    }

    /**
     * 判断某一应用是否正在运行
     *
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity != null && info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某一Service是否正在运行
     *
     * @param context     上下文
     * @param serviceName Service的全路径： 包名 + service的类名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取ANDROID_ID
     *
     * @return ANDROID_ID
     */
    private static String getAndroidID(Context context) {
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (id == null) id = "";
        return id;
    }

    /**
     * 加密ANDROID_ID
     *
     * @param context
     * @return DeviceUUID
     */
    private static String getDeviceUUID(Context context) {
        String androidId = getAndroidID(context);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) androidId.hashCode() << 32));
        return deviceUuid.toString();
    }

    /**
     * 获取自己生产 APP UUID
     *
     * @return
     */
    private static String getAppUUID() {
        String uuid = MMKVUtils.decodeString(PREF_KEY_ID, "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            MMKVUtils.encode(PREF_KEY_ID, uuid);
        }
        return uuid;
    }

    /**
     * 获取 UUID
     *
     * @return UUID
     */
    public static String getUUID(Context context) {
        String uuid = getDeviceUUID(context);
        if (TextUtils.isEmpty(uuid)) uuid = getAppUUID();
        return uuid;
    }

    /**
     * 获取中国通用广告 ID
     *
     * @param context
     * @return
     */
    public static String getCNAdId(Context context) {
        String CNAdId = MMKVUtils.decodeString(PREF_KEY_ID, "");
        if (TextUtils.isEmpty(CNAdId)) {
            CNAdId = Settings.System.getString(context.getContentResolver(), "ZHVzY2Lk");
            if (TextUtils.isEmpty(CNAdId)) CNAdId = getCNAdId();
            LoggerUtils.i("获取中国通用广告 CNAdId : " + CNAdId);
            if (!TextUtils.isEmpty(CNAdId)) MMKVUtils.encode(PREF_KEY_ID, CNAdId);

        }
        return CNAdId;
    }

    private static String getCNAdId() {
        String scid = null;
        String filePath = "/sdcard/Android/ZHVzY2Lk";

        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                scid = bufferedReader.readLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        return scid;
    }

    /**
     * 获取OAID
     *
     * @return OAID
     */
    public static String getOAID() {
        String OAID = MMKVUtils.decodeString(PREF_KEY_ID, "");
        if (TextUtils.isEmpty(OAID)) {
            OAID = DeviceID.getOAID();
            LoggerUtils.i("获取OAID : " + OAID);
            if (!TextUtils.isEmpty(OAID)) MMKVUtils.encode(PREF_KEY_ID, OAID);
        }
        return OAID;
    }

    /**
     * 获取设备id
     *
     * @param context
     * @return DeviceId
     */
    public static String getDeviceId(Context context) {
        String deviceId = getCNAdId(context);
        if (TextUtils.isEmpty(deviceId)) deviceId = getOAID();
        if (TextUtils.isEmpty(deviceId)) deviceId = getUUID(context);
        return deviceId;
    }

    /**
     * 隐藏导航栏
     *
     * @param context 上下文
     */
    public static void hideNavKey(Context context) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = ((Activity) context).getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = ((Activity) context).getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}