package com.ara.account.data.api;

import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.AccountBean;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by XieXin on 2022/2/17.
 * App服务器接口Api
 */
public interface AccountService {
    @FormUrlEncoded
    @POST("user/login")
    Flowable<BaseBean<AccountBean>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Flowable<BaseBean<AccountBean>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @GET("user/logout/json")
    Flowable<BaseBean<Object>> logout();
}
