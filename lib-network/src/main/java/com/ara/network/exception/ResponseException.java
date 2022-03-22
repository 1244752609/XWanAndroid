package com.ara.network.exception;

import com.ara.network.R;
import com.ara.network.bean.BaseBean;

/**
 * Created by XieXin on 2018/12/10.
 * 接口响应错误编码处理
 */
public class ResponseException extends ExceptionHandle.ServerException {
    public static ResponseException create(int code, String msg) {
        return new ResponseException(code, msg);
    }

    private ResponseException(int code, String msg) {
        this.setMsg(msg);
        this.setCode(code);
        switch (code) {
            case BaseBean.CODE_LOGIN_OVERDUE://登录过期
                this.setMsgResId(R.string.login_overdue);
                break;
            case 10001://HTTP请求方法不支持
            case 10002://JSON数据格式错误
            case 10003://请求的Body缺失
            case 10004://请求的URL参数缺失
            case 10005://数据验证不通过,请检查参数
                this.setMsgResId(R.string.error_network_desertion);
                break;
            default:
                if (String.valueOf(code).length() == 3) {
                    this.setMsgResId(R.string.error_network_desertion);
                } else {
                    this.setMsgResId(0);
                }
                break;
        }
    }
}
