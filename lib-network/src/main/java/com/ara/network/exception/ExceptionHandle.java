package com.ara.network.exception;

import android.net.ParseException;

import com.ara.common.util.LoggerUtils;
import com.ara.common.util.GsonUtils;
import com.ara.network.R;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLException;

import retrofit2.HttpException;

/**
 * Created by XieXin on 2018/12/10.
 * 网络异常处理
 */
public class ExceptionHandle {
    public static final int UNKNOWN = 1000;//未知错误
    public static final int PARSE_ERROR = 1001;//解析错误
    public static final int NETWORK_ERROR = 1002;//网络错误
    public static final int HTTP_ERROR = 1003;//协议出错
    public static final int SSL_ERROR = 1004;//证书出错
    public static final int TIMEOUT_ERROR = 1005;//连接超时
    public static final int NOT_NET_ERROR = 1006;//无网络
    public static final int DATA_ERROR = 1007;//数据异常
    public static final int UNKNOWN_HOST = 1007;//主机地址未知

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int SERVICE_UNAVAILABLE = 503;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    ex.msg = "操作未授权";
                    ex.msgResId = R.string.unauthorized;
                    break;
                case FORBIDDEN:
                    ex.msg = "请求被拒绝";
                    ex.msgResId = R.string.forbidden;
                    break;
                case NOT_FOUND:
                    ex.msg = "资源不存在";
                    ex.msgResId = R.string.not_found;
                    break;
                case REQUEST_TIMEOUT:
                    ex.msg = "服务器执行超时";
                    ex.msgResId = R.string.request_timeout;
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.msg = "服务器内部错误";
                    ex.msgResId = R.string.internal_server_error;
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.msg = "服务器不可用";
                    ex.msgResId = R.string.service_unavailable;
                    break;
                default:
                    ex.msg = "网络错误";
                    ex.msgResId = R.string.network_error;
                    break;
            }
        } else if (e instanceof ServerException) { //服务器错误编码处理
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException, resultException.code);
            ex.msg = resultException.getMsg();
            ex.msgResId = resultException.getMsgResId();
        } else if (e instanceof JsonParseException || e instanceof JSONException ||
                e instanceof ParseException || e instanceof MalformedJsonException) {
            ex = new ResponseThrowable(e, PARSE_ERROR);
            ex.msg = "解析错误";
            ex.msgResId = R.string.parse_error;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, NETWORK_ERROR);
            ex.msg = "连接失败";
            ex.msgResId = R.string.network_error;
        } else if (e instanceof SSLException) {
            ex = new ResponseThrowable(e, SSL_ERROR);
            ex.msg = "证书验证失败";
            ex.msgResId = R.string.certificate_error;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponseThrowable(e, TIMEOUT_ERROR);
            ex.msg = "连接超时";
            ex.msgResId = R.string.connection_timeout;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponseThrowable(e, TIMEOUT_ERROR);
            ex.msg = "连接超时";
            ex.msgResId = R.string.connection_timeout;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ResponseThrowable(e, UNKNOWN_HOST);
            ex.msg = "主机地址未知";
            ex.msgResId = R.string.unknown_host;
            return ex;
        } else {
            ex = new ResponseThrowable(e, UNKNOWN);
            ex.msg = "未知错误";
            ex.msgResId = R.string.unknown_error;
        }
        LoggerUtils.e(GsonUtils.toString(ex));
        return ex;
    }

    /**
     * 响应异常
     */
    public static class ResponseThrowable extends Exception {
        private int code;
        private String msg;
        private int msgResId;

        ResponseThrowable(int code, String msg, int msgResId) {
            this.code = code;
            this.msg = msg;
            this.msgResId = msgResId;
        }

        ResponseThrowable(Throwable e, int code) {
            super(e);
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getMsgResId() {
            return msgResId;
        }

        public void setMsgResId(int msgResId) {
            this.msgResId = msgResId;
        }
    }

    /**
     * 服务器异常
     */
    public static class ServerException extends RuntimeException {
        private int code;
        private String msg;
        private int msgResId;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getMsgResId() {
            return msgResId;
        }

        public void setMsgResId(int msgResId) {
            this.msgResId = msgResId;
        }
    }
}
