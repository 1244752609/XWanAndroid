package com.ara.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by XieXin on 2021/5/10.
 * 信封分割线
 */
public class MailLineView extends View {
    private float colorWidth = 8;//颜色区域宽度
    private float emptyWidth = 8;//间隔宽度
    private Paint paint;
    private Path path;

    public MailLineView(Context context) {
        this(context, null);
    }

    public MailLineView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MailLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    /**
     * 初始化画笔
     */
    public void initPaint() {
        paint = new Paint();
        path = new Path();
        paint.setAntiAlias(true);
        colorWidth = dp2px(getContext(), colorWidth);
        emptyWidth = dp2px(getContext(), emptyWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float viewHeight = getHeight();
        float drawLength = 0;
        int count = 0;
        while (drawLength < getWidth()) {
            if (count != 0) drawLength += emptyWidth;
            /*设置paint的颜色*/
            if (count % 2 == 0) {
                paint.setColor(Color.parseColor("#5D8AD0"));
            } else {
                paint.setColor(Color.parseColor("#D05D5D"));
            }
            /*开始画多边形*/
            path.reset();
            path.moveTo(drawLength, viewHeight);// 此点为多边形的起点
            path.lineTo(drawLength + colorWidth - viewHeight, viewHeight);
            path.lineTo(drawLength + colorWidth, 0);
            path.lineTo(drawLength + viewHeight, 0);
            path.close(); // 使这些点构成封闭的多边形
            canvas.drawPath(path, paint);
            drawLength += colorWidth;
            count++;
        }
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, Float dpValue) {
        Float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
