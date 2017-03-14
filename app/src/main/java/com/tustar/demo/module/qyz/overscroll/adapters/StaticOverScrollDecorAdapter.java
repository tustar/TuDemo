package com.tustar.demo.module.qyz.overscroll.adapters;

import android.view.View;

import com.tustar.demo.module.qyz.overscroll.HorizontalOverScrollBounceEffectDecorator;
import com.tustar.demo.module.qyz.overscroll.VerticalOverScrollBounceEffectDecorator;


/**
 * A static adapter for views that are ALWAYS over-scroll-able (e.g. image view).
 *
 * @author amit
 *
 * @see HorizontalOverScrollBounceEffectDecorator
 * @see VerticalOverScrollBounceEffectDecorator
 */
public class StaticOverScrollDecorAdapter implements IOverScrollDecoratorAdapter {

    protected final View mView;

    public StaticOverScrollDecorAdapter(View view) {
        mView = view;
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public boolean isInAbsoluteStart() {
        return true;
    }

    @Override
    public boolean isInAbsoluteEnd() {
        return true;
    }
}
