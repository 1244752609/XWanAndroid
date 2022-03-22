package com.ara.base.data.response;

import com.ara.network.exception.ExceptionHandle;

/**
 * 专用于数据层返回结果给 domain 层或 ViewModel 用，原因如下：
 * <p>
 * liveData 是专用于页面开发的、用于解决生命周期安全问题的组件，
 * 有时候数据并非一定是通过 liveData 来分发给页面，也可能是通过别的组件去通知给非页面的东西，
 * 这时候 repo 方法中内定通过 liveData 分发就不太合适，不如一开始就规定不在数据层通过 liveData 返回结果。
 * <p>
 * 如果这样说还不理解的话，详见《如何让同事爱上架构模式、少写 bug 多注释》的解析
 * https://xiaozhuanlan.com/topic/8204519736
 * <p>
 * Created by XieXin on 2022/2/15.
 */
public class DataResult<T> {
    private final T entity;
    private final boolean success;
    private final ResultSource source;
    private final ExceptionHandle.ResponseThrowable error;

    public static <T> DataResult<T> create(T entity) {
        return new DataResult<>(entity, true, null, ResultSource.NETWORK);
    }

    public static <T> DataResult<T> create(T entity, boolean success) {
        return new DataResult<>(entity, success, null, ResultSource.NETWORK);
    }

    public static <T> DataResult<T> create(T entity, ResultSource source) {
        return new DataResult<>(entity, true, null, source);
    }

    public static <T> DataResult<T> create(T entity, boolean success, ResultSource source) {
        return new DataResult<>(entity, success, null, source);
    }

    public static <T> DataResult<T> create(T entity, boolean success, ExceptionHandle.ResponseThrowable error, ResultSource source) {
        return new DataResult<>(entity, success, error, source);
    }

    /*** 创建失败返回数据 */
    public static <T> DataResult<T> createFail(ExceptionHandle.ResponseThrowable error) {
        return new DataResult<>(null, false, error, ResultSource.NETWORK);
    }

    private DataResult(T entity, boolean success, ExceptionHandle.ResponseThrowable error, ResultSource source) {
        this.entity = entity;
        this.success = success;
        this.error = error;
        this.source = source;
    }

    public T getResult() {
        return entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public ExceptionHandle.ResponseThrowable getError() {
        return error;
    }

    public ResultSource getSource() {
        return source;
    }

    public interface Result<T> {
        void onResult(DataResult<T> dataResult);
    }
}
