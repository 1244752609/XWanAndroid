package com.ara.widget.recyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by XieXin on 2019/1/20.
 * 自定义RecyclerView横线
 */
public class XItemDecoration extends RecyclerView.ItemDecoration {
    /*** 默认边距 */
    private int mMargin = 1;
    /*** 两边边距 */
    private int mBothSidesWidth = 0;

    private int mColor = 0;
    private Paint mPaint;

    public XItemDecoration() {
    }

    public XItemDecoration(int margin, int color) {
        this.mMargin = margin;
        this.mColor = color;
        if (color > 0) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(color);
        }
    }

    public XItemDecoration(int bothSidesWidth) {
        this.mBothSidesWidth = bothSidesWidth;
    }

    public XItemDecoration(int margin, int bothSidesWidth, int color) {
        this.mMargin = margin;
        this.mBothSidesWidth = bothSidesWidth;
        this.mColor = color;
        if (color > 0) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(color);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        setLinearLayoutSpaceItemDecoration(outRect, view, parent, state);
    }

    /**
     * LinearLayoutManager设置间距（此方法最左边和最右边间距为设置的一半）
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    private void setLinearLayoutSpaceItemDecoration(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mColor > 0) return;
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
            if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                outRect.bottom = 0;
                outRect.top = 0;
                //获取当前Item的position
                int position = parent.getChildAdapterPosition(view);
                //获得Item的数量
                int itemCount = parent.getAdapter().getItemCount();
                if (position == 0) {
                    outRect.left = SizeUtils.dp2px((float) mBothSidesWidth);
                } else {
                    outRect.left = mMargin;
                }
                if (position == itemCount - 1) {
                    outRect.right = SizeUtils.dp2px((float) mBothSidesWidth);
                } else {
                    outRect.right = mMargin;
                }
            } else {
                outRect.left = 0;
                outRect.right = 0;
                //获取当前Item的position
                int position = parent.getChildAdapterPosition(view);
                //获得Item的数量
                int itemCount = parent.getAdapter().getItemCount();
                if (position == 0) {
                    outRect.top = SizeUtils.dp2px((float) mBothSidesWidth);
                } else {
                    outRect.top = mMargin;
                }
                if (position == itemCount - 1) {
                    outRect.bottom = SizeUtils.dp2px((float) mBothSidesWidth);
                } else {
                    outRect.bottom = mMargin;
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mColor > 0) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
                //获得Item的数量
                int itemCount = parent.getAdapter().getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    View view = parent.getChildAt(i);
                    float dividerTop;
                    float dividerLeft;
                    float dividerBottom;
                    float dividerRight;
                    if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                        //获取当前Item的position
                        int position = parent.getChildAdapterPosition(view);
                        dividerTop = parent.getPaddingTop();
                        dividerLeft = (float) view.getLeft() - mMargin;
                        dividerBottom = (float) view.getWidth() - parent.getPaddingBottom();
                        dividerRight = view.getLeft();
                        if (position == 0) {
                            dividerLeft = (float) parent.getPaddingLeft() - SizeUtils.dp2px((float) mBothSidesWidth);
                        }
                        if (position == itemCount - 1) {
                            dividerLeft = (float) parent.getPaddingLeft() - SizeUtils.dp2px((float) mBothSidesWidth);
                        }
                    } else {
                        //获取当前Item的position
                        int position = parent.getChildAdapterPosition(view);
                        dividerTop = (float) view.getTop() - mMargin;
                        dividerLeft = parent.getPaddingLeft();
                        dividerBottom = view.getTop();
                        dividerRight = (float) parent.getWidth() - parent.getPaddingRight();
                        if (position == 0) {
                            dividerTop = (float) view.getTop() - SizeUtils.dp2px((float) mBothSidesWidth);
                        }
                        if (position == itemCount - 1) {
                            dividerTop = (float) view.getTop() - SizeUtils.dp2px((float) mBothSidesWidth);
                        }
                    }
                    c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
                }
            }
        }
    }
}
