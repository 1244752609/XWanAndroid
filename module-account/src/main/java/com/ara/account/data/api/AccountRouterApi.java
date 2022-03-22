package com.ara.account.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class AccountRouterApi {
    /*** 登录 */
    public static final String API_LOGIN = "/account/login";
    /*** 注册 */
    public static final String API_REGISTER = "/account/register";

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
     * 跳转到注册页
     */
    public static void jumpRegisterActivity() {
        ARouter.getInstance()
                .build(API_REGISTER)
                .navigation();
    }
}
