package com.ara.common.util;

import android.text.TextUtils;

import androidx.annotation.NonNull;

/**
 * Created by XieXin on 2018/12/10.
 * 布尔类型 工具类
 */
public final class BooleanUtils {

    private BooleanUtils() {
    }

    /**
     * 整型转布尔类型
     *
     * @param i
     * @return
     */
    public static boolean toBoolean(int i) {
        return i == 1;
    }

    /**
     * 字符串转布尔类型
     *
     * @param str
     * @return
     */
    public static boolean toBoolean(@NonNull String str) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim())) {
            return false;
        }
        return toBoolean(Integer.parseInt(str));
    }

    /**
     * 布尔类型转整型
     *
     * @param b
     * @return
     */
    public static int toInt(boolean b) {
        return b ? 1 : 0;
    }

    /**
     * 布尔类型转字符串
     *
     * @param b
     * @return
     */
    public static String toString(boolean b) {
        return b ? "1" : "0";
    }
}
