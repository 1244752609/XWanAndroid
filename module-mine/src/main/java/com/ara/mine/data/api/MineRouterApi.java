package com.ara.mine.data.api;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XieXin on 2022/2/18.
 * 路由API
 */
public class MineRouterApi {
    /*** 设置页 */
    public static final String API_SETTINGS = "/mine/settings";
    /*** 积分排行榜 */
    public static final String API_INTEGRAL_RANK = "/mine/IntegralRank";
    /*** 我的积分 */
    public static final String API_MY_INTEGRAL = "/mine/MyIntegral";
    /*** 我的收藏 */
    public static final String API_MY_COLLECT = "/mine/MyCollect";
    /*** 我的分享 */
    public static final String API_MY_SHARE = "/mine/MyShare";
    /*** 语言 */
    public static final String API_LANGUAGE = "/mine/Language";

    /**
     * 跳转到设置页
     */
    public static void jumpSettingsActivity() {
        ARouter.getInstance()
                .build(API_SETTINGS)
                .navigation();
    }

    /**
     * 跳转到积分排行榜
     */
    public static void jumpIntegralRankActivity() {
        ARouter.getInstance()
                .build(API_INTEGRAL_RANK)
                .navigation();
    }

    /**
     * 跳转到我的积分
     */
    public static void jumpMyIntegralActivity() {
        ARouter.getInstance()
                .build(API_MY_INTEGRAL)
                .navigation();
    }

    /**
     * 跳转到我的收藏
     */
    public static void jumpMyCollectActivity() {
        ARouter.getInstance()
                .build(API_MY_COLLECT)
                .navigation();
    }

    /**
     * 跳转到我的分享
     */
    public static void jumpMyShareActivity() {
        ARouter.getInstance()
                .build(API_MY_SHARE)
                .navigation();
    }

    /**
     * 跳转到语言
     */
    public static void jumpLanguageActivity() {
        ARouter.getInstance()
                .build(API_LANGUAGE)
                .navigation();
    }
}
