package com.ara.network.upload;


import com.ara.network.bean.BaseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Created by XieXin on 2021/5/21.
 * 上传 API
 * RequestBody body = RequestBody.create(file, MediaType.parse("multipart/form-data"));
 * MultipartBody.Part part = MultipartBody.Part.createFormData("file", filename, body);
 */
public interface UploadApi {
    /**
     * 上传单张图片
     * RequestBody body = RequestBody.create(file, MediaType.parse("multipart/form-data"));
     * MultipartBody.Part part = MultipartBody.Part.createFormData("file", filename, body);
     *
     * @param url  上传连接
     * @param part Part
     * @return BaseBean<String>
     */
    @Multipart
    @POST()
    Flowable<BaseBean<String>> upload(@Url String url,
                                      @Part MultipartBody.Part part);

    /**
     * 上传多张图片
     * MediaType mediaType = MediaType.parse("multipart/form-data");
     * RequestBody body1 = RequestBody.create(file1, mediaType);
     * RequestBody body2 = RequestBody.create(file2, mediaType);
     * Map<String, RequestBody> partMap = new HashMap<>();
     * partMap.put("name=\"file1\"; filename=\"" + filename1 + "\"", body1);
     * partMap.put("name=\"file2\"; filename=\"" + filename2 + "\"", body2);
     *
     * @param url     上传连接
     * @param partMap Part
     * @return BaseBean<List < String>>
     */
    @Multipart
    @POST()
    Flowable<BaseBean<List<String>>> uploads(@Url String url,
                                             @PartMap Map<String, RequestBody> partMap);
}
