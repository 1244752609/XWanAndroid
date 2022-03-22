package com.ara.project_common.ui.binding_adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.BindingAdapter;

import com.ara.project_common.ui.view.TitleBar;

/**
 * Created by XieXin on 2022/2/28.
 * 标题栏适配器
 */
public class TitleBarBindingAdapter {
    @BindingAdapter(value = {"barTitleText"}, requireAll = false)
    public static void barTitleText(TitleBar bar, String barTitleText) {
        if (!TextUtils.isEmpty(barTitleText)) bar.setTitle(barTitleText);
    }

    @BindingAdapter(value = {"barRightText"}, requireAll = false)
    public static void barRightText(TitleBar bar, String barRightText) {
        if (!TextUtils.isEmpty(barRightText)) bar.setRightText(barRightText);
    }

    @BindingAdapter(value = {"barRightVisible"}, requireAll = false)
    public static void barRightVisible(TitleBar bar, boolean barRightVisible) {
        bar.setRightVisible(barRightVisible);
    }

    @BindingAdapter(value = {"barRightOnClickListener"}, requireAll = false)
    public static void setRightOnClickListener(TitleBar bar, View.OnClickListener barRightOnClickListener) {
        if (barRightOnClickListener != null) bar.setRightOnClickListener(barRightOnClickListener);
    }

    @BindingAdapter(value = {"barBackOnClickListener"}, requireAll = false)
    public static void setBackOnClickListener(TitleBar bar, View.OnClickListener barBackOnClickListener) {
        if (barBackOnClickListener != null) bar.setRightOnClickListener(barBackOnClickListener);
    }
}
