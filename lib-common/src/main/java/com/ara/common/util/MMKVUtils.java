package com.ara.common.util;

import com.tencent.mmkv.MMKV;

import java.util.Map;

/**
 * Created by XieXin on 2020/6/7.
 * MMKV数据存储工具类
 */
public class MMKVUtils {
    private final static MMKV mmkv = MMKV.defaultMMKV();

    private MMKVUtils() {
    }

    /**
     * 获取MMKV
     * @return MMKV
     */
    public static MMKV getMmkv() {
        return mmkv;
    }

    /**
     * 保存数据，根据类型调用不同的保存方法
     *
     * @param key Key
     * @param obj Object
     */
    public static void encode(String key, Object obj) {
        if (obj == null) return;
        if (obj instanceof String) {
            mmkv.encode(key, (String) obj);
        } else if (obj instanceof Integer) {
            mmkv.encode(key, (Integer) obj);
        } else if (obj instanceof Boolean) {
            mmkv.encode(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            mmkv.encode(key, (Float) obj);
        } else if (obj instanceof Long) {
            mmkv.encode(key, (Long) obj);
        } else {
            mmkv.encode(key, obj.toString());
        }
    }

    /**
     * 获取保存String数据
     *
     * @param key
     * @return String
     */
    public static String decodeString(String key) {
        return mmkv.decodeString(key, "");
    }

    /**
     * 获取保存String数据
     *
     * @param key
     * @param defaultString
     * @return
     */
    public static String decodeString(String key, String defaultString) {
        return mmkv.decodeString(key, defaultString);
    }

    /**
     * 获取保存Integer数据
     *
     * @param key
     * @return int
     */
    public static int decodeInt(String key) {
        return mmkv.decodeInt(key, -1);
    }

    /**
     * 获取保存Integer数据
     *
     * @param key
     * @param defaultInt
     * @return
     */
    public static int decodeInt(String key, Integer defaultInt) {
        return mmkv.decodeInt(key, defaultInt);
    }

    /**
     * 获取保存Long数据
     *
     * @param key
     * @return long
     */
    public static long decodeLong(String key) {
        return mmkv.decodeLong(key, -1L);
    }

    /**
     * 获取保存Long数据
     *
     * @param key
     * @param defaultLong
     * @return
     */
    public static long decodeLong(String key, Long defaultLong) {
        return mmkv.decodeLong(key, defaultLong);
    }

    /**
     * 获取保存Float数据
     *
     * @param key
     * @return float
     */
    public static float decodeFloat(String key) {
        return mmkv.decodeFloat(key, -1f);
    }

    /**
     * 获取保存Float数据
     *
     * @param key
     * @param defaultFloat
     * @return
     */
    public static float decodeFloat(String key, Float defaultFloat) {
        return mmkv.decodeFloat(key, defaultFloat);
    }

    /**
     * 获取保存Boolean数据
     *
     * @param key
     * @return boolean
     */
    public static boolean decodeBoolean(String key) {
        return mmkv.decodeBool(key, false);
    }

    /**
     * 获取保存Boolean数据
     *
     * @param key
     * @param defaultBoolean
     * @return
     */
    public static boolean decodeBoolean(String key, Boolean defaultBoolean) {
        return mmkv.decodeBool(key, defaultBoolean);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key Key
     */
    public static void remove(String key) {
        mmkv.removeValueForKey(key);
    }

    /**
     * 清除所有的数据
     */
    public static void clear() {
        mmkv.clearAll();
    }

    /**
     * 查询某个key是否存在
     *
     * @param key Key
     * @return 包含这个key返回true，否则返回false
     */
    public static boolean contains(String key) {
        return mmkv.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return Map
     */
    public static Map<String, ?> getAll() {
        return mmkv.getAll();
    }
}
