package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {
    private static final int IMAGE_WIDTH = 600;
    private static final int IMAGE_HEIGHT = 400;
    private static final int START_X = 60;
    private static final int START_Y = 20;
    private static final int INTERVAL = 15;

    private static final String[] TEXTS = {"Froyo", "GB", "ICS", "JB", "KitKat", "L", "M"};
    private static final int[] HEIGHTS = {50, 100, 150, 400, 400, 200, 50};

    private static final int COLUMN_WIDTH = (IMAGE_WIDTH - (TEXTS.length + 1) * INTERVAL) / TEXTS.length;

    private Paint paint;

    {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
    }

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        paint.setColor(Color.WHITE);
        //坐标轴
        canvas.drawLine(START_X, START_Y, START_X, START_Y + IMAGE_HEIGHT, paint);
        canvas.drawLine(START_X, START_Y + IMAGE_HEIGHT, START_X + IMAGE_WIDTH, START_Y + IMAGE_HEIGHT, paint);

        //坐标轴底部文字
        paint.setTextSize(25);
        for (int i = 0; i < TEXTS.length; i++) {
            canvas.drawText(TEXTS[i],
                    START_X + (i + 1) * INTERVAL + i * COLUMN_WIDTH + COLUMN_WIDTH / 2 - paint.measureText(TEXTS[i]) / 2,
                    START_Y + IMAGE_HEIGHT + 20, paint);
        }

        //底部文字
        paint.setTextSize(35);
        canvas.drawText("直方图", START_X + (IMAGE_WIDTH / 2) - 50, START_Y + IMAGE_HEIGHT + 60, paint);

        //柱状图
        paint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(START_X + INTERVAL, START_Y + IMAGE_HEIGHT - HEIGHTS[0]);
        for (int i = 0; i < HEIGHTS.length; i++) {
            path.addRect(START_X + (i + 1) * INTERVAL + i * COLUMN_WIDTH,
                    START_Y + IMAGE_HEIGHT - HEIGHTS[i],
                    START_X + (i + 1) * (INTERVAL + COLUMN_WIDTH),
                    START_Y + IMAGE_HEIGHT, Path.Direction.CW);
        }
        canvas.drawPath(path, paint);
    }
}
