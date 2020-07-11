package com.tustar.qyz;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class QyzAnimMainActivity extends AppCompatActivity {

    public static final int TRANS_EXPLODE = 0;
    public static final int TRANS_SLIDE = 1;
    public static final int TRANS_FADE = 2;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_anim_main);
        setTitle(R.string.qyz_translation_title);
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
                getString(R.string.qyz_transition_name_share)).toBundle());
    }

    public void fab(View view) {
        View mAnimShare = findViewById(R.id.anim_share);
        Intent intent = createIntent(type);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(mAnimShare, getString(R.string.qyz_transition_name_share)),
                Pair.create(view, getString(R.string.qyz_transition_name_fab))).toBundle());
    }

    private Intent createIntent(int type) {
        Intent intent = new Intent(this, QyzAnimTransitionsActivity.class);
        intent.putExtra("trans_type", type);
        return intent;
    }

    private void startTransActivity() {
        Intent intent = createIntent(type);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
