package com.ara.common.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by XieXin on 2018/12/10.
 * Toast工具类
 */
@SuppressLint("ShowToast")
public class ToastUtils {
    private static boolean isShow = true;//默认显示

    private ToastUtils() {
    }

    /**
     * 设置是否显示
     *
     * @param isShow 是否显示 true是
     */
    public static void setShow(boolean isShow) {
        ToastUtils.isShow = isShow;
    }

    /**
     * 短时间显示Toast
     *
     * @param context Context
     * @param text    CharSequence
     */
    public static void showShort(Context context, CharSequence text) {
        if (isShow) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context Context
     * @param resId   resId
     */
    public static void showShort(Context context, int resId) {
        if (isShow) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context Context
     * @param text    CharSequence
     * @param gravity 位置
     */
    public static void showShort(Context context, CharSequence text, int gravity) {
        if (isShow) {
            Toast viewToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            viewToast.setGravity(gravity, 0, 0);
            viewToast.show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context Context
     * @param resId   resId
     * @param gravity 位置
     */
    public static void showShort(Context context, int resId, int gravity) {
        if (isShow) {
            Toast viewToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            viewToast.setGravity(gravity, 0, 0);
            viewToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context Context
     * @param text    CharSequence
     */
    public static void showLong(Context context, CharSequence text) {
        if (isShow) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context Context
     * @param resId   resId
     */
    public static void showLong(Context context, int resId) {
        if (isShow) {
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context Context
     * @param text    CharSequence
     * @param gravity 位置
     */
    public static void showLong(Context context, CharSequence text, int gravity) {
        if (isShow) {
            Toast viewToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            viewToast.setGravity(gravity, 0, 0);
            viewToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context Context
     * @param resId   resId
     * @param gravity 位置
     */
    public static void showLong(Context context, int resId, int gravity) {
        if (isShow) {
            Toast viewToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            viewToast.setGravity(gravity, 0, 0);
            viewToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  Context
     * @param text     CharSequence
     * @param duration 单位:毫秒
     */
    public static void show(Context context, CharSequence text, int duration) {
        if (isShow) {
            Toast.makeText(context, text, duration).show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  Context
     * @param resId    int
     * @param duration 单位:毫秒
     */
    public static void show(Context context, int resId, int duration) {
        if (isShow) {
            Toast.makeText(context, resId, duration).show();
        }
    }

    /**
     * 自定义Toast的View
     *
     * @param context  Context
     * @param duration 单位:毫秒
     * @param view     View
     */
    public static void showToastView(Context context, int duration, View view) {
        if (isShow) {
            Toast viewToast = Toast.makeText(context, "", duration);
            viewToast.setView(view);
            viewToast.show();
        }
    }

    /**
     * 自定义Toast的View
     *
     * @param context  Context
     * @param duration 单位:毫秒
     * @param view     View
     * @param gravity  权重
     * @param xOffset  偏移量
     * @param yOffset  偏移量
     */
    public static void showToastView(Context context, int duration, View view, int gravity, int xOffset, int yOffset) {
        if (isShow) {
            Toast viewToast = Toast.makeText(context, "", duration);
            viewToast.setView(view);
            viewToast.setGravity(gravity, xOffset, yOffset);
            viewToast.show();
        }
    }

    /**
     * 自定义Toast的位置
     *
     * @param context  Context
     * @param text     CharSequence
     * @param duration 单位:毫秒
     * @param gravity  权重
     * @param xOffset  偏移量
     * @param yOffset  偏移量
     */
    public static void showToastGravity(Context context, CharSequence text, int duration, int gravity, int xOffset, int yOffset) {
        if (isShow) {
            Toast textToast = Toast.makeText(context, text, duration);
            textToast.setText(text);
            textToast.setGravity(gravity, xOffset, yOffset);
            textToast.show();
        }
    }

    /**
     * 自定义Toast的位置
     *
     * @param context  Context
     * @param resId    int
     * @param duration 单位:毫秒
     * @param gravity  权重
     * @param xOffset  偏移量
     * @param yOffset  偏移量
     */
    public static void showToastGravity(Context context, int resId, int duration, int gravity, int xOffset, int yOffset) {
        if (isShow) {
            Toast textToast = Toast.makeText(context, resId, duration);
            textToast.setText(resId);
            textToast.setGravity(gravity, xOffset, yOffset);
            textToast.show();
        }
    }
}
