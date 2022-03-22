package com.ara.square.data.api;

import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.bean.PagingBean;
import com.ara.square.data.bean.NavigationBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by XieXin on 2022/2/24.
 * Copy API
 */
public interface SquareService {
    /**
     * 获取体系
     */
    @GET("tree/json")
    Flowable<BaseBean<List<NavigationBean>>> getSystem();

    /**
     * 获取导航
     */
    @GET("navi/json")
    Flowable<BaseBean<List<NavigationBean>>> getNavigation();

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    Flowable<BaseBean<PagingBean<ArticleBean>>> getListArticle(@Path("page") int page, @Query("cid") String id);

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
}
