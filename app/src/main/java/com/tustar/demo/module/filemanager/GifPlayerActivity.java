package com.tustar.demo.module.filemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tustar.demo.R;
import com.tustar.demo.util.ToastUtils;

import java.io.File;


public class GifPlayerActivity extends Activity {

    private static final String TAG = "GifPlayerActivity";
    public static final String GIF_FILE_PATH = "com.zui.filemanager.gif_file_path";
    private String mFilePath;
    private GifView mGifView;
    private ProgressBar mGifProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.i(TAG, "onCreate :: ");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_player);

        Intent intent = getIntent();
        mFilePath = intent.getStringExtra(GIF_FILE_PATH);
        mGifView = (GifView) findViewById(R.id.gif_view);
        mGifProgressBar = (ProgressBar) findViewById(R.id.gif_progress);
        mGifView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mGifView.setDecodeListener(new GifView.DecodeListener() {
            @Override
            public void onDecodeStart() {
                Logger.i(TAG, "onDecodeStart :: ");
                mGifView.setVisibility(View.INVISIBLE);
                mGifProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDecodeEnd(boolean result) {
                Logger.d(TAG, "onDecodeEnd :: ");
                mGifView.setVisibility(View.VISIBLE);
                mGifProgressBar.setVisibility(View.GONE);
            }
        });
        if (new File(mFilePath).exists()) {
            mGifView.setGifSource(mFilePath);
        } else {
            ToastUtils.showLong(this, R.string.image_no_exist);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
