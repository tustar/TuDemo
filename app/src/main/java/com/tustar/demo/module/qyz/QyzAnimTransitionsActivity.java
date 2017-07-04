package com.tustar.demo.module.qyz;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Window;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.common.IntentExtraKey;

public class QyzAnimTransitionsActivity extends BaseActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        int type = getIntent().getIntExtra(IntentExtraKey.TRANS_TYPE, 0);
        Transition transition = new Explode();
        switch (type) {
            case QyzAnimMainActivity.TRANS_EXPLODE:
                transition = new Explode();
                break;
            case QyzAnimMainActivity.TRANS_SLIDE:
                transition = new Slide();
                break;
            case QyzAnimMainActivity.TRANS_FADE:
                transition = new Fade();
                break;
            default:
                break;

        }
        window.setEnterTransition(transition);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_anim_transitions);
        setTitle(R.string.qyz_translation_title);
    }
}
