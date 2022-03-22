package com.ara.home.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class HomeRouterApi {
    /*** 主页 */
    public static final String API_HOME = "/home/home";
    /*** 搜索页 */
    public static final String API_SEARCH = "/home/search";
    /*** 搜索列表页 */
    public static final String API_SEARCH_LIST = "/home/searchList";

    /**
     * 跳转到主页
     */
    public static void jumpHomeActivity() {
        ARouter.getInstance()
                .build(API_HOME)
                .navigation();
    }

    /**
     * 跳转到搜索页
     */
    public static void jumpSearchActivity() {
        ARouter.getInstance()
                .build(API_SEARCH)
                .navigation();
    }

    /**
     * 跳转到搜索列表页
     */
    public static void jumpSearchListActivity(String keyword) {
        ARouter.getInstance()
                .build(API_SEARCH_LIST)
                .withString("keyword", keyword)
                .navigation();
    }
}
