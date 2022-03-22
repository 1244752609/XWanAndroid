package com.ara.project_common.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.ara.project_common.R;
import com.blankj.utilcode.util.BarUtils;


/**
 * Created by XieXin on 2021/5/12.
 * 标题栏
 */
public class TitleBar extends FrameLayout {
    private final View view;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvRight;
    private OnClickListener backOnClickListener;
    private OnClickListener rightOnClickListener;

    /*** 顶部外边距是否添加状态栏高度 */
    private boolean isAddBarHeight;

    public TitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = inflate(context, R.layout.title_bar, this);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        ivBack = view.findViewById(R.id.ivBack);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvRight = view.findViewById(R.id.tvRight);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        try {
            tvTitle.setText(a.getText(R.styleable.TitleBar_barTitleText));
            int titleColor = a.getColor(R.styleable.TitleBar_barTitleTextColor, 0);
            if (titleColor != 0) tvTitle.setTextColor(titleColor);
            int titleSize = a.getDimensionPixelSize(R.styleable.TitleBar_barTitleTextSize, 0);
            if (titleSize != 0) tvTitle.setTextColor(titleColor);

            CharSequence titleText = a.getText(R.styleable.TitleBar_barRightText);
            boolean isShowRight = a.getBoolean(R.styleable.TitleBar_barIsShowRight, true);
            if (isShowRight && titleText != null && titleText.length() > 0) {
                tvRight.setVisibility(VISIBLE);
                tvRight.setText(titleText);
                int rightColor = a.getColor(R.styleable.TitleBar_barRightTextColor, 0);
                if (rightColor != 0) tvRight.setTextColor(titleColor);
                int rightSize = a.getDimensionPixelSize(R.styleable.TitleBar_barRightTextSize, 0);
                if (rightSize != 0) tvRight.setTextColor(titleColor);
            } else {
                tvRight.setVisibility(GONE);
            }

            int backIcon = a.getResourceId(R.styleable.TitleBar_barBackIcon, 0);
            if (backIcon != 0) ivBack.setImageResource(backIcon);
            boolean isBack = a.getBoolean(R.styleable.TitleBar_barIsBack, true);
            ivBack.setVisibility(isBack ? VISIBLE : GONE);
            if (isBack) ivBack.setOnClickListener(v -> ((Activity) context).finish());

            isAddBarHeight = a.getBoolean(R.styleable.TitleBar_barIsAddBarHeight, true);
        } finally {
            a.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isAddBarHeight) BarUtils.addMarginTopEqualStatusBarHeight(view);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setShowBack(boolean showBack) {
        if (ivBack != null) ivBack.setVisibility(showBack ? VISIBLE : GONE);
    }

    public void setTitle(@StringRes int resId) {
        if (tvTitle != null) tvTitle.setText(resId);
    }

    public void setTitle(String title) {
        if (tvTitle != null) tvTitle.setText(title);
    }

    public void setRightText(@StringRes int resId) {
        if (tvRight != null && resId != 0) {
            tvRight.setText(resId);
            tvRight.setVisibility(VISIBLE);
        }
    }

    public void setRightText(String title) {
        if (tvRight != null && !TextUtils.isEmpty(title)) {
            tvRight.setText(title);
            tvRight.setVisibility(VISIBLE);
        }
    }

    public void setRightVisible(boolean visible) {
        if (tvRight != null) {
            tvRight.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    public void setBackOnClickListener(OnClickListener onClickListener) {
        this.backOnClickListener = onClickListener;
        ivBack.setOnClickListener(v -> {
            if (backOnClickListener != null) backOnClickListener.onClick(v);
        });
    }

    public void setRightOnClickListener(OnClickListener onClickListener) {
        this.rightOnClickListener = onClickListener;
        tvRight.setOnClickListener(v -> {
            if (rightOnClickListener != null) rightOnClickListener.onClick(v);
        });
    }
}
