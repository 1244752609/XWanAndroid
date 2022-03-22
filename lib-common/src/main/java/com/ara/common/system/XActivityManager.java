package com.ara.common.system;

import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by XieXin on 2020/9/23.
 * Activity管理工具
 */
public class XActivityManager {
    private static String TAG = XActivityManager.class.getSimpleName();
    private static Stack<WeakReference<AppCompatActivity>> mActivityStack;

    private XActivityManager() {
    }

    /**
     * 添加Activity到栈
     *
     * @param activity AppCompatActivity
     */
    public static void addActivity(AppCompatActivity activity) {
        if (mActivityStack == null) mActivityStack = new Stack<>();
        mActivityStack.add(new WeakReference<>(activity));
        release();
    }

    /**
     * 删除栈中activity
     */
    public static void remove(AppCompatActivity activity) {
        if (mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<AppCompatActivity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<AppCompatActivity> activityReference = it.next();
                AppCompatActivity temp = activityReference.get();
                if (temp == null) it.remove();
                if (temp == activity) it.remove();
            }
        }
        release();
    }

    /**
     * 检查弱引用是否释放，若释放，则从栈中清理掉该元素
     */
    public static void release() {
        if (mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<AppCompatActivity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<AppCompatActivity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) it.remove();
            }
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     *
     * @return AppCompatActivity
     */
    public static AppCompatActivity currentActivity() {
        release();
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            return mActivityStack.lastElement().get();
        }
        return null;
    }

    /**
     * 关闭当前Activity（栈中最后一个压入的）
     */
    public static void finishActivity() {
        AppCompatActivity activity = currentActivity();
        if (activity != null) finishActivity(activity);
        release();
    }

    /**
     * 关闭指定的Activity
     *
     * @param activity
     */
    public static void finishActivity(AppCompatActivity activity) {
        if (activity != null && mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<AppCompatActivity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<AppCompatActivity> activityReference = it.next();
                AppCompatActivity temp = activityReference.get();
                // 清理掉已经释放的activity
                if (temp == null) {
                    it.remove();
                    continue;
                }
                if (temp == activity) it.remove();
            }
            activity.finish();
        }
    }

    /**
     * 关闭指定类名的所有Activity
     *
     * @param cls Activity类
     */
    public static void finishActivity(Class<?> cls) {
        if (mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<AppCompatActivity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<AppCompatActivity> activityReference = it.next();
                AppCompatActivity activity = activityReference.get();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (mActivityStack != null) {
            for (WeakReference<AppCompatActivity> activityReference : mActivityStack) {
                AppCompatActivity activity = activityReference.get();
                if (activity != null) activity.finish();
            }
            mActivityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public static void exitApp() {
        try {
            finishAllActivity();
            // 退出JVM,释放所占内存资源,0表示正常退出
            System.exit(0);
            // 从系统中kill掉应用程序
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

}
