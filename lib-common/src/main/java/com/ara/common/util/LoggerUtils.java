package com.ara.common.util;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

/**
 * Created by XieXin on 2020/6/1.
 * 日志打印工具
 */
public class LoggerUtils {
    private LoggerUtils() {
    }

    /**
     * Given tag will be used as tag only once for this method call regardless of the tag that's been
     * set during initialization. After this invocation, the general tag that's been set will
     * be used for the subsequent log calls
     */
    public static Printer t(String tag) {
        return Logger.t(tag);
    }

    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, String tag, String message, Throwable throwable) {
        Logger.log(priority, tag, message, throwable);
    }

    /**
     * Debug
     * @param message 消息
     * @param args 参数
     */
    public static void d(String message, Object... args) {
        if (message == null) message = "null";
        Logger.d(message, args);
    }

    /**
     * Debug
     * @param object 对象
     */
    public static void d(Object object) {
        Logger.d(object);
    }

    /**
     * Error
     * @param message 消息
     * @param args 参数
     */
    public static void e(String message, Object... args) {
        if (message == null) message = "null";
        Logger.e(null, message, args);
    }

    /**
     * Error
     * @param throwable 异常
     * @param message 消息
     * @param args 参数
     */
    public static void e(Throwable throwable, String message, Object... args) {
        if (message == null) message = "null";
        Logger.e(throwable, message, args);
    }

    /**
     * Info
     * @param message 消息
     * @param args 参数
     */
    public static void i(String message, Object... args) {
        if (message == null) message = "null";
        Logger.i(message, args);
    }

    /**
     * Verbose
     * @param message 消息
     * @param args 参数
     */
    public static void v(String message, Object... args) {
        if (message == null) message = "null";
        Logger.v(message, args);
    }

    /**
     * Warn
     * @param message 消息
     * @param args 参数
     */
    public static void w(String message, Object... args) {
        if (message == null) message = "null";
        Logger.w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(String message, Object... args) {
        if (message == null) message = "null";
        Logger.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(String json) {
        Logger.json(json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }
}
