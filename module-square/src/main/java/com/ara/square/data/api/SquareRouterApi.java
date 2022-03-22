package com.ara.square.data.api;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ara.square.data.bean.ChildrenBean;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class SquareRouterApi {
    /*** 广场页 */
    public static final String API_SQUARE = "/square/square";
    /*** 广场页 */
    public static final String API_SQUARE_LIST = "/square/squareList";

    /**
     * 跳转到广场页
     */
    public static void jumpSquareActivity() {
        ARouter.getInstance()
                .build(API_SQUARE)
                .navigation();
    }

    /**
     * 跳转到广场列表页
     */
    public static void jumpSquareListActivity(ChildrenBean bean) {
        ARouter.getInstance()
                .build(API_SQUARE_LIST)
                .withObject("children", bean)
                .navigation();
    }
}
