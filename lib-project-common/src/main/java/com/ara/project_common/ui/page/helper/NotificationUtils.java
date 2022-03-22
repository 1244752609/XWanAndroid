package com.ara.project_common.ui.page.helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.ara.common.util.LoggerUtils;
import com.ara.project_common.R;
import com.ara.widget.dialog.XPromptDialog;

/**
 * Created by XieXin on 2019/3/11.
 * 通知栏
 */
public class NotificationUtils {
    private static NotificationManager manager;

    private NotificationUtils() {
    }

    /**
     * 获取系统提供的通知管理服务
     *
     * @param context
     * @return
     */
    public static NotificationManager getManager(Context context) {
        if (manager == null) {
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    /**
     * 获取无效果Notification
     *
     * @param context     上下文
     * @param title       标题
     * @param content     内容
     * @param channelId   渠道id
     * @param channelName 渠道名
     * @return NotificationCompat
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static NotificationCompat.Builder getNoneNotificationBuilder(Context context, String title,
                                                                         String content,
                                                                         String channelId,
                                                                         String channelName
    ) {
        //大于8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //id随便指定
            NotificationChannel channel = new NotificationChannel(channelId, context.getPackageName(), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setName(channelName);
            channel.canBypassDnd();//可否绕过，请勿打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//锁屏显示通知
            channel.setSound(null, null);
            channel.getGroup();//获取通知渠道组
            channel.setBypassDnd(true);//设置可以绕过，请勿打扰模式
            channel.enableLights(false);
            channel.enableVibration(false);//取消震动
            channel.setVibrationPattern(new long[]{0});//取消震动
            //通知管理者创建的渠道有修改如果是统一channelId必须删除使用才有效
            getManager(context).deleteNotificationChannel(channelId);
            //通知管理者创建的渠道
            getManager(context).createNotificationChannel(channel);

        }
        return new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)//取消震动
                .setVibrate(new long[]{0})//取消震动
                .setSmallIcon(R.mipmap.ic_notification_download)
                .setSound(null);


    }

    /**
     * 获取Notification
     *
     * @param context     上下文
     * @param title       标题
     * @param content     内容
     * @param channelId   渠道id 通知管理类别
     * @param channelName 渠道名 通知管理类别
     * @return NotificationCompat
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static NotificationCompat.Builder getNotificationBuilder(Context context, String title,
                                                                    String content, String channelId,
                                                                    String channelName,
                                                                    int iconResId,
                                                                    boolean isEnableLights,
                                                                    boolean isEnableVibration,
                                                                    boolean isEnableSound) {
        NotificationCompat.Builder notification;
        //大于8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!getManager(context).areNotificationsEnabled()) {
                showNotifyPermissionDialog(context);
            }

            //id随便指定
            NotificationChannel channel = new NotificationChannel(channelId, context.getPackageName(), NotificationManager.IMPORTANCE_HIGH);
            channel.setName(channelName);
            channel.canBypassDnd();//可否绕过，请勿打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//锁屏显示通知
            channel.getGroup();//获取通知渠道组
            channel.setBypassDnd(true);//设置可以绕过，请勿打扰模式
            channel.enableLights(isEnableLights);//呼吸灯
            if (isEnableLights) {
                //闪关灯的灯光颜色
                channel.setLightColor(Color.GREEN);
                //是否会有灯光
                channel.shouldShowLights();
            }
            if (isEnableSound) {
                //获取系统通知响铃声音的配置
//                channel.getAudioAttributes();
//                Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.daihing_bgm);
                Uri sound = getSystemDefaultRingtoneUri(context);
                channel.setSound(sound, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            } else {
                channel.setSound(null, null);
            }
            if (isEnableVibration) {
                channel.enableVibration(true);//震动
                channel.setVibrationPattern(new long[]{100, 200, 300});//震动
            } else {
                channel.enableVibration(false);//取消震动
                channel.setVibrationPattern(new long[]{0});//取消震动
            }
            //通知管理者创建的渠道有修改如果是统一channelId必须删除使用才有效
//                getManager(context).deleteNotificationChannel(channelId);
            //通知管理者创建的渠道
            getManager(context).createNotificationChannel(channel);
        }
        if (iconResId == 0) iconResId = R.mipmap.ic_notification_download;
        notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setTicker(content)
                .setSmallIcon(iconResId)
                .setWhen(System.currentTimeMillis());
        notification.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notification.setShowWhen(true);
        if (isEnableLights) {
            notification.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        }
        if (isEnableVibration) {
            notification.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        } else {
            notification.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);//取消震动
            notification.setVibrate(new long[]{0});//取消震动
        }
        if (isEnableSound) {
            notification.setDefaults(NotificationCompat.DEFAULT_SOUND);

//            Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.daihing_bgm);
            Uri sound = getSystemDefaultRingtoneUri(context);
            notification.setSound(sound);
        } else {
            notification.setSound(null);
        }
        return notification;


    }

    private static NotificationCompat.Builder builder;

    /**
     * 显示通知
     *
     * @param context     上下文
     * @param title       标题
     * @param content     内容
     * @param manageId    管理id 不同消息需要不同id
     * @param channelId   渠道id 通知管理类别
     * @param channelName 渠道名 通知管理类别
     * @param progress    进度
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void showProgressNotification(Context context, String title, String content, int manageId,
                                                String channelId, String channelName, int progress) {
        if (builder == null) {
            builder = getNoneNotificationBuilder(context, title, content, channelId, channelName);
            builder.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);//取消震动
            builder.setVibrate(new long[]{0});//取消震动
            builder.setSound(null);//取消声音
            builder.setAutoCancel(true);//点击后消失
        }

        LoggerUtils.i("showProgressNotification progress " + progress);
        //需要下载完成才能点击
        if (progress == 100) {
            //点击处理
            Intent intent = new Intent(context, UpgradeNotificationClickReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }

        //刷新进度
        builder.setProgress(100, progress, false);
        //时间
        builder.setWhen(System.currentTimeMillis());
        //内容
        builder.setContentText(content);

        //通知刷新
        if (manager != null)
            manager.notify(manageId, builder.build());

    }

    /**
     * 显示通知
     *
     * @param context     上下文
     * @param title       标题
     * @param content     内容
     * @param manageId    管理id 不同消息需要不同id
     * @param channelId   渠道id 通知管理类别
     * @param channelName 渠道名 通知管理类别
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void showNotification(Context context, String title, String content,
                                        int manageId, String channelId, String channelName,
                                        boolean isEnableLights, boolean isEnableVibration,
                                        boolean isEnableSound) {
        builder = getNotificationBuilder(context, title, content, channelId, channelName, 0,
                isEnableLights, isEnableVibration, isEnableSound);
        builder.setAutoCancel(true);//点击后消失
        Notification notification = builder.build();
        //通知刷新
        if (manager != null)
            manager.notify(manageId, notification);
    }

    /**
     * 显示通知
     *
     * @param context       上下文
     * @param title         标题
     * @param content       内容
     * @param manageId      管理id 不同消息需要不同id
     * @param channelId     渠道id 通知管理类别
     * @param channelName   渠道名 通知管理类别
     * @param pendingIntent 点击事件
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void showNotification(Context context, String title, String content,
                                        int manageId, String channelId, String channelName, int iconResId,
                                        boolean isEnableLights, boolean isEnableVibration,
                                        boolean isEnableSound, PendingIntent pendingIntent) {
        builder = getNotificationBuilder(context, title, content, channelId, channelName, iconResId,
                isEnableLights, isEnableVibration, isEnableSound);

        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        builder.setAutoCancel(true);//点击后消失
        Notification notification = builder.build();
        //通知刷新
        if (manager != null)
            manager.notify(manageId, notification);
    }

    /**
     * 取消管理
     *
     * @param manageId
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void cancelNotification(int manageId) {
        if (manager != null)
            manager.cancel(manageId);
    }

    /**
     * 显示通知权限对话框
     */
    public static void showNotifyPermissionDialog(Context context) {
         XPromptDialog.Builder.create((AppCompatActivity) context)
                .title("提示")
                .content("请在“通知”中打开通知权限")
                .textPositive("去设置")
                .textNegative(com.ara.base.R.string.cancel)
                .onPositive(dialog -> {
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package", context.getPackageName());
                        intent.putExtra("app_uid", context.getApplicationInfo().uid);
                    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                    } else if (Build.VERSION.SDK_INT >= 19) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                    }
                    context.startActivity(intent);

                })
                .show();
    }

    // 获取系统默认铃声的Uri
    private static Uri getSystemDefaultRingtoneUri(Context context) {
        return RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE);
    }
}
