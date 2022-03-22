package com.ara.common.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by XieXin on 2018/12/10.
 * Json工具类
 */
public final class GsonUtils {
    private static final Gson gson = new Gson();

    private GsonUtils() {
    }

    /**
     * Object转json
     *
     * @param obj Object
     */
    public static String toString(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * json转成Bean
     *
     * @param jsonString json字符串
     * @param clazz      Bean.class
     */
    public static <T> T toBean(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    /**
     * json转成集合
     *
     * @param jsonString json字符串
     * @param type       数据类型, 类型变量和基本类型。
     */
    public static <T> T toObject(String jsonString, Type type) {
        return gson.fromJson(jsonString, type);
    }

    /**
     * json转成List
     *
     * @param jsonString json字符串
     * @param type       Bean.class
     */
    public static <T> List<T> toList(String jsonString, Type type) {
        return gson.fromJson(jsonString, getListType(type));
    }

    /**
     * json转成List<Map<String,T>>
     *
     * @param jsonString json字符串
     * @param type       Bean.class
     */
    public static <T> List<Map<String, T>> toListMaps(String jsonString, Type type) {
        return gson.fromJson(jsonString, getListType(getMapType(String.class, type)));
    }

    /**
     * json转成Map<String,T>
     *
     * @param jsonString json字符串
     * @param type       Bean.class
     */
    public static <T> Map<String, T> toMap(String jsonString, Type type) {
        return gson.fromJson(jsonString, getMapType(String.class, type));
    }

    /**
     * 判断JsonString是否有效
     *
     * @param jsonString json字符串
     * @return true有效 false无效
     */
    public static boolean isJson(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) return false;
        try {
            gson.fromJson(jsonString, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    // ====================== Type ==================================================

    /**
     * 获取 Array Type
     *
     * @param type Bean.class
     * @return Bean[] Type
     */
    public static Type getArrayType(final Type type) {
        return TypeToken.getArray(type).getType();
    }

    /**
     * 获取 List Type
     *
     * @param type Bean.class
     * @return List<Bean> Type
     */
    public static Type getListType(final Type type) {
        return TypeToken.getParameterized(List.class, type).getType();
    }

    /**
     * 获取 Set Type
     *
     * @param type Bean.class
     * @return Set<Bean> Type
     */
    public static Type getSetType(final Type type) {
        return TypeToken.getParameterized(Set.class, type).getType();
    }

    /**
     * 获取 Map Type
     *
     * @param keyType   Key.class
     * @param valueType Value.class
     * @return Map<Bean> Type
     */
    public static Type getMapType(final Type keyType, final Type valueType) {
        return TypeToken.getParameterized(Map.class, keyType, valueType).getType();
    }

    /**
     * 获取 Type
     *
     * @param rawType       raw type
     * @param typeArguments type arguments
     * @return Type
     */
    public static Type getType(final Type rawType, final Type... typeArguments) {
        return TypeToken.getParameterized(rawType, typeArguments).getType();
    }
}
