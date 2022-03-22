package com.ara.network.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;


import com.ara.network.bean.BaseBean;
import com.ara.network.exception.ResponseException;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Created by XieXin on 2018/12/10.
 * Rx帮助类，预处理服务器回掉结果
 */
public class RxUtils {
    private RxUtils() {
    }

    /**
     * 处理返回结果
     *
     * @param successCode Api返回成功Code
     * @return FlowableTransformer<BaseBean < T>, BaseBean<T>>
     */
    public static <T> FlowableTransformer<BaseBean<T>, BaseBean<T>> handleResult(final int successCode) {
        return upstream -> upstream.flatMap((Function<BaseBean<T>, Publisher<BaseBean<T>>>) bean -> {
            if (bean.getCode() == successCode) {
                return createData(bean);
            } else {
                return Flowable.error(ResponseException.create(bean.getCode(), bean.getMsg()));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 处理返回结果
     *
     * @return FlowableTransformer<BaseBean < T>, BaseBean<T>>
     */
    public static <T> FlowableTransformer<BaseBean<T>, BaseBean<T>> handleResult() {
        return upstream -> upstream.flatMap((Function<BaseBean<T>, Publisher<BaseBean<T>>>) bean -> {
            if (bean.getCode() == BaseBean.CODE_OK) {
                return createData(bean);
            } else {
                return Flowable.error(ResponseException.create(bean.getCode(), bean.getMsg()));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 创建Flowable
     *
     * @param base 基本数据实体
     * @param <T>  泛型
     * @return Flowable<BaseBean < T>>
     */
    private static <T> Flowable<BaseBean<T>> createData(final BaseBean<T> base) {
        return Flowable.create((FlowableOnSubscribe<BaseBean<T>>) emitter -> {
            try {
                emitter.onNext(base);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 没有处理返回结果
     *
     * @return FlowableTransformer<T, T>
     */
    public static <T> FlowableTransformer<T, T> notHandleResult() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 倒计时
     * 利用interval()定时发送Observable
     * 通过map()将0、1、2、3...的计数变为...3、2、1、0倒计时
     * 通过take(N)取前N个的Observable
     *
     * @param time 时间秒
     * @return Observable<Integer>
     */
    public static Observable<Integer> countDown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue()).take(countTime + 1L);
    }

    /**
     * 倒计时
     * 利用interval()定时发送Observable
     * 通过map()将0、1、2、3...的计数变为...3、2、1、0倒计时
     * 通过take(N)取前N个的Observable
     *
     * @param time 时间秒
     * @return Observable<Integer>
     */
    public static Observable<Integer> countDown(int time, Lifecycle lifecycle) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription))
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue()).take(countTime + 1L);
    }

    /**
     * 倒计时
     * 利用interval()定时发送Observable
     * 通过map()将0、1、2、3...的计数变为...3、2、1、0倒计时
     * 通过take(N)取前N个的Observable
     *
     * @param time 时间秒
     * @return Observable<Integer>
     */
    public static Observable<Integer> countDown(int time, TimeUnit unit, Lifecycle lifecycle) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, unit)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription))
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1L);
    }

    /**
     * 倒计时
     * 利用interval()定时发送Observable
     * 通过map()将0、1、2、3...的计数变为...3、2、1、0倒计时
     * 通过take(N)取前N个的Observable
     *
     * @param time 时间秒
     * @return Observable<Integer>
     */
    public static Observable<Long> countDown(long time, Lifecycle lifecycle) {
        if (time < 0) time = 0;
        final long countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription))
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1L);
    }

    /**
     * 倒计时
     * 利用interval()定时发送Observable
     * 通过map()将0、1、2、3...的计数变为...3、2、1、0倒计时
     * 通过take(N)取前N个的Observable
     *
     * @param time 时间秒
     * @return Observable<Integer>
     */
    public static Observable<Long> countDown(long time, TimeUnit unit, Lifecycle lifecycle) {
        if (time < 0) time = 0;
        final long countTime = time;
        return Observable.interval(0, 1, unit)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription))
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1L);
    }

    /**
     * 轮询
     * 利用interval()定时发送0、1、2、3... Observable
     */
    public static void interval(long period, Observer<Long> observer, Lifecycle lifecycle) {
        Observable.interval(0, period, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 轮询
     * 利用interval()定时发送0、1、2、3... Observable
     */
    public static void interval(long initialDelay, long period, Observer<Long> observer, Lifecycle lifecycle) {
        Observable.interval(initialDelay, period, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 轮询
     * 利用interval()定时发送0、1、2、3... Observable
     */
    public static void interval(long initialDelay, long period, @NonNull TimeUnit unit, Observer<Long> observer, Lifecycle lifecycle) {
        Observable.interval(initialDelay, period, unit)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 绑定生命周期
     *
     * @param lifecycle Lifecycle
     */
    public static void bindToLifecycle(Lifecycle lifecycle, Disposable disposable) {
        if (lifecycle == null) return;
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (disposable != null && !disposable.isDisposed())
                    disposable.dispose();
            }
        });
    }
}
