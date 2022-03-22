package com.ara.module_public.data.api;

import com.ara.module_public.data.bean.PublicAuthorBean;
import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.bean.PagingBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by XieXin on 2022/2/24.
 * 公众号 API
 */
public interface PublicService {

    /**
     * 项目分类
     */
    @GET("wxarticle/chapters/json")
    Flowable<BaseBean<List<PublicAuthorBean>>> getAuthor();

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    Flowable<BaseBean<PagingBean<ArticleBean>>> getListPublic(@Path("page") int page);

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    Flowable<BaseBean<PagingBean<ArticleBean>>> getListPublic(@Path("page") int page, @Query("cid") String id);

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
