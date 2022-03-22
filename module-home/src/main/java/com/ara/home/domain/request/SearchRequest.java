package com.ara.home.domain.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ara.base.data.response.DataResult;
import com.ara.base.domain.request.BaseRequest;
import com.ara.db.entity.SearchRecord;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.home.data.bean.HotBean;
import com.ara.home.data.repository.HomeRepository;
import com.ara.home.data.repository.SearchRepository;
import com.ara.network.bean.BaseBean;
import com.ara.project_common.data.bean.PagingBean;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

import java.util.List;

/**
 * Created by XieXin on 2022/3/4.
 */
public class SearchRequest extends BaseRequest implements DefaultLifecycleObserver {
    private final UnPeekLiveData<DataResult<BaseBean<List<HotBean>>>> hotLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<BaseBean<PagingBean<ArticleBean>>>> articleLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<DataResult<List<SearchRecord>>> searchRecordLiveData = new UnPeekLiveData<>();

    public UnPeekLiveData<DataResult<BaseBean<List<HotBean>>>> getHotLiveData() {
        return hotLiveData;
    }

    public UnPeekLiveData<DataResult<BaseBean<PagingBean<ArticleBean>>>> getArticleLiveData() {
        return articleLiveData;
    }

    public UnPeekLiveData<DataResult<List<SearchRecord>>> getSearchRecordLiveData() {
        return searchRecordLiveData;
    }

    public void getHotSearch() {
        SearchRepository.getInstance().getHotSearch(hotLiveData::postValue);
    }

    public void getListArticle(int page, String keyword) {
        SearchRepository.getInstance().getListArticle(page, keyword, articleLiveData::postValue);
    }

    public void getSearchRecord() {
        SearchRepository.getInstance().getSearchRecord(searchRecordLiveData::postValue);
    }

    public void insertSearchRecord(SearchRecord record) {
        SearchRepository.getInstance().insertSearchRecord(record);
    }

    public void deleteSearchRecord(long id) {
        SearchRepository.getInstance().deleteSearchRecord(id);
    }

    public void deleteAllSearchRecord() {
        SearchRepository.getInstance().deleteAllSearchRecord();
    }

    public void cancel() {
        HomeRepository.getInstance().cancelRequest();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        HomeRepository.getInstance().cancelRequest();
    }
}
