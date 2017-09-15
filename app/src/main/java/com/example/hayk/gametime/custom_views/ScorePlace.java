package com.example.hayk.gametime.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.example.hayk.gametime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hayk on 17.08.2017.
 */

public class ScorePlace extends View {

    private Paint mPaint;
    private int mColorStroke = getResources().getColor(R.color.colorOfPen);
    int mTop = 0, mBottom = 0, mStart = 0, mEnd = 0;


    public ScorePlace(Context context) {
        this(context, null);
    }

    public ScorePlace(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScorePlace(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        startDrawing();
    }

    private void startDrawing() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mColorStroke);
        mPaint.setStrokeWidth(15);
        canvas.drawLine(canvas.getWidth() / 2, mStart + 100, canvas.getWidth() / 2, mEnd - 100, mPaint);
        canvas.drawLine(canvas.getWidth() / 4, mTop, canvas.getWidth() / 4 * 3, mTop, mPaint);
        canvas.drawLine(canvas.getWidth() / 4 + 100, mBottom, canvas.getWidth() / 4 * 3 - 100, mBottom, mPaint);
    }

    public void setParameters(int one, int two, int three, int four) {
        mTop = one;
        mBottom = two;
        mStart = three;
        mEnd = four;
        invalidate();
    }
}
