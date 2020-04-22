package com.tustar.demo.ui.loader;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.tustar.util.Logger;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.loader.content.AsyncTaskLoader;

/**
 * Created by tustar on 5/9/16.
 */
public class AppListLoader extends AsyncTaskLoader<List<AppEntry>> {

    private static final String TAG = "AppListLoader";
    public final PackageManager mPm;
    private List<AppEntry> mApps;

    public AppListLoader(Context context) {
        super(context);
        mPm = getContext().getPackageManager();
    }

    /****************************************************/
    /** (1) A task that performs the asynchronous load **/
    /****************************************************/

    /**
     * This method is called on a background thread and generates a List of
     * {@link AppEntry} objects. Each entry corresponds to a single installed
     * application on the device.
     */
    @Override
    public List<AppEntry> loadInBackground() {
        Logger.i(TAG, "loadInBackground ::");
        List<ApplicationInfo> apps = mPm.getInstalledApplications(0);
        if (null == apps) {
            apps = new ArrayList<>();
        }

        // Create corresponding array of entries and load their labels.
        List<AppEntry> entries = new ArrayList<>(apps.size());
        for (ApplicationInfo info: apps) {
            AppEntry entry = new AppEntry(this, info);
            entry.loadLabel(getContext());
            entries.add(entry);
        }

        // Sort the list.
        Collections.sort(entries, ALPHA_COMPARATOR);

        return entries;
    }

    /*******************************************/
    /** (2) Deliver the results to the client **/
    /*******************************************/
    /**
     * Called when there is new DATAS to deliver to the client. The superclass will
     * deliver it to the registered listener (i.e. the LoaderManager), which will
     * forward the results to the client through a call to onLoadFinished.
     */
    @Override
    public void deliverResult(List<AppEntry> data) {
        Logger.i(TAG, "deliverResult: ");
        if (isReset()) {
            Logger.d(TAG, "deliverResult: Warning! An async query came in while the Loader was reset!");
            // The Loader has been reset; ignore the result and invalidate the DATAS.
            // This can happen when the Loader is reset while an asynchronous query
            // is working in the background. That is, when the background thread
            // finishes its work and attempts to deliver the results to the client,
            // it will see here that the Loader has been reset and discard any
            // resources associated with the new DATAS as necessary.
            if (null != data) {
                releaseResources(data);
                return;
            }
        }

        // Hold a reference to the old DATAS so it doesn't get garbage collected.
        // We must protect it until the new DATAS has been delivered.
        List<AppEntry> oldApps = mApps;
        mApps = data;

        if (isStarted()) {
            // If the Loader is in a started state, have the superclass deliver the
            // results to the client.
            Logger.d(TAG, "deliverResult: Delivering results to the LoaderManager " +
                    "for the ListFragment to display!");
            super.deliverResult(data);
        }

        // Invalidate the old DATAS as we don't need it any more.
        if (null != oldApps && oldApps != data) {
            Logger.d(TAG, "deliverResult: Releasing any old DATAS associated with this Loader.");
            releaseResources(oldApps);
        }
    }

    /*********************************************************/
    /** (3) Implement the Loaders state-dependent behavior **/
    /*********************************************************/
    @Override
    protected void onStartLoading() {
        Logger.i(TAG, "onStartLoading: ");
        if (null != mApps) {
            Logger.d(TAG, "onStartLoading: Delivering previously loaded DATAS to the client...");
            deliverResult(mApps);
        }

        // Register the observers that will notify the Loader when changes are made.
        if (null == mAppsObserver) {
            mAppsObserver = new AppListObserver(this);
        }

        if (takeContentChanged()) {
            // When the observer detects a new installed application, it will call
            // onContentChanged() on the Loader, which will cause the next call to
            // takeContentChanged() to return true. If this is ever the case (or if
            // the current DATAS is null), we force a new load.
            Logger.d(TAG, "onStartLoading: A content change has been detected... so force load!");
            forceLoad();
        } else if (null == mApps) {
            // If the current DATAS is null... then we should make it non-null! :)
            Logger.d(TAG, "onStartLoading: The current DATAS is DATAS is null... so force load!");
            forceLoad();

        }
    }

    @Override
    protected void onStopLoading() {
        Logger.i(TAG, "onStopLoading: ");
        // The Loader has been put in a stopped state, so we should attempt to
        // cancel the current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is; Loaders in a stopped state
        // should still monitor the DATAS source for changes so that the Loader
        // will know to force a new load if it is ever started again.
    }

    @Override
    protected void onReset() {
        Logger.i(TAG, "onReset: ");

        // Ensure the loader is stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'apps'.
        if (null != mApps) {
            releaseResources(mApps);
            mApps = null;
        }

        // The Loader is being reset, so we should stop monitoring for changes.
        if (null != mAppsObserver) {
            getContext().unregisterReceiver(mAppsObserver);
            mAppsObserver = null;
        }
    }

    @Override
    public void onCanceled(List<AppEntry> data) {
        Logger.i(TAG, "onCanceled: ");

        // Attempt to cancel the current asynchronous load.
        super.onCanceled(data);

        // The load has been canceled, so we should release the resources
        // associated with 'mApps'.
        releaseResources(data);
    }


    @Override
    public void forceLoad() {
        Logger.i(TAG, "forceLoad: ");
        super.forceLoad();
    }

    /**
     * Helper method to take care of releasing resources associated with an
     * actively loaded DATAS set.
     */
    private void releaseResources(List<AppEntry> mApps) {
        // For a simple List, there is nothing to do. For something like a Cursor,
        // we would close it in this method. All resources associated with the
        // Loader should be released here.
    }

    /*********************************************************************/
    /** (4) Observer which receives notifications when the DATAS changes **/
    /*********************************************************************/
    // An observer to notify the Loader when new apps are installed/updated.
    private AppListObserver mAppsObserver;

    /**************************/
    /** (5) Everything else! **/
    /**************************/
    /**
     * Performs alphabetical comparison of {@link AppEntry} objects. This is
     * used to sort queried DATAS in {@link loadInBackground}.
     */
    private static final Comparator<? super AppEntry> ALPHA_COMPARATOR = new Comparator<AppEntry>() {

        Collator collator = Collator.getInstance();

        @Override
        public int compare(AppEntry lhs, AppEntry rhs) {
            return collator.compare(lhs.getLabel(), rhs.getLabel());
        }
    };
}
