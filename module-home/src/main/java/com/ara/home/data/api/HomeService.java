package com.ara.home.data.api;

import com.ara.project_common.data.bean.ArticleBean;
import com.ara.home.data.bean.BannerBean;
import com.ara.home.data.bean.HotBean;
import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.PagingBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by XieXin on 2022/2/17.
 * App服务器接口Api
 */
public interface HomeService {
    /**
     * 获取轮播图
     */
    @GET("banner/json")
    Flowable<BaseBean<List<BannerBean>>> getBanner();

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    Flowable<BaseBean<PagingBean<ArticleBean>>> getListArticle(@Path("page") int page);

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
     * 获取轮播图
     */
    @GET("hotkey/json")
    Flowable<BaseBean<List<HotBean>>> getHotSearch();

    /**
     * 获取搜索内容
     *
     * @param page 页码，拼接在连接中，从0开始。
     */
    @POST("article/query/{page}/json")
    Flowable<BaseBean<PagingBean<ArticleBean>>> getListArticle(@Path("page") int page, @Query("k") String keyword);
}
