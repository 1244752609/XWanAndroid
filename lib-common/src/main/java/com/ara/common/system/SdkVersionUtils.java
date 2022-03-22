package com.ara.common.system;

import android.os.Build;

import com.ara.common.util.LoggerUtils;

/**
 * Created by XieXin on 2020/1/3.
 * Sdk版本判断
 */
public class SdkVersionUtils {
    /**
     * 判断是否是Android R版本
     *
     * @return true大于等于R 30
     */
    public static boolean hasR() {
        LoggerUtils.i("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + "\n" + "Build.VERSION_CODES: " + Build.VERSION_CODES.Q);
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }
    /**
     * 判断是否是Android Q版本
     *
     * @return true大于等于Q 29
     */
    public static boolean hasQ() {
        LoggerUtils.i("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + "\n" + "Build.VERSION_CODES: " + Build.VERSION_CODES.Q);
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * 判断是否是Android P版本
     *
     * @return true大于等于P 28
     */
    public static boolean hasP() {
        LoggerUtils.i("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + "\n" + "Build.VERSION_CODES: " + Build.VERSION_CODES.Q);
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 判断是否是Android O版本
     *
     * @return true大于等于O 26
     */
    public static boolean hasO() {
        LoggerUtils.i("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + "\n" + "Build.VERSION_CODES: " + Build.VERSION_CODES.O);
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 判断是否是Android M版本
     *
     * @return true大于等于M 24
     */
    public static boolean hasN() {
        LoggerUtils.i("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + "\n" + "Build.VERSION_CODES: " + Build.VERSION_CODES.N);
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 判断是否是Android Q版本
     *
     * @return true大于等于KITKAT 19
     */
    public static boolean hasKitKat() {
        LoggerUtils.i("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + "\n" + "Build.VERSION_CODES: " + Build.VERSION_CODES.KITKAT);
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
