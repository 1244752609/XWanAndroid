package com.ara.common.system;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by XieXin on 2018/12/10.
 * 屏幕工具类
 */
public class ScreenUtils {
    private ScreenUtils() {
    }

    /**
     * 截屏
     *
     * @param view    需要截图控件
     * @param context 上下文
     */
    public static Bitmap screenshot(View view, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        ToastUtils.showLong(context, "截屏成功");
        return bitmap;
    }

    /**
     * 利用反射获取状态栏高度（单位：px）
     *
     * @param context 上下文
     * @return 高度px
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 利用反射获取导航栏高度（单位：px）
     *
     * @param context 上下文
     * @return 高度px
     */
    public int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);

    }

    /**
     * 获取屏幕的真实宽度（单位：px）
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    public static int getScreenRealWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getRealMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的真实高度（单位：px）
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenRealHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getRealMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕密度DPI
     *
     * @param context 上下文
     * @return 屏幕密度DPI
     */
    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 判断屏幕方向
     *
     * @param context 上下文
     * @return 横屏-true 竖屏-false
     */
    public static boolean isScreenOrientation(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            return true;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            return false;
        }
        return false;
    }
}
