package com.tustar.demo.ui.qyz.systemWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by tustar on 4/16/16.
 */
public class ShineTextView extends AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mWidth;
    private int mDx;

    public ShineTextView(Context context) {
        super(context);
    }

    public ShineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != mGradientMatrix) {
            mDx += mWidth / 5;
            if (mDx > 2 * mWidth) {
                mDx = -mWidth;
            }

            mGradientMatrix.setTranslate(mDx, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (0 == mWidth) {
            mWidth = getMeasuredWidth();
            if (mWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0,
                        0,
                        mWidth,
                        0,
                        new int[]{Color.RED, Color.GREEN, Color.BLUE},
                        null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }
}