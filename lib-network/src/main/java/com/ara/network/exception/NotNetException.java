package com.ara.network.exception;

import com.ara.network.R;

/**
 * Created by XieXin on 2019/1/17.
 * 无网络异常
 */
public class NotNetException extends ExceptionHandle.ResponseThrowable {

    public static NotNetException create() {
        return new NotNetException();
    }

    private NotNetException() {
        super(ExceptionHandle.NOT_NET_ERROR, "无网络", R.string.not_net);
    }

}
