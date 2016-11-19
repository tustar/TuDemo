package com.tustar.demo.module.loaderdemo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * Created by tustar on 5/9/16.
 */
public class AppEntry {

    private final AppListLoader loader;
    private final ApplicationInfo info;
    private final File apkFile;
    private String label;
    private Drawable icon;
    private boolean mounted;

    public AppEntry(AppListLoader loader, ApplicationInfo info) {
        this.loader = loader;
        this.info = info;
        this.apkFile = new File(info.sourceDir);
    }

    public void loadLabel(Context context) {
        if (null == label || !mounted) {
            if (!apkFile.exists()) {
                mounted = false;
                label = info.packageName;
            } else {
                mounted = true;
                CharSequence label = info.loadLabel(context.getPackageManager());
                this.label = label != null ? label.toString() : info.packageName;
            }
        }
    }

    public String getLabel() {
        return label;
    }

    public Drawable getIcon() {
        if (null == icon) {
            if (apkFile.exists()) {
                icon = info.loadIcon(loader.mPm);
                return icon;
            } else {
                mounted = false;
            }
        } else if (!mounted) {
            // If the app wasn't mounted but is now mounted, reload its icon.
            if (apkFile.exists()) {
                mounted = true;
                icon = info.loadIcon(loader.mPm);
                return icon;
            }
        } else {
            return icon;
        }

        return loader.getContext().getResources().getDrawable(android.R.drawable.sym_def_app_icon);
    }

    public ApplicationInfo getInfo() {
        return info;
    }

    public File getApkFile() {
        return apkFile;
    }

    @Override
    public String toString() {
        return label;
    }
}
