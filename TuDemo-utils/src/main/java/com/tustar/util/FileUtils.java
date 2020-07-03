/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tustar.util;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.StrictMode;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Tools for managing files.  Not for public consumption.
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    public static Uri getDocumentUri(File file) {
        Uri uri = null;
        String path = file.getPath();
        if (path.startsWith("/mnt/media_rw")) {
            String authority = "com.android.externalstorage.documents";
            String deviceID = path.substring(14, 23);
            String nameString = path.substring(24);
            String uriPath = deviceID + ":" + nameString;
            uri = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
                    .authority(authority).appendPath("document")
                    .appendPath(uriPath).build();

        } else {
            uri = Uri.fromFile(file);
        }

        return uri;

    }

    public static Uri getShareUri(File file) {
        Uri uri;
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            uri = FileUtils.getDocumentUri(file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }


    public static void disableDeathOnFileUriExposure() {

        //add for android N .file://
        if (VERSION.SDK_INT > VERSION_CODES.M) {
            Class<StrictMode> strictMode = StrictMode.class;
            Method[] methods;
            try {
                methods = strictMode.getMethods();
                for (Method method : methods) {
                    if ("disableDeathOnFileUriExposure".equals(method.getName())) {
                        method.invoke(null, new Object[]{});
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void enableDeathOnFileUriExposure() {

        //add for android N .file://
        if (VERSION.SDK_INT > VERSION_CODES.M) {
            Class<StrictMode> strictMode = StrictMode.class;
            Method[] methods;
            try {
                methods = strictMode.getMethods();
                for (Method method : methods) {
                    if ("enableDeathOnFileUriExposure".equals(method.getName())) {
                        method.invoke(null, new Object[]{});
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}

