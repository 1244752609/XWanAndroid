package com.ara.wanandroid.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class AppRouterApi {
    /*** 主页 */
    public static final String API_MAIN = "/main/Main";

    /**
     * 跳转到主页
     */
    public static void jumpMainActivity() {
        ARouter.getInstance()
                .build(API_MAIN)
                .navigation();
    }
}
