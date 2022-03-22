package com.ara.project_common.data.api;

import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.UpgradeBean;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by XieXin on 2017-10-24.
 * 升级API
 */

public interface UpgradeService {
    /**
     * 下载文件
     *
     * @return Flowable<ResponseBody>
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     * 获取版本信息
     */
    @GET
    Flowable<BaseBean<UpgradeBean>> getVersion(@Url String url);
}
