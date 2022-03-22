package com.ara.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by XieXin on 2018/12/10.
 * 是否可滑动RecyclerView
 */
public class ScrollLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled;

    public ScrollLinearLayoutManager(Context context, boolean isScrollEnabled) {
        super(context);
        this.isScrollEnabled = isScrollEnabled;
    }

    public ScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout, boolean isScrollEnabled) {
        super(context, orientation, reverseLayout);
        this.isScrollEnabled = isScrollEnabled;
    }

    public ScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean isScrollEnabled) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.isScrollEnabled = isScrollEnabled;
    }


    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled;
    }

    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnabled;
    }
}
