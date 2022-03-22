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

package com.ara.module_public.data.repository;

import android.text.TextUtils;

import com.ara.base.data.response.DataResult;
import com.ara.module_public.data.api.PublicService;
import com.ara.module_public.data.bean.PublicAuthorBean;
import com.ara.network.RetrofitHolder;
import com.ara.network.bean.BaseBean;
import com.ara.network.exception.DataException;
import com.ara.network.exception.ExceptionHandle;
import com.ara.network.util.RxSubscriberUtils;
import com.ara.network.util.RxUtils;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.bean.PagingBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Create by KunMinX at 19/10/29
 */
public class DataRepository {
    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();
    private static final CompositeDisposable disposable = new CompositeDisposable();
    private static final PublicService service = RetrofitHolder.getInstance().create(PublicService.class);

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    public void getAuthor(DataResult.Result<BaseBean<List<PublicAuthorBean>>> result) {
        disposable.add(service.getAuthor()
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<List<PublicAuthorBean>>>() {
                    @Override
                    protected void doOnNext(BaseBean<List<PublicAuthorBean>> bean) {
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

    public void getListPublic(int page, String cid, DataResult.Result<BaseBean<PagingBean<ArticleBean>>> result) {
        Flowable<BaseBean<PagingBean<ArticleBean>>> flowable;
        if (TextUtils.isEmpty(cid)) {
            flowable = service.getListPublic(page);
        } else {
            flowable = service.getListPublic(page, cid);
        }
        disposable.add(flowable
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

    public void collect(String id, DataResult.Result<BaseBean<Object>> result) {
        disposable.add(service.collect(id)
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<Object>>() {
                    @Override
                    protected void doOnNext(BaseBean<Object> bean) {
                        result.onResult(DataResult.create(bean));
                    }

                    @Override
                    protected void doOnError(ExceptionHandle.ResponseThrowable e) {
                        result.onResult(DataResult.createFail(e));
                    }
                }));
    }

    public void unCollect(String id, DataResult.Result<BaseBean<Object>> result) {
        disposable.add(service.unCollect(id)
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<Object>>() {
                    @Override
                    protected void doOnNext(BaseBean<Object> bean) {
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

}
