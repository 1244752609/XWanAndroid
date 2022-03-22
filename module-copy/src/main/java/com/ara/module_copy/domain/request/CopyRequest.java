package com.ara.module_copy.domain.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ara.base.data.response.DataResult;
import com.ara.base.domain.request.BaseRequest;
import com.ara.module_copy.data.repository.DataRepository;
import com.ara.network.bean.BaseBean;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

/**
 * Created by XieXin on 2022/3/4.
 */
public class CopyRequest extends BaseRequest implements DefaultLifecycleObserver {
    // 让 Request 可观察页面生命周期，
    // 从而在页面即将退出、且登录请求由于网络延迟尚未完成时，
    // 及时通知数据层取消本次请求，以避免资源浪费和一系列不可预期的问题。
    private final UnPeekLiveData<DataResult<BaseBean<Object>>> copyLiveData = new UnPeekLiveData<>();

    // 向 ui 层提供的 request LiveData，使用 "父类的 LiveData" 而不是 "Mutable 的 LiveData"，
    // 如此达成了 "唯一可信源" 的设计，也即通过访问控制权限实现 "读写分离"，
    // 并且进一步地，使用 ProtectedUnPeekLiveData 类，而不是 LiveData 类，
    // 以此来确保消息分发的可靠一致，及 "事件" 场景下的防倒灌特性。
    public UnPeekLiveData<DataResult<BaseBean<Object>>> getCopyLiveData() {
        // 与此同时，为了方便语义上的理解，故而直接将 DataResult 作为 LiveData value 回推给 UI 层，
        // 而不是将 DataResult 的泛型实体拆下来单独回推，如此
        // 一方面使 UI 层有机会基于 DataResult 的 responseStatus 来分别处理 请求成功或失败的情况下的 UI 表现，
        // 另一方面从语义上强调了 该数据是请求得来的结果，是只读的，与 "可变状态" 形成明确的区分，
        // 从而方便团队开发人员自然而然遵循 "唯一可信源"/"单向数据流" 的开发理念，规避消息同步一致性等不可预期的错误。
        return copyLiveData;
    }

    public void copy(String name) {
        DataRepository.getInstance().copy(name, copyLiveData::postValue);
    }

    public void cancel() {
        DataRepository.getInstance().cancelRequest();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DataRepository.getInstance().cancelRequest();
    }

}
