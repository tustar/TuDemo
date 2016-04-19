package com.tustar.demo.activity.anim.viewanim;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.tustar.demo.R;
import com.tustar.demo.activity.BaseActivity;

public class ViewAnimActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim);
    }

    public void btnAlpha(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        view.startAnimation(alphaAnimation);
    }

    public void btnRotate(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 100, 100);
        rotateAnimation.setDuration(1000);
        view.startAnimation(rotateAnimation);
    }

    public void btnRotateSelf(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF,
                Animation.RELATIVE_TO_SELF);
        rotateAnimation.setDuration(1000);
        view.startAnimation(rotateAnimation);
    }

    public void btnTranslate(View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 200);
        translateAnimation.setDuration(1000);
        view.startAnimation(translateAnimation);
    }

    public void btnScale(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 2, 0, 2);
        scaleAnimation.setDuration(1000);
        view.startAnimation(scaleAnimation);
    }

    public void btnScaleSelf(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        view.startAnimation(scaleAnimation);
    }

    public void btnAnimSet(View view) {
        AnimationSet animSet = new AnimationSet(true);
        animSet.setDuration(1000);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        animSet.addAnimation(alphaAnimation);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 100, 0, 200);
        translateAnimation.setDuration(1000);
        animSet.addAnimation(translateAnimation);

        view.startAnimation(translateAnimation);
    }
}
