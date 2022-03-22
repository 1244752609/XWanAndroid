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

package com.ara.wanandroid.data.repository;

import com.ara.base.data.response.DataResult;
import com.ara.network.RetrofitHolder;
import com.ara.wanandroid.data.api.AppService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by KunMinX at 19/10/29
 */
public class DataRepository {

    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }


    private Call<String> mTestCall;

    public void test(DataResult.Result<String> result) {
        mTestCall = RetrofitHolder.getInstance().create(AppService.class).test();
        mTestCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                result.onResult(DataResult.create(String.valueOf(response.code())));
                mTestCall = null;
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                result.onResult(DataResult.createFail(null));
                mTestCall = null;
            }
        });
    }

    public void cancelTest() {
        if (mTestCall != null && !mTestCall.isCanceled()) {
            mTestCall.cancel();
            mTestCall = null;
        }
    }

}
