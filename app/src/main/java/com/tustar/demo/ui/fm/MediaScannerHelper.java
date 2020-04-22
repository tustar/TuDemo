package com.tustar.demo.ui.fm;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import com.tustar.util.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tustar on 17-2-13.
 */

public class MediaScannerHelper {

    private static final String TAG = "MediaScannerHelper";

    public static void scanFile(Context context, List<String> paths) {
        Logger.i(TAG, "scanFile :: paths size = " + paths.size());
        if (paths == null || paths.isEmpty()) {
            Logger.w(TAG, "scanFile :: paths is empty!!!");
            return;
        }

        String[] absPaths = paths.toArray(new String[]{});
        MediaScannerConnection.scanFile(context.getApplicationContext(),
                absPaths, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                        Logger.d(TAG, "Finished scanning " + path);
                    }
                });
    }

    public static void scanFile(Context context, File file) {

        List<String> paths = new ArrayList<>();
        if (file == null) {
            Logger.w(TAG, "scanFile :: file is null");
        }
        paths = fetchAllPath(paths, file);
        scanFile(context, paths);
    }

    public static void scanFile(Context context, String path) {
        File file = new File(path);
        scanFile(context, file);
    }

    public static List<String> fetchAllPath(List<String> paths, File file) {
        paths.add(file.getAbsolutePath());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tmp : files) {
                fetchAllPath(paths, tmp);
            }
        }

        return paths;
    }
}
