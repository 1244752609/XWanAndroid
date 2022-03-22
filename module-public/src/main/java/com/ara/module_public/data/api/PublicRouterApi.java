package com.ara.module_public.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class PublicRouterApi {
    /*** 公众号 */
    public static final String API_PUBLIC = "/public/public";

    /**
     * 跳转到公众号页
     */
    public static void jumpPublicActivity() {
        ARouter.getInstance()
                .build(API_PUBLIC)
                .navigation();
    }
}
