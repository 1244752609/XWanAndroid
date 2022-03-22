package com.ara.module_copy.data.api;

import com.ara.network.bean.BaseBean;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by XieXin on 2022/2/24.
 * Copy API
 */
public interface CopyService {
    /**
     * copy
     */
    @GET("copy/copy")
    Flowable<BaseBean<Object>> copy(@Query("name") String name);
}
