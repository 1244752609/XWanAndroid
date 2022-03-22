package com.ara.network.util;

import com.ara.common.rxbus.RxBus;
import com.ara.common.rxbus.RxMsgEvent;
import com.ara.network.bean.BaseBean;
import com.ara.network.exception.ExceptionHandle;

import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

/**
 * Created by XieXin on 2018/12/10.
 * RxJava的生命周期处理工具类
 */
public abstract class RxSubscriberUtils<T> extends DisposableSubscriber<T> {

    @Override
    public void onNext(T t) {
        doOnNext(t);
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable t) {
        doOnError(ExceptionHandle.handleException(t));
        if (t instanceof ExceptionHandle.ServerException) {
            ExceptionHandle.ServerException exception = (ExceptionHandle.ServerException) t;
            if (exception.getCode() == BaseBean.CODE_LOGIN_OVERDUE) {//登录过期
                RxBus.post(RxMsgEvent.create(BaseBean.REQUEST_LOGIN_OVERDUE));
            }
        }
    }

    protected abstract void doOnNext(T bean);

    protected void doOnError(ExceptionHandle.ResponseThrowable e) {
    }

}
