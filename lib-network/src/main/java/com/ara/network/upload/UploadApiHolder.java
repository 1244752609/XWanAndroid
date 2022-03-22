package com.ara.network.upload;

import com.ara.network.RetrofitHolder;

import okhttp3.MediaType;

/**
 * Created by XieXin on 2019/1/25.
 * Api持有人
 */
public class UploadApiHolder {
    private static UploadApi uploadApi;
    private static MediaType mediaType;

    /**
     * API
     */
    public static UploadApi getUploadApi() {
        if (uploadApi == null) uploadApi = RetrofitHolder.getInstance().create(UploadApi.class);
        return uploadApi;
    }

    /**
     * API
     */
    public static UploadApi getUploadApi(int timeOut) {
        if (uploadApi == null)
            uploadApi = RetrofitHolder.getInstance(timeOut).create(UploadApi.class);
        return uploadApi;
    }

    public static MediaType getMultipartFormData() {
        if (mediaType == null) mediaType = MediaType.parse("multipart/form-data");
        return mediaType;
    }
}
