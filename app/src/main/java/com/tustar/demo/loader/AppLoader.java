package com.tustar.demo.loader;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.AsyncTaskLoader;

import com.tustar.demo.model.AppEntry;
import com.tustar.demo.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tustar on 5/9/16.
 */
public class AppLoader extends AsyncTaskLoader<List<AppEntry>> {

    private static final String TAG = "AppLoader";
    private final PackageManager mPm;
    private List<AppEntry> mApps;

    public AppLoader(Context context) {
        super(context);
        mPm = getContext().getPackageManager();
    }

    @Override
    public List<AppEntry> loadInBackground() {
        Logger.i(TAG, "loadInBackground ::");
        List<ApplicationInfo> apps = mPm.getInstalledApplications(0);
        if (null == apps) {
            apps = new ArrayList<>();
        }

        List<AppEntry> entries = new ArrayList<>(apps.size());
        for (ApplicationInfo info: apps) {
            AppEntry entry = new AppEntry(this, info);
            entry.loadLabel(getContext());
            entries.add(entry);
        }
        gs
        return null;
    }
}
