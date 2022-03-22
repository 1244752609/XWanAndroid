package com.ara.common.rxbus;

/**
 * Created by XieXin on 2018/12/10.
 * RxBus Msg
 */
public class RxMsgEvent {
    private int code;
    private String msg;
    private Object data;
    private int whit;

    public static RxMsgEvent create(int code) {
        return new RxMsgEvent(code, "", null);
    }

    public static RxMsgEvent create(int code, String msg) {
        return new RxMsgEvent(code, msg, null);
    }

    public static RxMsgEvent create(int code, Object data) {
        return new RxMsgEvent(code, "", data);
    }

    public static RxMsgEvent create(int code, String msg, Object data) {
        return new RxMsgEvent(code, msg, data);
    }

    private RxMsgEvent(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        if (msg == null) setMsg("");
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getWhit() {
        return whit;
    }

    public void setWhit(int whit) {
        this.whit = whit;
    }
}
