package com.tustar.demo.module.filemanager;

import android.os.Bundle;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.common.CommonDefine;
import com.tustar.demo.util.ToastUtils;

import java.io.File;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class AndroidGifDrawableActivity extends BaseActivity {

    private GifImageView mGifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_gif_drawable);

        mGifImageView = (GifImageView) findViewById(R.id.gif_image_view);

        if (new File(CommonDefine.TEST_GIF).exists()) {
            try {
                GifDrawable drawable = new GifDrawable(CommonDefine.TEST_GIF);
                mGifImageView.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showLong(this, R.string.image_no_exist);
        }
    }
}
