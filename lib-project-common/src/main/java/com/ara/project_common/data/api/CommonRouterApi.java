package com.ara.project_common.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class CommonRouterApi {
    /*** 主页 */
    public static final String API_MAIN = "/main/Main";

    /*** 登录 */
    public static final String API_LOGIN = "/account/login";

    /*** Web */
    public static final String API_WEB = "/web/web";

    /**
     * 跳转到主页
     */
    public static void jumpMainActivity() {
        ARouter.getInstance()
                .build(API_MAIN)
                .navigation();
    }

    /**
     * 跳转到登录页
     */
    public static void jumpLoginActivity() {
        ARouter.getInstance()
                .build(API_LOGIN)
                .withBoolean("isReLogin", false)
                .navigation();
    }

    /**
     * 跳转到登录页
     */
    public static void jumpReLoginActivity() {
        ARouter.getInstance()
                .build(API_LOGIN)
                .withBoolean("isReLogin", true)
                .navigation();
    }

    /**
     * 跳转到Web
     */
    public static void jumpWebActivity(String url) {
        ARouter.getInstance()
                .build(API_WEB)
                .withString("url", url)
                .navigation();
    }
}
