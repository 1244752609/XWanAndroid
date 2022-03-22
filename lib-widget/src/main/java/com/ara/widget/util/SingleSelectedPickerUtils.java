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
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ScreenUtils;

import java.util.List;

/**
 * Created by XieXin on 2019/9/25.
 * 单选框工具类
 */
public class SingleSelectedPickerUtils {
    private SingleSelectedPickerUtils() {
    }

    /**
     * 获取单选选择器
     *
     * @return OptionsPickerView
     */
    public static OptionsPickerView getSingleSelected(Context context, String title, int colorResId, int textColorResId,
                                                      List<String> items, OnOptionsSelectListener listener,
                                                      Lifecycle lifecycle) {
        OptionsPickerView options = new OptionsPickerBuilder(context, listener)
                .setTitleText(title)
                .setDividerColor(ContextCompat.getColor(context, colorResId))
                .setTextColorCenter(ContextCompat.getColor(context, textColorResId))
                .setCancelColor(ContextCompat.getColor(context, colorResId))
                .setSubmitColor(ContextCompat.getColor(context, colorResId))
                .setContentTextSize(20)
                .setLineSpacingMultiplier(2.5f)
                .isDialog(true)
                .build();
        options.setPicker(items);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        options.getDialogContainerLayout().setLayoutParams(params);

        //获取当前Activity所在的窗体
        Window dialogWindow = options.getDialog().getWindow();
        //获得窗体的属性
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ScreenUtils.getScreenWidth();
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            //底部
            dialogWindow.setGravity(Gravity.BOTTOM);
        }
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (options.isDialog()) options.dismiss();
            }
        });
        return options;
    }

    /**
     * 获取单选选择器
     *
     * @return OptionsPickerView
     */
    public static OptionsPickerView getSingleSelected(Context context, String title, int colorResId, int textColorResId,
                                                      List<String> items,
                                                      OnOptionsSelectListener listener, OnDialogListener onDialogListener,
                                                      Lifecycle lifecycle) {
        OptionsPickerView options = new OptionsPickerBuilder(context, listener)
                .setLayoutRes(R.layout.pickerview_custom_options, v -> {
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
                .setTitleText(title)
                .setDividerColor(ContextCompat.getColor(context, colorResId))
                .setTextColorCenter(ContextCompat.getColor(context, textColorResId))
                .setCancelColor(ContextCompat.getColor(context, colorResId))
                .setSubmitColor(ContextCompat.getColor(context, colorResId))
                .setContentTextSize(20)
                .setOutSideCancelable(false)
                .setLineSpacingMultiplier(2.5f)
                .isDialog(true)
                .build();

        options.setPicker(items);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        options.getDialogContainerLayout().setLayoutParams(params);

        //获取当前Activity所在的窗体
        Window dialogWindow = options.getDialog().getWindow();
        //获得窗体的属性
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ScreenUtils.getScreenWidth();
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            //底部
            dialogWindow.setGravity(Gravity.BOTTOM);
        }
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (options.isDialog()) options.dismiss();
            }
        });
        return options;
    }

    /**
     * 获取单选选择器
     *
     * @return OptionsPickerView
     */
    public static OptionsPickerView getSingleSelected(Context context, String title, int colorResId, int textColorResId,
                                                      String cancel, String confirm, List<String> items,
                                                      OnOptionsSelectListener listener, OnDialogListener onDialogListener,
                                                      Lifecycle lifecycle) {
        OptionsPickerView options = new OptionsPickerBuilder(context, listener)
                .setLayoutRes(R.layout.pickerview_custom_options, v -> {
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
                .setTitleText(title)
                .setDividerColor(ContextCompat.getColor(context, colorResId))
                .setTextColorCenter(ContextCompat.getColor(context, textColorResId))
                .setCancelColor(ContextCompat.getColor(context, colorResId))
                .setSubmitColor(ContextCompat.getColor(context, colorResId))
                .setContentTextSize(20)
                .setOutSideCancelable(false)
                .setLineSpacingMultiplier(2.5f)
                .isDialog(true)
                .build();

        options.setPicker(items);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        options.getDialogContainerLayout().setLayoutParams(params);

        //获取当前Activity所在的窗体
        Window dialogWindow = options.getDialog().getWindow();
        //获得窗体的属性
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ScreenUtils.getScreenWidth();
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            //底部
            dialogWindow.setGravity(Gravity.BOTTOM);
        }
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (options.isDialog()) options.dismiss();
            }
        });
        return options;
    }

    /**
     * 获取单选选择器
     *
     * @return OptionsPickerView
     */
    public static OptionsPickerView getSingleSelected(Context context, String title, int colorResId, int textColorResId,
                                                      String cancel, String confirm, List<String> items, int selectOptions,
                                                      OnOptionsSelectListener listener, OnDialogListener onDialogListener,
                                                      Lifecycle lifecycle) {
        OptionsPickerView options = new OptionsPickerBuilder(context, listener)
                .setLayoutRes(R.layout.pickerview_custom_options, v -> {
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
                .setTitleText(title)
                .setDividerColor(ContextCompat.getColor(context, colorResId))
                .setTextColorCenter(ContextCompat.getColor(context, textColorResId))
                .setCancelColor(ContextCompat.getColor(context, colorResId))
                .setSubmitColor(ContextCompat.getColor(context, colorResId))
                .setContentTextSize(20)
                .setOutSideCancelable(false)
                .setLineSpacingMultiplier(2.5f)
                .setSelectOptions(selectOptions)
                .isDialog(true)
                .build();

        options.setPicker(items);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        options.getDialogContainerLayout().setLayoutParams(params);

        //获取当前Activity所在的窗体
        Window dialogWindow = options.getDialog().getWindow();
        //获得窗体的属性
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ScreenUtils.getScreenWidth();
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
            //底部
            dialogWindow.setGravity(Gravity.BOTTOM);
        }
        lifecycle.addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (options.isDialog()) options.dismiss();
            }
        });
        return options;
    }
}
