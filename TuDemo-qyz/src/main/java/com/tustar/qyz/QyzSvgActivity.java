package com.tustar.qyz;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class QyzSvgActivity extends AppCompatActivity implements View.OnClickListener {

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
        int id = v.getId();
        if (id == R.id.svg_image) {
            drawable = mSvgImage.getDrawable();
        } else if (id == R.id.svg_face) {
            drawable = mSvgFace.getDrawable();
        }

        if (null != drawable) {
            doAnimate(drawable);
        }
    }
}
