package com.ara.wanandroid.data.api;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by XieXin on 2022/2/17.
 * App服务器接口Api
 */
public interface AppService {
    @POST("test/test")
    Call<String> test();
}
