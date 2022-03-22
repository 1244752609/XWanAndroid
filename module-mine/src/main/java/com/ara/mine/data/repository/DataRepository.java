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

package com.ara.mine.data.repository;

import com.ara.base.data.response.DataResult;
import com.ara.mine.data.api.MineService;
import com.ara.mine.data.bean.IntegralBean;
import com.ara.mine.data.bean.ShareBean;
import com.ara.mine.data.bean.UserInfoBean;
import com.ara.network.RetrofitHolder;
import com.ara.network.bean.BaseBean;
import com.ara.network.exception.DataException;
import com.ara.network.exception.ExceptionHandle;
import com.ara.network.util.RxSubscriberUtils;
import com.ara.network.util.RxUtils;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.bean.PagingBean;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Create by KunMinX at 19/10/29
 */
public class DataRepository {
    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();
    private static final CompositeDisposable disposable = new CompositeDisposable();
    private static final MineService service = RetrofitHolder.getInstance().create(MineService.class);

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    public void getUserInfo(DataResult.Result<BaseBean<UserInfoBean>> result) {
        disposable.add(service.getUserInfo()
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<UserInfoBean>>() {
                    @Override
                    protected void doOnNext(BaseBean<UserInfoBean> bean) {
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

    public void getIntegralList(int page, DataResult.Result<BaseBean<PagingBean<IntegralBean>>> result) {
        disposable.add(service.getIntegralList(page)
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<PagingBean<IntegralBean>>>() {
                    @Override
                    protected void doOnNext(BaseBean<PagingBean<IntegralBean>> bean) {
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

    public void getRank(int page, DataResult.Result<BaseBean<PagingBean<IntegralBean>>> result) {
        disposable.add(service.getRank(page)
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<PagingBean<IntegralBean>>>() {
                    @Override
                    protected void doOnNext(BaseBean<PagingBean<IntegralBean>> bean) {
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

    public void getCollectList(int page, DataResult.Result<BaseBean<PagingBean<ArticleBean>>> result) {
        disposable.add(service.getCollectList(page)
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

    public void getShareList(int page, DataResult.Result<BaseBean<ShareBean>> result) {
        disposable.add(service.getShareList(page)
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<ShareBean>>() {
                    @Override
                    protected void doOnNext(BaseBean<ShareBean> bean) {
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

    public void shareArticle(String title, String url, DataResult.Result<BaseBean<Object>> result) {
        disposable.add(service.shareArticle(title, url)
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

    public void deleteArticle(String id, DataResult.Result<BaseBean<Object>> result) {
        disposable.add(service.deleteArticle(id)
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

    public void logout(DataResult.Result<BaseBean<Object>> result) {
        disposable.add(service.logout()
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
