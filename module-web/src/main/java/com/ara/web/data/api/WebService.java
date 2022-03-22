package com.ara.web.data.api;

import com.ara.network.bean.BaseBean;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by XieXin on 2022/2/24.
 * Web API
 */
public interface WebService {
    /**
     * web
     */
    @GET("web/web")
    Flowable<BaseBean<Object>> web(@Query("name") String name);
}
