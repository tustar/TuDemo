package com.tustar.demo.module.loaderdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tustar.demo.util.Logger;

/**
 * Created by tustar on 5/11/16.
 */
public class AppListObserver extends BroadcastReceiver {

    private static final String TAG = "AppListObserver";
    private AppListLoader loader;

    public AppListObserver(AppListLoader loader) {
        this.loader = loader;

        // Register for events related to application installs/removals/updates.
        IntentFilter packageFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        packageFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        packageFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        packageFilter.addDataScheme("package");
        loader.getContext().registerReceiver(this, packageFilter);

        // Register for events related to sdcard installation.
        IntentFilter sdFilter = new IntentFilter();
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        loader.getContext().registerReceiver(this, sdFilter);

        IntentFilter localFilter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        loader.getContext().registerReceiver(this, localFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.i(TAG, "onReceive: context = " + context + ", intent = " + intent);
        loader.onContentChanged();
    }
}
