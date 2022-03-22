package com.ara.mine.domain.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ara.base.data.response.DataResult;
import com.ara.base.domain.request.BaseRequest;
import com.ara.mine.data.repository.DataRepository;
import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.bean.PagingBean;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

/**
 * Created by XieXin on 2022/3/4.
 */
public class CollectRequest extends BaseRequest implements DefaultLifecycleObserver {
    private final UnPeekLiveData<DataResult<BaseBean<PagingBean<ArticleBean>>>> collectListLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<BaseBean<Object>>> collectLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<BaseBean<Object>>> unCollectLiveData = new UnPeekLiveData<>();

    public UnPeekLiveData<DataResult<BaseBean<PagingBean<ArticleBean>>>> getCollectListLiveData() {
        return collectListLiveData;
    }

    public UnPeekLiveData<DataResult<BaseBean<Object>>> getCollectLiveData() {
        return collectLiveData;
    }

    public UnPeekLiveData<DataResult<BaseBean<Object>>> getUnCollectLiveData() {
        return unCollectLiveData;
    }

    public void getCollectList(int page) {
        DataRepository.getInstance().getCollectList(page, collectListLiveData::postValue);
    }

    public void collect(String id) {
        DataRepository.getInstance().collect(id, collectLiveData::postValue);
    }

    public void unCollect(String id) {
        DataRepository.getInstance().unCollect(id, unCollectLiveData::postValue);
    }

    public void cancel() {
        DataRepository.getInstance().cancelRequest();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DataRepository.getInstance().cancelRequest();
    }

}
