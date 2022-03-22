package com.ara.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by XieXin on 2019/3/14.
 * 输入框工具类
 */
public class EditTextUtils {
    /*** 小数点后的位数 */
    private static final int POINTER_LENGTH = 2;

    private static final String POINTER = ".";

    private static final String ZERO = "0";

    private static String number;
    private static int curSelection;

    private EditTextUtils() {
    }

    /***
     * 保留两位小数
     * @param editText
     * @param length 整数数字长度
     */
    @SuppressLint("SetTextI18n")
    public static void keepTwoDecimals(EditText editText, int length) {
        number = editText.getText().toString();
        //第一位不能输入小点
        if (number.length() == 1 && TextUtils.equals(number.substring(0, 1), POINTER)) {
            editText.setText("");
            return;
        }

        //第一位0时，后续不能输入其他数字
        if (number.length() > 1 && TextUtils.equals(number.substring(0, 1), ZERO) &&
                !TextUtils.equals(number.substring(1, 2), POINTER)) {
            editText.setText(number.substring(0, 1));
            editText.setSelection(editText.length());
            return;
        }

        String[] numbers = number.split("\\.");
        //已经输入小数点的情况下
        if (numbers.length == 2) {
            //小数处理
            int decimalsLength = numbers[1].length();
            if (decimalsLength > POINTER_LENGTH) {
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, numbers[0].length() + 1 + POINTER_LENGTH));
                editText.setSelection(Math.min(curSelection, number.length()));
            }
            //整数处理
            if (numbers[0].length() > length) {
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, length) + number.substring(length + 1));
                editText.setSelection(Math.min(curSelection, length));
            }
        } else {
            //整数处理
            if (editText.length() > length) {
                if (number.contains(POINTER)) return;
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, length));
                editText.setSelection(Math.min(curSelection, length));
            }
        }
    }

    /***
     * 保留小数
     * @param editText
     * @param length 整数数字长度
     * @param pointerLength 小数点后的位数长度
     */
    @SuppressLint("SetTextI18n")
    public static void keepDecimals(EditText editText, int length, int pointerLength) {
        number = editText.getText().toString();
        //第一位不能输入小点
        if (number.length() == 1 && TextUtils.equals(number.substring(0, 1), POINTER)) {
            editText.setText("");
            return;
        }

        //第一位0时，后续不能输入其他数字
        if (number.length() > 1 && TextUtils.equals(number.substring(0, 1), ZERO) &&
                !TextUtils.equals(number.substring(1, 2), POINTER)) {
            editText.setText(number.substring(0, 1));
            editText.setSelection(editText.length());
            return;
        }

        String[] numbers = number.split("\\.");
        //已经输入小数点的情况下
        if (numbers.length == 2) {
            //小数处理
            int decimalsLength = numbers[1].length();
            if (decimalsLength > pointerLength) {
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, numbers[0].length() + 1 + pointerLength));
                editText.setSelection(Math.min(curSelection, number.length()));
            }
            //整数处理
            if (numbers[0].length() > length) {
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, length) + number.substring(length + 1));
                editText.setSelection(Math.min(curSelection, length));
            }
        } else {
            //整数处理
            if (editText.length() > length) {
                if (number.contains(POINTER)) return;
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, length));
                editText.setSelection(Math.min(curSelection, length));
            }
        }
    }
}
