package com.tustar.demo.module.qyz;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SvgActivity extends BaseActivity {


    @BindView(R.id.svg_image)
    ImageView mSvgImage;
    @BindView(R.id.svg_face)
    ImageView mSvgFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        ButterKnife.bind(this);
    }

    private void doAnimate(Drawable drawable) {
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            animatable.start();
        }
    }

    @OnClick({R.id.svg_image, R.id.svg_face})
    public void onClick(View view) {
        Drawable drawable = null;
        switch (view.getId()) {
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
