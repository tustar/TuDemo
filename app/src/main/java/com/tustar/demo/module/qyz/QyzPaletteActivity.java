package com.tustar.demo.module.qyz;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.TextView;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.util.ColorUtils;
import com.tustar.demo.util.Logger;


public class QyzPaletteActivity extends BaseActivity {

    private static final String TAG = QyzPaletteActivity.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_palette);
        setTitle(R.string.qyz_palette_title);

        // TextView
        final TextView mRow0Col0 = (TextView) findViewById(R.id.row_0_col_0);
        final TextView mRow0Col1 = (TextView) findViewById(R.id.row_0_col_1);
        final TextView mRow0Col2 = (TextView) findViewById(R.id.row_0_col_2);
        final TextView mRow1Col0 = (TextView) findViewById(R.id.row_1_col_0);
        final TextView mRow1Col1 = (TextView) findViewById(R.id.row_1_col_1);
        final TextView mRow1Col2 = (TextView) findViewById(R.id.row_1_col_2);

        // Palette
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.me);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Vibrant
                final Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (null != vibrant) {
                    mRow0Col0.setBackgroundColor(vibrant.getRgb());
                    mRow0Col0.setText("Vibrant\n" + ColorUtils.rgbToHex(vibrant.getRgb()));
                    mRow0Col0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateActionBar(vibrant.getRgb());
                        }
                    });
                } else {
                    Logger.w(TAG, "onGenerated :: vibrant is null");
                }
                // Dark Vibrant
                final Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                if (null != darkVibrant) {
                    mRow0Col1.setBackgroundColor(darkVibrant.getRgb());
                    mRow0Col1.setText("Dark Vibrant\n" + ColorUtils.rgbToHex(darkVibrant.getRgb()));
                    mRow0Col1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateActionBar(darkVibrant.getRgb());
                        }
                    });
                } else {
                    Logger.w(TAG, "onGenerated :: darkVibrant is null");
                }
                // Light Vibrant
                final Palette.Swatch lightVibrant = palette.getLightVibrantSwatch();
                if (null != lightVibrant) {
                    mRow0Col2.setBackgroundColor(lightVibrant.getRgb());
                    mRow0Col2.setText("Light Vibrant\n" + ColorUtils.rgbToHex(lightVibrant.getRgb()));
                    mRow0Col2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateActionBar(lightVibrant.getRgb());
                        }
                    });
                } else {
                    Logger.w(TAG, "onGenerated  :: lightVibrant is null");
                }
                // Muted
                final Palette.Swatch muted = palette.getMutedSwatch();
                if (null != muted) {
                    mRow1Col0.setBackgroundColor(muted.getRgb());
                    mRow1Col0.setText("Muted\n" + ColorUtils.rgbToHex(muted.getRgb()));
                    mRow1Col0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateActionBar(muted.getRgb());
                        }
                    });
                } else {
                    Logger.w(TAG, "onGenerated :: muted is null");
                }
                // Dark Muted
                final Palette.Swatch darkMuted = palette.getDarkMutedSwatch();
                if (null != darkMuted) {
                    mRow1Col1.setBackgroundColor(darkMuted.getRgb());
                    mRow1Col1.setText("Dark Muted\n" + ColorUtils.rgbToHex(darkMuted.getRgb()));
                    mRow1Col1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateActionBar(darkMuted.getRgb());
                        }
                    });
                } else {
                    Logger.w(TAG, "onGenerated :: darkMuted is null");
                }
                // Light Muted
                final Palette.Swatch lightMuted = palette.getLightMutedSwatch();
                if (null != lightMuted) {
                    mRow1Col2.setBackgroundColor(lightMuted.getRgb());
                    mRow1Col2.setText("Light Muted\n" + ColorUtils.rgbToHex(lightMuted.getRgb()));
                    mRow1Col2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateActionBar(lightMuted.getRgb());
                        }
                    });
                } else {
                    Logger.w(TAG, "onGenerated :: lightMuted is null");
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateActionBar(int color) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        getWindow().setStatusBarColor(color);
    }
}
