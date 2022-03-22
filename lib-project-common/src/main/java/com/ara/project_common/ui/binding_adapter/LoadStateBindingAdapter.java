package com.ara.project_common.ui.binding_adapter;

import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.databinding.BindingAdapter;

import com.ara.base.data.response.LoadState;
import com.ara.project_common.ui.view.LoadStateView;

/**
 * Created by XieXin on 2022/2/28.
 * 加载状态适配器
 */
public class LoadStateBindingAdapter {
    @BindingAdapter(value = {"loadState", "loadStateClick", "loadStateText", "loadStateBgVisibility",
            "loadStateBgColor"}, requireAll = false)
    public static void loadState(LoadStateView loadStateView, LoadState loadState,
                                 LoadStateView.ClickProxy loadStateClick, String loadStateText,
                                 boolean loadStateBgVisibility, @ColorInt int loadStateBgColor) {
        if (loadState == null) return;
        loadStateView.setBgVisibility(loadStateBgVisibility, loadStateBgColor);
        switch (loadState) {
            case LOADING://加载中
                if (TextUtils.isEmpty(loadStateText)) {
                    loadStateView.showLoading();
                } else {
                    loadStateView.showLoading(loadStateText);
                }
                break;
            case SUCCESS://加载成功
                loadStateView.showSuccess();
                break;
            case EMPTY://加载成功，空数据
                if (TextUtils.isEmpty(loadStateText)) {
                    if (loadStateClick != null) {
                        loadStateView.showEmpty(v -> loadStateClick.retry());
                    } else {
                        loadStateView.showEmpty();
                    }
                } else {
                    if (loadStateClick != null) {
                        loadStateView.showEmpty(loadStateText, v -> loadStateClick.retry());
                    } else {
                        loadStateView.showEmpty(loadStateText);
                    }
                }
                break;
            case ERROR://加载异常
                if (TextUtils.isEmpty(loadStateText)) {
                    if (loadStateClick != null) {
                        loadStateView.showError(v -> loadStateClick.retry());
                    } else {
                        loadStateView.showError();
                    }
                } else {
                    if (loadStateClick != null) {
                        loadStateView.showError(loadStateText, v -> loadStateClick.retry());
                    } else {
                        loadStateView.showError(loadStateText);
                    }
                }
                break;
        }
    }
}
