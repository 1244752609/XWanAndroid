package com.ara.widget.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Created by XieXin on 2021/7/26.
 * 正方形ConstraintLayout
 */
public class SquareConstraintLayout extends ConstraintLayout {
    public SquareConstraintLayout(@NonNull  Context context) {
        super(context);
    }

    public SquareConstraintLayout(@NonNull  Context context, @Nullable  AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareConstraintLayout(@NonNull  Context context, @Nullable  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
