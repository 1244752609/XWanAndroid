package com.ara.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;


/**
 * Created by XieXin on 2020/6/23.
 * 圆点虚线
 */
public class DotLine extends View {
    private Paint paint;
    //圆数量X
    private int circleNumX;
    //绘制圆曲线后X轴剩余距离
    private int remindCircleX;
    //圆之间间距
    private float circleGap;
    //圆半径
    private float circleRadius;
    //圆颜色
    private int circleColor;

    public DotLine(Context context) {
        this(context, null);
    }

    public DotLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circleGap = SizeUtils.dp2px(11f);
        circleRadius = SizeUtils.dp2px(2f);
        circleColor = Color.GRAY;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        remindCircleX = (int) ((w - circleGap) % (2 * circleRadius + circleGap));
        circleNumX = (int) ((w - circleGap) / (2 * circleRadius + circleGap));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < circleNumX; i++) {
            float x = circleGap + circleRadius + (int) (remindCircleX / 2) + (circleGap + circleRadius * 2) * i;
            canvas.drawCircle(x, circleRadius, circleRadius, paint);
        }
    }
}
