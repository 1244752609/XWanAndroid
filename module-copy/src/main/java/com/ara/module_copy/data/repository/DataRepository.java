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

package com.ara.module_copy.data.repository;

import com.ara.base.data.response.DataResult;
import com.ara.module_copy.data.api.CopyService;
import com.ara.network.RetrofitHolder;
import com.ara.network.bean.BaseBean;
import com.ara.network.exception.DataException;
import com.ara.network.exception.ExceptionHandle;
import com.ara.network.util.RxSubscriberUtils;
import com.ara.network.util.RxUtils;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Create by KunMinX at 19/10/29
 */
public class DataRepository {
    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();
    private static final CompositeDisposable disposable = new CompositeDisposable();
    private static final CopyService service = RetrofitHolder.getInstance().create(CopyService.class);

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    public void copy(String name, DataResult.Result<BaseBean<Object>> result) {
        disposable.add(service.copy(name)
                .compose(RxUtils.handleResult())
                .subscribeWith(new RxSubscriberUtils<BaseBean<Object>>() {
                    @Override
                    protected void doOnNext(BaseBean<Object> bean) {
                        // TODO: 2022/3/16 有些接口用不到data可以去除非空处理
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

}
