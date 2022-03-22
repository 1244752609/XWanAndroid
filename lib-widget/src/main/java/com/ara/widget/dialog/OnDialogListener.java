package com.ara.widget.dialog;

/**
 * Created by XieXin on 2019/9/25.
 * 对话框监听事件
 */
public interface OnDialogListener {
    default void onClose() {
    }

    default void onCancel() {
    }

    default void onConfirm() {
    }
}
