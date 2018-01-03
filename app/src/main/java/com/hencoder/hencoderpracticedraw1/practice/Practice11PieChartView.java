package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Practice11PieChartView extends View {

    private Paint mPaint;
    private Paint mLinePaint;
    private Paint mTextPaint;

    private List<DataBean> mDataList;

    //半径
    private static final int RADIUS = 300;
    //间隔角度
    private static final int INTERVAL_ANGLE = 2;
    //偏差长度
    private static final int DEVIATION_LENGTH = 30;
    //倾斜线的长度
    private static final int SLANT_LINE_LENGTH = 30;
    private static final int STRAIGHT_LINE_LENGTH = 50;
    private static final int TEXT_INTERVAL = 15;
    private static final int TEXT_SIZE = 28;

    {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(0xFFFFFFFF);
        mLinePaint.setStrokeWidth(3);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(0xFFFFFFFF);
        mTextPaint.setTextSize(TEXT_SIZE);

        mDataList = new ArrayList<>();
        mDataList.add(new DataBean(Color.YELLOW, 100, "Marshmallow", true));
        mDataList.add(new DataBean(Color.LTGRAY, 200, "Froyo"));
        mDataList.add(new DataBean(Color.GREEN, 50, "Gingerbread"));
        mDataList.add(new DataBean(Color.BLUE, 10, "Ice Cream Sandwich"));
        mDataList.add(new DataBean(Color.CYAN, 30, "Jelly Bean"));
        mDataList.add(new DataBean(Color.DKGRAY, 200, "KitKat"));
        mDataList.add(new DataBean(Color.RED, 150, "Lollipop"));
    }


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

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        int midWidth = getWidth() / 2;
        int midHeight = getHeight() / 2;

        BigDecimal startArc = BigDecimal.valueOf(0);
        int totalArc = 360 - (mDataList.size() * INTERVAL_ANGLE);

    }

    public static class DataBean {
        private static int total;

        @ColorInt
        private int color;
        private int dataNum;
        private String title;
        private boolean isDeviation = false;

        public DataBean(int color, int dataNum, String title) {
            this(color, dataNum, title, false);
        }

        public DataBean(int color, int dataNum, String title, boolean isDeviation) {
            this.color = color;
            this.dataNum = dataNum;
            this.title = title;
            this.isDeviation = isDeviation;
            total += dataNum;
        }

        /**
         * 使用Bigdecimal来避免float不精确的问题
         */
        public BigDecimal getPercentage() {
            return BigDecimal.valueOf(dataNum).divide(BigDecimal.valueOf(total), 4, RoundingMode.DOWN);
        }

        public int getColor() {
            return color;
        }

        public int getDataNum() {
            return dataNum;
        }

        public String getTitle() {
            return title;
        }
    }
}
