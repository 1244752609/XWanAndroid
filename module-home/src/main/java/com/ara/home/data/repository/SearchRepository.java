/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ara.home.data.repository;

import com.ara.base.data.response.DataResult;
import com.ara.base.data.response.ResultSource;
import com.ara.db.DBUtils;
import com.ara.db.entity.SearchRecord;
import com.ara.home.data.api.HomeService;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.home.data.bean.HotBean;
import com.ara.network.RetrofitHolder;
import com.ara.network.bean.BaseBean;
import com.ara.network.exception.DataException;
import com.ara.network.exception.ExceptionHandle;
import com.ara.network.util.RxSubscriberUtils;
import com.ara.network.util.RxUtils;
import com.ara.project_common.data.bean.PagingBean;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Created by XieXin on 2022/3/4.
 * 搜索
 */
public class SearchRepository {
    private static final SearchRepository S_REQUEST_MANAGER = new SearchRepository();
    private static final CompositeDisposable disposable = new CompositeDisposable();
    private static final HomeService service = RetrofitHolder.getInstance().create(HomeService.class);

    private SearchRepository() {
    }

    public static SearchRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    public void getHotSearch(DataResult.Result<BaseBean<List<HotBean>>> result) {
        disposable.add(service.getHotSearch()
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<List<HotBean>>>() {
                    @Override
                    protected void doOnNext(BaseBean<List<HotBean>> bean) {
                        if (bean.getData() == null) {
                            result.onResult(DataResult.createFail(DataException.create()));
                            return;
                        }
                        result.onResult(DataResult.create(bean));
                    }

                    @Override
                    protected void doOnError(ExceptionHandle.ResponseThrowable e) {
                        result.onResult(DataResult.createFail(e));
                    }
                }));
    }

    public void getListArticle(int page, String keyword, DataResult.Result<BaseBean<PagingBean<ArticleBean>>> result) {
        disposable.add(service.getListArticle(page, keyword)
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<PagingBean<ArticleBean>>>() {
                    @Override
                    protected void doOnNext(BaseBean<PagingBean<ArticleBean>> bean) {
                        if (bean.getData() == null) {
                            result.onResult(DataResult.createFail(DataException.create()));
                            return;
                        }
                        result.onResult(DataResult.create(bean));
                    }

                    @Override
                    protected void doOnError(ExceptionHandle.ResponseThrowable e) {
                        result.onResult(DataResult.createFail(e));
                    }
                }));
    }

    public void cancelRequest() {
        if (disposable != null) disposable.clear();
    }

    public void getSearchRecord(DataResult.Result<List<SearchRecord>> result) {
        result.onResult(DataResult.create(DBUtils.getDb().searchRecordDao().getAll(), ResultSource.DATABASE));
    }

    public void insertSearchRecord(SearchRecord record) {
        SearchRecord searchRecord = DBUtils.getDb().searchRecordDao().findByContent(record.content);
        if (searchRecord == null) {//不存在添加，存在不做任何处理
            DBUtils.getDb().searchRecordDao().insertAll(record);
        }
        List<SearchRecord> list = DBUtils.getDb().searchRecordDao().getAll();
        if (list.size() > 20) {
            deleteSearchRecord(list.get(0).id);
        }
    }

    public void deleteSearchRecord(long id) {
        DBUtils.getDb().searchRecordDao().deleteById(id);
    }

    public void deleteAllSearchRecord() {
        DBUtils.getDb().searchRecordDao().deleteAll();
    }

}
