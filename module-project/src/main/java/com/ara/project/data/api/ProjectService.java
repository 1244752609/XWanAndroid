package com.ara.project.data.api;

import com.ara.network.bean.BaseBean;
import com.ara.project.data.bean.ProjectClassifyBean;
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
 * Project API
 */
public interface ProjectService {

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    Flowable<BaseBean<List<ProjectClassifyBean>>> getProjectType();

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("project/list/{page}/json")
    Flowable<BaseBean<PagingBean<ArticleBean>>> getListProject(@Path("page") int page);

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("project/list/{page}/json")
    Flowable<BaseBean<PagingBean<ArticleBean>>> getListProject(@Path("page") int page, @Query("cid") String id);

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
