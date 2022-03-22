package com.ara.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by XieXin on 2021/5/7.
 * 基础响应实体
 */
public class BaseBean<T> {
    /*** 成功编码 */
    public static final int CODE_OK = 0;
    /*** 登录过期编码 */
    public static final int REQUEST_LOGIN_OVERDUE = 888888;
    public static final int CODE_LOGIN_OVERDUE = -1001;

    /*** 编码 */
    @SerializedName("errorCode")
    private int code;
    /*** 消息 */
    @SerializedName("errorMsg")
    private String msg;
    /*** 数据实体 */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
