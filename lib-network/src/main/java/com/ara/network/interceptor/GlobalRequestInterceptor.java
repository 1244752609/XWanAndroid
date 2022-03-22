package com.ara.network.interceptor;

import android.text.TextUtils;

import com.ara.common.util.MMKVUtils;
import com.ara.network.RetrofitHolder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by XieXin on 2019/1/17.
 * 全局请求处理
 */
public class GlobalRequestInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        String token = MMKVUtils.decodeString(RetrofitHolder.TOKEN_KEY);
        if (TextUtils.isEmpty(token)) {
            return chain.proceed(chain.request());
        } else {
            Request oldRequest = chain.request();
            Request request = oldRequest.newBuilder()
                    .header("token", token)
                    .method(oldRequest.method(), oldRequest.body())
                    .build();
            return chain.proceed(request);
        }
    }
}
