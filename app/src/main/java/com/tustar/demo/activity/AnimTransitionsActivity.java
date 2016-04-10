package com.tustar.demo.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Window;

import com.tustar.demo.R;
import com.tustar.demo.common.IntentExtraKey;

public class AnimTransitionsActivity extends BaseActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        int type = getIntent().getIntExtra(IntentExtraKey.TRANS_TYPE, 0);
        Transition transition = new Explode();
        switch (type) {
            case AnimMainActivity.TRANS_EXPLODE:
                transition = new Explode();
                break;
            case AnimMainActivity.TRANS_SLIDE:
                transition = new Slide();
                break;
            case AnimMainActivity.TRANS_FADE:
                transition = new Fade();
                break;
            default:
                break;

        }
        window.setEnterTransition(transition);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_transitions);
    }
}
