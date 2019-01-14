package com.tustar.demo.ui.anim.viewanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

public class HideActionBarActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "HideActionBarActivity";
    private TextView mHideActionBar;
    private TextView mHideSearchLayout;
    private ListView mHideListView;

    private ArrayAdapter<String> mAdatpter;
    private boolean mSearching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_action_bar);

        initActionBar();

        initSearchView();

        initListView();
    }

    private void initActionBar() {
        mHideActionBar = (TextView) findViewById(R.id.hide_action_bar);
    }

    private void initSearchView() {
        mHideSearchLayout = (TextView) findViewById(R.id.hide_search_layout);
        mHideSearchLayout.setOnClickListener(this);
    }

    private void initListView() {
        mHideListView = (ListView) findViewById(R.id.hide_list_view);
        mAdatpter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.content_data));
        mHideListView.setAdapter(mAdatpter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        if (mSearching) {
            showActionBar();
            return;
        }

        super.finish();
    }

    public View getActionBarView() {
        Window window = getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }

    private void hideActionBar() {
        if (mSearching) {
            return;
        }

        final int height = mHideActionBar.getMeasuredHeight();
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator actionbarAnimator = ObjectAnimator.ofFloat(mHideActionBar,
                View.TRANSLATION_Y, 0, -height);
        ObjectAnimator searchAnimator = ObjectAnimator.ofFloat(mHideSearchLayout,
                View.TRANSLATION_Y, 0, -height);
        ObjectAnimator listAnimator = ObjectAnimator.ofFloat(mHideListView,
                View.TRANSLATION_Y, 0, -height);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mHideActionBar.setVisibility(View.GONE);
                mHideActionBar.setTranslationY(0);
                mHideSearchLayout.setTranslationY(0);
                mHideListView.setTranslationY(0);
                mSearching = true;
            }
        });
        animatorSet.playTogether(actionbarAnimator, searchAnimator, listAnimator);
        animatorSet.start();
    }

    private void showActionBar() {
        if (!mSearching) {
            return;
        }

        final int height = mHideActionBar.getMeasuredHeight();
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator actionbarAnimator = ObjectAnimator.ofFloat(mHideActionBar,
                View.TRANSLATION_Y, -height, 0);
        ObjectAnimator searchAnimator = ObjectAnimator.ofFloat(mHideSearchLayout,
                View.TRANSLATION_Y, -height, 0);
        ObjectAnimator listAnimator = ObjectAnimator.ofFloat(mHideListView,
                View.TRANSLATION_Y, -height, 0);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mHideActionBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSearching = false;
            }
        });
        animatorSet.playTogether(actionbarAnimator, searchAnimator, listAnimator);
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hide_search_layout:
                hideActionBar();
                break;
            default:
                break;
        }
    }
}
