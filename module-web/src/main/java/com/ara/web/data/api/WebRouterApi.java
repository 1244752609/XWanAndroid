package com.ara.web.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class WebRouterApi {
    /*** Web */
    public static final String API_WEB = "/web/web";

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
