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

package com.ara.web.data.repository;

import com.ara.base.data.response.DataResult;
import com.ara.network.RetrofitHolder;
import com.ara.network.bean.BaseBean;
import com.ara.network.exception.ExceptionHandle;
import com.ara.network.util.RxSubscriberUtils;
import com.ara.network.util.RxUtils;
import com.ara.web.data.api.WebService;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Create by KunMinX at 19/10/29
 */
public class DataRepository {
    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();
    private static final CompositeDisposable disposable = new CompositeDisposable();
    private static final WebService service = RetrofitHolder.getInstance().create(WebService.class);

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    public void web(String name, DataResult.Result<BaseBean<Object>> result) {
        disposable.add(service.web(name)
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
