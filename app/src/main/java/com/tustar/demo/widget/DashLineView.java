package com.tustar.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.tustar.demo.R;


/**
 * Created by tustar on 16-11-9.
 */

public class DashLineView extends View {

    public DashLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DashLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DashLineView(Context context) {
        super(context);
    }

    public DashLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getResources().getDimensionPixelSize(R.dimen.dash_divider_height);
        int left = 0;
        int top = height / 2;
        int right = left + getMeasuredWidth();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.dash_divider_dash_color));
        paint.setStrokeWidth(height);
        Path path = new Path();
        path.moveTo(left, top);
        path.lineTo(right, top);
        float dashWidth = getResources().getDimension(R.dimen.dash_divider_dash_width);
        float dashCap = getResources().getDimension(R.dimen.dash_divider_dash_gap);
        PathEffect effects = new DashPathEffect(new float[]{dashWidth, dashCap}, 0);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}