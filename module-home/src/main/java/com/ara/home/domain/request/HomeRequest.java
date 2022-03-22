package com.ara.home.domain.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ara.base.data.response.DataResult;
import com.ara.base.domain.request.BaseRequest;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.home.data.bean.BannerBean;
import com.ara.home.data.repository.HomeRepository;
import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.PagingBean;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

import java.util.List;

/**
 * Created by XieXin on 2022/3/4.
 */
public class HomeRequest extends BaseRequest implements DefaultLifecycleObserver {
    private final UnPeekLiveData<DataResult<BaseBean<List<BannerBean>>>> bannerLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<BaseBean<PagingBean<ArticleBean>>>> articleLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<BaseBean<Object>>> collectLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<BaseBean<Object>>> unCollectLiveData = new UnPeekLiveData<>();

    public UnPeekLiveData<DataResult<BaseBean<List<BannerBean>>>> getBannerLiveData() {
        return bannerLiveData;
    }

    public UnPeekLiveData<DataResult<BaseBean<PagingBean<ArticleBean>>>> getArticleLiveData() {
        return articleLiveData;
    }

    public UnPeekLiveData<DataResult<BaseBean<Object>>> getCollectLiveData() {
        return collectLiveData;
    }

    public UnPeekLiveData<DataResult<BaseBean<Object>>> getUnCollectLiveData() {
        return unCollectLiveData;
    }

    public void getBanner() {
        HomeRepository.getInstance().getBanner(bannerLiveData::postValue);
    }

    public void getArticle(int page) {
        HomeRepository.getInstance().getListArticle(page, articleLiveData::postValue);
    }

    public void collect(String id) {
        HomeRepository.getInstance().collect(id, collectLiveData::postValue);
    }

    public void unCollect(String id) {
        HomeRepository.getInstance().unCollect(id, unCollectLiveData::postValue);
    }

    public void cancel() {
        HomeRepository.getInstance().cancelRequest();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        HomeRepository.getInstance().cancelRequest();
    }
}
