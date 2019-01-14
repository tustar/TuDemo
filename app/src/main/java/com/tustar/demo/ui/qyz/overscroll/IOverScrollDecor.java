package com.tustar.demo.ui.qyz.overscroll;

import android.view.View;

/**
 * Created by tustar on 17-3-14.
 */

public interface IOverScrollDecor {

    View getView();

    void setOverScrollStateListener(IOverScrollStateListener listener);

    void setOverScrollUpdateListener(IOverScrollUpdateListener listener);

    /**
     * Get the current decorator's runtime state, i.e. one of the values specified by {@link IOverScrollState}.
     *
     * @return The state.
     */
    int getCurrentState();

    /**
     * Detach the decorator from its associated view, thus disabling it entirely.
     * <p>
     * <p>It is best to call this only when over-scroll isn't currently in-effect - i.e. verify that
     * <code>getCurrentState()==IOverScrollState.STATE_IDLE</code> as a precondition, or otherwise
     * use a state listener previously installed using
     * {@link #setOverScrollStateListener(IOverScrollStateListener)}.</p>
     * <p>
     * <p>Note: Upon detachment completion, the view in question will return to the default
     * Android over-scroll configuration (i.e. {@link View.OVER_SCROLL_ALWAYS} mode). This can be
     * overridden by calling <code>View.setOverScrollMode(mode)</code> immediately thereafter.</p>
     */
    void detach();

}