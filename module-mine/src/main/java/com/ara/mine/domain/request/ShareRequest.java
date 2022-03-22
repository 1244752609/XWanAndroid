package com.ara.mine.domain.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ara.base.data.response.DataResult;
import com.ara.base.domain.request.BaseRequest;
import com.ara.mine.data.bean.ShareBean;
import com.ara.mine.data.repository.DataRepository;
import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.bean.PagingBean;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

/**
 * Created by XieXin on 2022/3/4.
 */
public class ShareRequest extends BaseRequest implements DefaultLifecycleObserver {
    private final UnPeekLiveData<DataResult<BaseBean<ShareBean>>> shareListLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<BaseBean<Object>>> shareArticleLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<BaseBean<Object>>> deleteArticleLiveData = new UnPeekLiveData<>();

    public UnPeekLiveData<DataResult<BaseBean<ShareBean>>> getShareListLiveData() {
        return shareListLiveData;
    }

    public UnPeekLiveData<DataResult<BaseBean<Object>>> getShareArticleLiveData() {
        return shareArticleLiveData;
    }

    public UnPeekLiveData<DataResult<BaseBean<Object>>> getDeleteArticleLiveData() {
        return deleteArticleLiveData;
    }

    public void getShareList(int page) {
        DataRepository.getInstance().getShareList(page, shareListLiveData::postValue);
    }

    public void shareArticle(String title, String url) {
        DataRepository.getInstance().shareArticle(title, url, shareArticleLiveData::postValue);
    }

    public void deleteArticle(String id) {
        DataRepository.getInstance().deleteArticle(id, deleteArticleLiveData::postValue);
    }

    public void cancel() {
        DataRepository.getInstance().cancelRequest();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DataRepository.getInstance().cancelRequest();
    }

}
