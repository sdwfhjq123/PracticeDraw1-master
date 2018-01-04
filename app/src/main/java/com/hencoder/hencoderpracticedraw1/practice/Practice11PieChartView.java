package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Practice11PieChartView extends View {

    private int mHeight, mWidth;//宽高
    private Paint mPaint;//扇形的画笔
    private Paint mTextPaint;//画文字的画笔

    private int centerX, centerY;//中心坐标

    //"其他"的value
    //扇形图分成太多快 所以要合并一部分为其他 即图中灰色部分
    private double rest;

    private int maxNum = 5;//扇形图的最大块数 超过的item就合并到其他

    String others = "其他";//“其他”块要显示的文字
    double total;//数据的总和
    double[] datas;//数据集
    String[] texts;//每个数据对应的文字集

    //颜色 默认的颜色
    private int[] mColors = {
            Color.parseColor("#FF4081"), Color.parseColor("#ffc0cb"),
            Color.parseColor("#00ff00"), Color.parseColor("#0066ff"), Color.parseColor("#ffee00")
    };

    private int mTextSize;//文字大小  单位：像素

    private int radius = 500;//半径 在画图时初始化

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //无数据 直接返回
        if (datas == null || datas.length == 0) return;

        centerX = (getRight() - getLeft()) / 2;
        centerY = (getBottom() - getTop()) / 2;

        int min = mHeight > mWidth ? mWidth : mHeight;
        if (radius > min / 2) {
            radius = (int) ((min - getPaddingTop() - getPaddingBottom()) / 3.5);
        }

        //画各个扇形
        drawCircle(canvas);

        //画线与文字
        drawLineAndText(canvas);
    }

    private void drawLineAndText(Canvas canvas) {
        int start = 0;
        //平移画布到中心，所以下面的坐标是从中点开始算起的
        canvas.translate(centerX, centerY);
        mPaint.setStrokeWidth(4);

        //如果数据集过大，那么要合并到其他
        for (int i = 0; i < (maxNum < datas.length ? maxNum : datas.length); i++) {
            float angles = (float) ((datas[i] * 1.0f / total) * 360);
            //画线条和文字
            drawLine(canvas, start, angles, texts[i], mColors[i % mColors.length]);
            start += angles;
        }

        //画其他部分的线条和文字
        if (start < 360) {
            drawLine(canvas, start, 360 - start, others, Color.GRAY);
        }
    }

    private void drawLine(Canvas canvas, int start, float angles, String text, int color) {
        mPaint.setColor(color);
        float stopX, stopY;
        stopX = (float) ((radius + 40) * Math.cos((2 * start + angles) / 2 * Math.PI / 180));
        stopY = (float) ((radius + 40) * Math.sin((2 * start + angles) / 2) * Math.PI / 180);

        canvas.drawLine((float) ((radius - 20) * Math.cos((2 * start + angles) / 2 * Math.PI / 180)),
                (float) ((radius - 20) * Math.sin((2 * start + angles) / 2 * Math.PI / 180)),
                stopX, stopY, mPaint);

        //画横线
        int dx;//判断横线是话在左边还是右边
        int endX;
        if (stopX > 0) {
            endX = (centerX - getPaddingRight() - 20);
        } else {
            endX = (-centerX + getPaddingLeft() + 20);
        }
        //画横线
        canvas.drawLine(stopX, stopY, endX, stopY, mPaint);
        dx = (int) (endX - stopX);

        //测量文字大小
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width();
        int h = rect.height();
        int offset = 20;//文字在横线的偏移量
        //画文字 文字的Y坐标值的是文字底部的Y坐标
        canvas.drawText(text, 0, text.length(), dx > 0 ? stopX + offset : stopX - w - offset, stopY + h, mTextPaint);

        //测量百分比大小
        String percentage = angles / 3.60 + "";
        percentage = percentage.substring(0, percentage.length() > 4 ? 4 : percentage.length()) + "%";
        mTextPaint.getTextBounds(percentage, 0, percentage.length(), rect);
        w = rect.width() - 10;
        //画百分比
        canvas.drawText(percentage, 0, percentage.length(), dx > 0 ? stopX + offset : stopX - w - offset, stopY - 5, mTextPaint);

    }

    private void drawCircle(Canvas canvas) {
        int centerX = (getRight() - getLeft()) / 2;//中点
        int centerY = (getBottom() - getTop()) / 2;
        RectF rectF = new RectF((float) (centerX - radius), centerY - radius, centerX + radius, centerY + radius);

        int start = 0;//扇形开始的角度
        for (int i = 0; i < (maxNum < datas.length ? maxNum : datas.length); i++) {
            float angles = (float) ((datas[i] * 1.0f / total) * 360);//计算扇形的角度
            mPaint.setColor(mColors[i % mColors.length]);//颜色
            canvas.drawArc(rectF, start, angles, true, mPaint);
            start += angles;//下一个扇形开始的角度
        }

        //画other部分
        rest = 0;//保存其他部分的value
        for (int i = maxNum; i < datas.length; i++) {
            float angles = (float) 360 - start;//other角度
            mPaint.setColor(Color.GRAY);
            canvas.drawArc(rectF, start, angles, true, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高 不要设置wrap_content
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    public abstract class ArcViewAdapter<T> {

        public void setData(List<T> list) {
            datas = new double[list.size()];
            texts = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                total += getValue(list.get(i));
                datas[i] = getValue(list.get(i));
                texts[i] = getText(list.get(i));
            }
            invalidate();//请求重绘
        }

        //通过传来的数据集的某个元素  得到具体的数字
        public abstract double getValue(T t);

        //通过传来的数据集的某个元素  得到具体的描述
        public abstract String getText(T t);
    }

}
