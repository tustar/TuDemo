package com.tustar.demo.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.tustar.demo.R;
import com.tustar.demo.common.IntentExtraKey;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AnimMainActivity extends BaseActivity {

    public static final int TRANS_EXPLODE = 0;
    public static final int TRANS_SLIDE = 1;
    public static final int TRANS_FADE = 2;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_main);
    }


    public void explode(View view) {
        type = TRANS_EXPLODE;
        startTransActivity();
    }

    public void slide(View view) {
        type = TRANS_SLIDE;
        startTransActivity();
    }

    public void fade(View view) {
        type = TRANS_FADE;
        startTransActivity();
    }

    public void share(View view) {
        Intent intent = createIntent(type);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view,
                getString(R.string.transition_name_share)).toBundle());
    }

    public void fab(View view) {
        View mAnimShare = findViewById(R.id.anim_share);
        Intent intent = createIntent(type);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(mAnimShare, getString(R.string.transition_name_share)),
                Pair.create(view, getString(R.string.transition_name_fab))).toBundle());
    }

    private Intent createIntent(int type) {
        Intent intent = new Intent(this, AnimTransitionsActivity.class);
        intent.putExtra(IntentExtraKey.TRANS_TYPE, type);
        return intent;
    }

    private void startTransActivity() {
        Intent intent = createIntent(type);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
