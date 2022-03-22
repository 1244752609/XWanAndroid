package com.ara.mine.data.api;

import com.ara.mine.data.bean.IntegralBean;
import com.ara.mine.data.bean.ShareBean;
import com.ara.mine.data.bean.UserInfoBean;
import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.bean.PagingBean;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by XieXin on 2022/2/24.
 * API
 */
public interface MineService {
    /**
     * 获取用户信息
     */
    @GET("user/lg/userinfo/json")
    Flowable<BaseBean<UserInfoBean>> getUserInfo();

    /**
     * 获取个人积分列表
     */
    @GET("lg/coin/list/{page}/json")
    Flowable<BaseBean<PagingBean<IntegralBean>>> getIntegralList(@Path("page") int page);

    /**
     * 积分排行榜
     */
    @GET("coin/rank/{page}/json")
    Flowable<BaseBean<PagingBean<IntegralBean>>> getRank(@Path("page") int page);

    /**
     * 收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    Flowable<BaseBean<PagingBean<ArticleBean>>> getCollectList(@Path("page") int page);

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    Flowable<BaseBean<Object>> collect(@Path("id") String id);

    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    Flowable<BaseBean<Object>> unCollect(@Path("id") String id);

    /**
     * 分享列表
     */
    @GET("user/lg/private_articles/{page}/json")
    Flowable<BaseBean<ShareBean>> getShareList(@Path("page") int page);

    /**
     * 分享文章
     */
    @POST("lg/user_article/add/json")
    Flowable<BaseBean<Object>> shareArticle(@Query("title") String title, @Query("link") String link);

    /**
     * 删除文章
     */
    @POST("lg/user_article/delete/{id}/json")
    Flowable<BaseBean<Object>> deleteArticle(@Path("id") String id);

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    Flowable<BaseBean<Object>> logout();

}
