package com.tustar.demo.module.qyz.overscroll;

/**
 * Created by tustar on 17-3-14.
 */

public interface ListenerStubs {

    class OverScrollStateListenerStub implements IOverScrollStateListener {

        @Override
        public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {

        }
    }

    class OverScrollUpdateListenerStub implements IOverScrollUpdateListener {

        @Override
        public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {

        }
    }
}
