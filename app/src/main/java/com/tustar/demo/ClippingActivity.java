package com.tustar.demo;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

public class ClippingActivity extends BaseActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipping);

        TextView mClippingRect = (TextView) findViewById(R.id.clipping_rect);
        TextView mClippingCircle = (TextView) findViewById(R.id.clipping_circle);

        // Clipping
        ViewOutlineProvider roundRectOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getWidth(), 30);
            }
        };
        ViewOutlineProvider cicleOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getWidth());
            }
        };

        mClippingRect.setOutlineProvider(cicleOutlineProvider);
        mClippingCircle.setOutlineProvider(roundRectOutlineProvider);
    }
}
