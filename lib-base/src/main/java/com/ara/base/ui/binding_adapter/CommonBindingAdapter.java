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

package com.ara.base.ui.binding_adapter;

import android.graphics.drawable.Drawable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.databinding.BindingAdapter;

import com.ara.base.data.response.LoadState;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;


/**
 * Create by KunMinX at 19/9/18
 */
public class CommonBindingAdapter {

    @BindingAdapter(value = {"imageUrl", "placeHolder", "error"}, requireAll = false)
    public static void imageUrl(ImageView view, String url, Drawable placeHolder, Drawable error) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeHolder) //占位图
                .error(error);            //错误图
        Glide.with(view.getContext()).load(url).apply(options).into(view);
    }

    @BindingAdapter(value = {"visible"}, requireAll = false)
    public static void visible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = {"textColorBA"}, requireAll = false)
    public static void setTextColorBA(TextView textView, @ColorInt int textColor) {
        textView.setTextColor(textColor);
    }

    @BindingAdapter(value = {"selected"}, requireAll = false)
    public static void selected(View view, boolean select) {
        view.setSelected(select);
    }


    @BindingAdapter(value = {"onClickWithDebouncing"}, requireAll = false)
    public static void onClickWithDebouncing(View view, View.OnClickListener clickListener) {
        ClickUtils.applySingleDebouncing(view, clickListener);
    }

    @BindingAdapter(value = {"isShowStateBarHeight"}, requireAll = false)
    public static void isShowStateBarHeight(View view, boolean isShowStateBarHeight) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = BarUtils.getStatusBarHeight();
    }

    @BindingAdapter(value = {"onRefreshListener", "onRefreshLoadMoreListener",
            "onLoadMoreListener", "onMultiListener"}, requireAll = false)
    public static void SmartRefreshLayout(SmartRefreshLayout refresh,
                                          OnRefreshListener onRefreshListener,
                                          OnRefreshLoadMoreListener onRefreshLoadMoreListener,
                                          OnLoadMoreListener onLoadMoreListener,
                                          OnMultiListener onMultiListener) {
        if (onRefreshListener != null)
            refresh.setOnRefreshListener(onRefreshListener);

        if (onRefreshLoadMoreListener != null)
            refresh.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener);

        if (onLoadMoreListener != null)
            refresh.setOnLoadMoreListener(onLoadMoreListener);

        if (onMultiListener != null)
            refresh.setOnMultiListener(onMultiListener);

    }

    @BindingAdapter(value = {"autoRefresh", "autoLoadMore"}, requireAll = false)
    public static void SmartRefreshLayout(SmartRefreshLayout refresh, boolean autoRefresh, boolean autoLoadMore) {
        if (autoRefresh) refresh.autoRefresh();

        if (autoLoadMore) refresh.autoLoadMore();
    }

    @BindingAdapter(value = {"enableRefresh"}, requireAll = false)
    public static void enableRefresh(SmartRefreshLayout refresh, boolean enableRefresh) {
        refresh.setEnableRefresh(enableRefresh);
    }

    @BindingAdapter(value = {"enableLoadMore"}, requireAll = false)
    public static void enableLoadMore(SmartRefreshLayout refresh, boolean enableLoadMore) {
        refresh.setEnableLoadMore(enableLoadMore);
    }

    @BindingAdapter(value = {"refreshLoadState"}, requireAll = false)
    public static void SmartRefreshLayout(SmartRefreshLayout refresh, LoadState refreshLoadState) {
        if (refreshLoadState == null) return;
        switch (refreshLoadState) {
            case SUCCESS:
                refresh.finishRefresh();
                break;
            case ERROR:
                refresh.finishRefresh(false);
                break;
            case LOAD_MORE_SUCCESS:
                refresh.finishLoadMore();
                break;
            case LOAD_MORE_ERROR:
                refresh.finishLoadMore(false);
                break;
            case LOAD_MORE_NO_MORE_DATA:
                refresh.finishLoadMoreWithNoMoreData();
                break;
        }
    }

    @BindingAdapter(value = {"addTextChangedListener"}, requireAll = false)
    public static void addTextChangedListener(EditText et, TextWatcher addTextChangedListener) {
        if (addTextChangedListener != null) et.addTextChangedListener(addTextChangedListener);
    }

    @BindingAdapter(value = {"onEditorActionListener"}, requireAll = false)
    public static void setOnEditorActionListener(EditText et, TextView.OnEditorActionListener onEditorActionListener) {
        if (onEditorActionListener != null) et.setOnEditorActionListener(onEditorActionListener);
    }
}
