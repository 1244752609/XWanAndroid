package com.ara.module_copy.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class CopyRouterApi {
    /*** 主页 */
    public static final String API_COPY_MAIN = "/copy/Main";

    /**
     * 跳转到Copy主页
     */
    public static void jumpCopyMainActivity() {
        ARouter.getInstance()
                .build(API_COPY_MAIN)
                .navigation();
    }
}
