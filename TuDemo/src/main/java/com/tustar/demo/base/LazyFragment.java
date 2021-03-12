package com.tustar.demo.base;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tustar.demo.util.Logger;

abstract class LazyFragment extends Fragment {

    protected View mRootView;
    protected boolean mViewCreated = false;
    protected boolean mCurrentVisibleState = false;
    private SparseArray<View> mCacheViews = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutRes(), container, false);
        }
        initView(mRootView);
        mViewCreated = true;
        if (getUserVisibleHint() && !isHidden()) {
            dispatchUserVisibleHint(true);
        }
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Logger.d("isVisibleToUser = " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if (mViewCreated) {
            if (!mCurrentVisibleState && isVisibleToUser) {
                dispatchUserVisibleHint(true);
            } else if (mCurrentVisibleState && !isVisibleToUser) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    private void dispatchUserVisibleHint(boolean visibleState) {
        Logger.d("visibleState = " + visibleState);
        if (mCurrentVisibleState == visibleState) {
            return;
        }
        mCurrentVisibleState = visibleState;
        if (visibleState) {
            onFragmentLoad();
        } else {
            onFragmentLoadStop();
        }

    }

    @Override
    public void onResume() {
        Logger.d("");
        super.onResume();
        if (!mCurrentVisibleState && getUserVisibleHint() && !isHidden()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        Logger.d("");
        super.onPause();
        if (mCurrentVisibleState && !getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
        mViewCreated = false;
        mCurrentVisibleState = false;
        if (mCacheViews != null) {
            mCacheViews.clear();
            mCacheViews = null;
        }
    }

    @Nullable
    public View getRootView() {
        return mRootView;
    }

    public final <V extends View> V getView(@IdRes int id) {
        if (mCacheViews == null) {
            mCacheViews = new SparseArray<>();
        }

        View view = mCacheViews.get(id);
        if (view == null) {
            view = findViewById(id);
            if (view != null) {
                mCacheViews.put(id, view);
            }
        }

        return (V) view;
    }

    public final <V extends View> V findViewById(@IdRes int id) {
        if (mRootView == null) {
            return null;
        }

        return mRootView.findViewById(id);
    }

    protected abstract int getLayoutRes();

    protected abstract void initView(View rootView);

    protected abstract void onFragmentLoad();

    protected abstract void onFragmentLoadStop();
}
