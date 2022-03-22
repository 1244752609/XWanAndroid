package com.ara.common.rxbus;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.PublishProcessor;

/**
 * Created by XieXin on 2018/12/10.
 * 基于Rxjava2的事件总线：Rxbus
 */
public class RxBus {
    private final FlowableProcessor<Object> mBus;

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    private static class RxHolder {
        private static final RxBus instance = new RxBus();
    }

    private static RxBus getInstance() {
        return RxHolder.instance;
    }

    /**
     * 发送消息
     *
     * @param obj 对象
     */
    public static void post(@NonNull Object obj) {
        RxBus.getInstance().mBus.onNext(obj);
    }

    /**
     * 注册
     * 注册完后要解除绑定
     *
     * @param clz 类
     * @param <T> 传输类型
     * @return Flowable
     */
    public static <T> Flowable<T> register(Class<T> clz) {
        return RxBus.getInstance().mBus.ofType(clz)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     * 注册完后无需解除绑定，已自动绑定生命周期
     *
     * @param clz       类
     * @param <T>       传输类型
     * @param lifecycle 生命周期
     * @return Flowable
     */
    public static <T> Flowable<T> register(Class<T> clz, Lifecycle lifecycle) {
        return RxBus.getInstance().mBus.ofType(clz)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription));

    }

    /**
     * 注册
     * 注册完后无需解除绑定，已自动绑定生命周期
     *
     * @param lifecycle 生命周期
     * @return Flowable
     */
    public static Flowable<RxMsgEvent> register(Lifecycle lifecycle) {
        return RxBus.getInstance().mBus.ofType(RxMsgEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> bindToLifecycle(lifecycle, subscription));

    }

    /**
     * 绑定生命周期
     *
     * @param lifecycle 生命周期
     */
    private static void bindToLifecycle(Lifecycle lifecycle, Subscription subscription) {
        if (lifecycle == null) return;
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (subscription != null) subscription.cancel();
            }
        });
    }

    /**
     * 会将所有由mBus生成的Flowable都置completed状态后续的所有消息都收不到了
     */
    public static void unregisterAll() {
        RxBus.getInstance().mBus.onComplete();
    }

    public static boolean hasSubscribers() {
        return RxBus.getInstance().mBus.hasSubscribers();
    }
}
