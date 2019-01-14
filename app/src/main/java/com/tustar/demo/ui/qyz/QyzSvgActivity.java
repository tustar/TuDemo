package com.tustar.demo.ui.qyz;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

public class QyzSvgActivity extends BaseActivity implements View.OnClickListener {

    ImageView mSvgImage;
    ImageView mSvgFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_svg);
        setTitle(R.string.qyz_svg_title);

        mSvgImage = findViewById(R.id.svg_image);
        mSvgImage.setOnClickListener(this);
        mSvgFace  = findViewById(R.id.svg_face);
        mSvgFace.setOnClickListener(this);
    }

    private void doAnimate(Drawable drawable) {
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            animatable.start();
        }
    }

    @Override
    public void onClick(View v) {
        Drawable drawable = null;
        switch (v.getId()) {
            case R.id.svg_image:
                drawable = mSvgImage.getDrawable();
                break;
            case R.id.svg_face:
                drawable = mSvgFace.getDrawable();
                break;
            default:
                break;
        }

        if (null != drawable) {
            doAnimate(drawable);
        }
    }
}
