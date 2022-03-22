package com.ara.project.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class ProjectRouterApi {
    /*** 项目 */
    public static final String API_PROJECT = "/project/project";

    /**
     * 跳转到Project页
     */
    public static void jumpProjectActivity() {
        ARouter.getInstance()
                .build(API_PROJECT)
                .navigation();
    }
}
