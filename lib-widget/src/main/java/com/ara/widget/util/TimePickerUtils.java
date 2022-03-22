package com.ara.widget.util;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.ara.widget.R;
import com.ara.widget.dialog.OnDialogListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ScreenUtils;

import java.util.Calendar;

/**
 * Created by XieXin on 2022/2/24.
 * 时间选择工具类
 */
public class TimePickerUtils {
    /**
     * 获取时间对话框
     *
     * @param context
     * @param date
     * @param startDate
     * @param endDate
     * @param isShowHMS 是否显示时分秒
     * @param listener
     * @return
     */
    public static TimePickerView getTimePickerView(Context context, Calendar date, Calendar startDate,
                                                   Calendar endDate, boolean isShowHMS,
                                                   OnTimeSelectListener listener,
                                                   Lifecycle lifecycle) {
        int color = ContextCompat.getColor(context, com.ara.base.R.color.colorAccent);
        TimePickerView timePickerView = new TimePickerBuilder(context, listener)
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, isShowHMS, isShowHMS, isShowHMS})
                .setContentTextSize(20)
                .setLineSpacingMultiplier(2.5f)
                .isCenterLabel(false)
                .setSubmitColor(color)
                .setCancelColor(color)
                .setDividerColor(color)
                .setDate(date)
                .setRangDate(startDate, endDate)
                .isDialog(true)
                .build();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        timePickerView.getDialogContainerLayout().setLayoutParams(params);

        //获取当前Activity所在的窗体
        Window dialogWindow = timePickerView.getDialog().getWindow();
        //获得窗体的属性
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ScreenUtils.getScreenWidth();
            lp.width = lp.width - (lp.width / 6);
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            //底部
            dialogWindow.setGravity(Gravity.CENTER);
        }
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (timePickerView.isDialog()) timePickerView.dismiss();
            }
        });
        return timePickerView;
    }

    /**
     * 获取自定义时间对话框
     *
     * @param context
     * @param date
     * @param startDate
     * @param endDate
     * @param isShowHMS 是否显示时分秒
     * @param title
     * @param listener
     * @return
     */
    public static TimePickerView getTimePickerView(Context context, Calendar date, Calendar startDate,
                                                   Calendar endDate, boolean isShowHMS, String title,
                                                   OnTimeSelectListener listener, OnDialogListener onDialogListener,
                                                   Lifecycle lifecycle) {
        int color = ContextCompat.getColor(context, com.ara.base.R.color.colorAccent);
        TimePickerView timePickerView = new TimePickerBuilder(context, listener)
                .setLayoutRes(R.layout.pickerview_custom_time, v -> {
                    //自定义布局中的控件初始化及事件处理
                    final TextView tvTitle = v.findViewById(R.id.tv_title);
                    final ImageView ivClose = v.findViewById(R.id.iv_close);
                    final TextView tvCancel = v.findViewById(R.id.tv_cancel);
                    final TextView tvConfirm = v.findViewById(R.id.tv_confirm);
                    tvTitle.setText(title);
                    ivClose.setOnClickListener(v1 -> {
                        if (onDialogListener != null)
                            onDialogListener.onClose();

                    });
                    tvCancel.setOnClickListener(v12 -> {
                        if (onDialogListener != null)
                            onDialogListener.onCancel();
                    });
                    tvConfirm.setOnClickListener(v13 -> {
                        if (onDialogListener != null)
                            onDialogListener.onConfirm();
                    });
                })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, isShowHMS, isShowHMS, isShowHMS})
                .setLineSpacingMultiplier(2.5f)
                .setContentTextSize(20)
                .isCenterLabel(false)
                .setSubmitColor(color)
                .setCancelColor(color)
                .setDividerColor(color)
                .setOutSideCancelable(false)
                .setDate(date)
                .setRangDate(startDate, endDate)
                .isDialog(true)
                .build();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        timePickerView.getDialogContainerLayout().setLayoutParams(params);

        //获取当前Activity所在的窗体
        Window dialogWindow = timePickerView.getDialog().getWindow();
        //获得窗体的属性
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ScreenUtils.getScreenWidth();
            lp.width = lp.width - (lp.width / 6);
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            //底部
            dialogWindow.setGravity(Gravity.CENTER);
        }
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (timePickerView.isDialog()) timePickerView.dismiss();
            }
        });
        return timePickerView;
    }

    /**
     * 获取自定义时间对话框
     *
     * @param context
     * @param date
     * @param startDate
     * @param endDate
     * @param isShowHMS 是否显示时分秒
     * @param title
     * @param listener
     * @return
     */
    public static TimePickerView getTimePickerView(Context context, Calendar date, Calendar startDate,
                                                   Calendar endDate, boolean isShowHMS, String title,
                                                   String cancel, String confirm,
                                                   OnTimeSelectListener listener, OnDialogListener onDialogListener,
                                                   Lifecycle lifecycle) {

        int color = ContextCompat.getColor(context, com.ara.base.R.color.colorAccent);
        TimePickerView timePickerView = new TimePickerBuilder(context, listener)
                .setLayoutRes(R.layout.pickerview_custom_time, v -> {
                    //自定义布局中的控件初始化及事件处理
                    final TextView tvTitle = v.findViewById(R.id.tv_title);
                    final ImageView ivClose = v.findViewById(R.id.iv_close);
                    final TextView tvCancel = v.findViewById(R.id.tv_cancel);
                    final TextView tvConfirm = v.findViewById(R.id.tv_confirm);
                    tvTitle.setText(title);
                    tvCancel.setText(cancel);
                    tvConfirm.setText(confirm);
                    ivClose.setOnClickListener(v1 -> {
                        if (onDialogListener != null)
                            onDialogListener.onClose();

                    });
                    tvCancel.setOnClickListener(v12 -> {
                        if (onDialogListener != null)
                            onDialogListener.onCancel();
                    });
                    tvConfirm.setOnClickListener(v13 -> {
                        if (onDialogListener != null)
                            onDialogListener.onConfirm();
                    });
                })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, isShowHMS, isShowHMS, isShowHMS})
                .setLineSpacingMultiplier(2.5f)
                .setContentTextSize(20)
                .isCenterLabel(false)
                .setSubmitColor(color)
                .setCancelColor(color)
                .setDividerColor(color)
                .setOutSideCancelable(false)
                .setDate(date)
                .setRangDate(startDate, endDate)
                .isDialog(true)
                .build();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        timePickerView.getDialogContainerLayout().setLayoutParams(params);

        //获取当前Activity所在的窗体
        Window dialogWindow = timePickerView.getDialog().getWindow();
        //获得窗体的属性
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ScreenUtils.getScreenWidth();
            lp.width = lp.width - (lp.width / 6);
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            //底部
            dialogWindow.setGravity(Gravity.CENTER);
        }
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (timePickerView.isDialog()) timePickerView.dismiss();
            }
        });
        return timePickerView;
    }

    /**
     * 获取时间对话框
     *
     * @param context
     * @param date
     * @param startDate
     * @param endDate
     * @param listener
     * @return
     */
    public static TimePickerView getTimePickerViewOnlyYear(Context context, Calendar date, Calendar startDate,
                                                           Calendar endDate,
                                                           OnTimeSelectListener listener,
                                                           Lifecycle lifecycle) {
        int color = ContextCompat.getColor(context, com.ara.base.R.color.colorAccent);
        TimePickerView timePickerView = new TimePickerBuilder(context, listener)
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, false, false, false, false, false})
                .setLineSpacingMultiplier(2.5f)
                .setContentTextSize(16)
                .isCenterLabel(false)
                .setSubmitColor(color)
                .setCancelColor(color)
                .setDividerColor(color)
                .setDate(date)
                .setRangDate(startDate, endDate)
                .isDialog(true)
                .build();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        timePickerView.getDialogContainerLayout().setLayoutParams(params);

        //获取当前Activity所在的窗体
        Window dialogWindow = timePickerView.getDialog().getWindow();
        //获得窗体的属性
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ScreenUtils.getScreenWidth();
            lp.width = lp.width - (lp.width / 6);
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            //底部
            dialogWindow.setGravity(Gravity.CENTER);
        }
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (timePickerView.isDialog()) timePickerView.dismiss();
            }
        });
        return timePickerView;
    }
}
