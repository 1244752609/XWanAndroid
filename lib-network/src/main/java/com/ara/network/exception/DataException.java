package com.ara.network.exception;


import com.ara.network.R;

/**
 * Created by XieXin on 2020/5/12.
 * 数据异常
 */
public class DataException extends ExceptionHandle.ResponseThrowable {
    private DataException() {
        super(ExceptionHandle.DATA_ERROR, "", R.string.data_error);
    }

    public static DataException create() {
        return new DataException();
    }
}
