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
package com.tustar.demo.module.filemanager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * Tools for managing files.  Not for public consumption.
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    public static final int S_IRWXU = 00700;
    public static final int S_IRUSR = 00400;
    public static final int S_IWUSR = 00200;
    public static final int S_IXUSR = 00100;

    public static final int S_IRWXG = 00070;
    public static final int S_IRGRP = 00040;
    public static final int S_IWGRP = 00020;
    public static final int S_IXGRP = 00010;

    public static final int S_IRWXO = 00007;
    public static final int S_IROTH = 00004;
    public static final int S_IWOTH = 00002;
    public static final int S_IXOTH = 00001;

    /**
     * Regular expression for safe filenames: no spaces or metacharacters
     */
    private static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("[\\w%+,./=_-]+");

    /**
     * Set owner and mode of of given {@link File}.
     *
     * @param mode to apply through {@code chmod}
     * @param uid  to apply through {@code chown}, or -1 to leave unchanged
     * @param gid  to apply through {@code chown}, or -1 to leave unchanged
     * @return 0 on success, otherwise errno.
     */

    /**
     * Return owning UID of given path, otherwise -1.
     */
    public static int getUid(String path) {
        try {
            return Os.stat(path).st_uid;
        } catch (ErrnoException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Perform an fsync on the given FileOutputStream.  The stream at this
     * point must be flushed but not yet closed.
     */
    public static boolean sync(FileOutputStream stream) {
        try {
            if (stream != null) {
                stream.getFD().sync();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // copy a file from srcFile to destFile, return true if succeed, return
    // false if fail
    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     */
    public static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            FileOutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.flush();
                try {
                    out.getFD().sync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if a filename is "safe" (no metacharacters or spaces).
     *
     * @param file The file to check
     */
    public static boolean isFilenameSafe(File file) {
        // Note, we check whether it matches what's known to be safe,
        // rather than what's known to be unsafe.  Non-ASCII, control
        // characters, etc. are all unsafe by default.
        return SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
    }

    /**
     * Read a text file into a String, optionally limiting the length.
     *
     * @param file     to read (will not seek, so things like /proc files are OK)
     * @param max      length (positive for head, negative of tail, 0 for no limit)
     * @param ellipsis to add of the file was truncated (can be null)
     * @return the contents of the file, possibly truncated
     * @throws IOException if something goes wrong reading the file
     */
    public static String readTextFile(File file, int max, String ellipsis) throws IOException {
        InputStream input = new FileInputStream(file);
        // wrapping a BufferedInputStream around it because when reading /proc with unbuffered
        // input stream, bytes read not equal to buffer size is not necessarily the correct
        // indication for EOF; but it is true for BufferedInputStream due to its implementation.
        BufferedInputStream bis = new BufferedInputStream(input);
        try {
            long size = file.length();
            if (max > 0 || (size > 0 && max == 0)) {  // "head" mode: read the first N bytes
                if (size > 0 && (max == 0 || size < max)) max = (int) size;
                byte[] data = new byte[max + 1];
                int length = bis.read(data);
                if (length <= 0) return "";
                if (length <= max) return new String(data, 0, length);
                if (ellipsis == null) return new String(data, 0, max);
                return new String(data, 0, max) + ellipsis;
            } else if (max < 0) {  // "tail" mode: keep the last N
                int len;
                boolean rolled = false;
                byte[] last = null;
                byte[] data = null;
                do {
                    if (last != null) rolled = true;
                    byte[] tmp = last;
                    last = data;
                    data = tmp;
                    if (data == null) data = new byte[-max];
                    len = bis.read(data);
                } while (len == data.length);

                if (last == null && len <= 0) return "";
                if (last == null) return new String(data, 0, len);
                if (len > 0) {
                    rolled = true;
                    System.arraycopy(last, len, last, 0, last.length - len);
                    System.arraycopy(data, 0, last, last.length - len, len);
                }
                if (ellipsis == null || !rolled) return new String(last);
                return ellipsis + new String(last);
            } else {  // "cat" mode: size unknown, read it all in streaming fashion
                ByteArrayOutputStream contents = new ByteArrayOutputStream();
                int len;
                byte[] data = new byte[1024];
                do {
                    len = bis.read(data);
                    if (len > 0) contents.write(data, 0, len);
                } while (len == data.length);
                return contents.toString();
            }
        } finally {
            bis.close();
            input.close();
        }
    }

    /**
     * Writes string to file. Basically same as "echo -n $string > $filename"
     *
     * @param filename
     * @param string
     * @throws IOException
     */
    public static void stringToFile(String filename, String string) throws IOException {
        FileWriter out = new FileWriter(filename);
        try {
            out.write(string);
        } finally {
            out.close();
        }
    }

    /**
     * Computes the checksum of a file using the CRC32 checksum routine.
     * The value of the checksum is returned.
     *
     * @param file the file to checksum, must not be null
     * @return the checksum value or an exception is thrown.
     */
    public static long checksumCrc32(File file) throws FileNotFoundException, IOException {
        CRC32 checkSummer = new CRC32();
        CheckedInputStream cis = null;

        try {
            cis = new CheckedInputStream(new FileInputStream(file), checkSummer);
            byte[] buf = new byte[128];
            while (cis.read(buf) >= 0) {
                // Just read for checksum to get calculated.
            }
            return checkSummer.getValue();
        } finally {
            if (cis != null) {
                try {
                    cis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Delete older files in a directory until only those matching the given
     * constraints remain.
     *
     * @param minCount Always keep at least this many files.
     * @param minAge   Always keep files younger than this age.
     * @return if any files were deleted.
     */
    public static boolean deleteOlderFiles(File dir, int minCount, long minAge) {
        if (minCount < 0 || minAge < 0) {
            throw new IllegalArgumentException("Constraints must be positive or 0");
        }

        final File[] files = dir.listFiles();
        if (files == null) return false;

        // Sort with newest files first
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                return (int) (rhs.lastModified() - lhs.lastModified());
            }
        });

        // Keep at least minCount files
        boolean deleted = false;
        for (int i = minCount; i < files.length; i++) {
            final File file = files[i];

            // Keep files newer than minAge
            final long age = System.currentTimeMillis() - file.lastModified();
            if (age > minAge) {
                if (file.delete()) {
                    Log.d(TAG, "Deleted old file " + file);
                    deleted = true;
                }
            }
        }
        return deleted;
    }

    /**
     * Test if a file lives under the given directory, either as a direct child
     * or a distant grandchild.
     * <p>
     * Both files <em>must</em> have been resolved using
     * {@link File#getCanonicalFile()} to avoid symlink or path traversal
     * attacks.
     */
    public static boolean contains(File dir, File file) {
        if (file == null) return false;

        String dirPath = dir.getAbsolutePath();
        String filePath = file.getAbsolutePath();

        if (dirPath.equals(filePath)) {
            return true;
        }

        if (!dirPath.endsWith("/")) {
            dirPath += "/";
        }
        return filePath.startsWith(dirPath);
    }

    /**
     * Assert that given filename is valid on ext4.
     */
    public static boolean isValidExtFilename(String name) {
        if (TextUtils.isEmpty(name) || ".".equals(name) || "..".equals(name)) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            final char c = name.charAt(i);
            if (c == '\0' || c == '/') {
                return false;
            }
        }
        return true;
    }

    public static String rewriteAfterRename(File beforeDir, File afterDir, String path) {
        if (path == null) return null;
        final File result = rewriteAfterRename(beforeDir, afterDir, new File(path));
        return (result != null) ? result.getAbsolutePath() : null;
    }

    public static String[] rewriteAfterRename(File beforeDir, File afterDir, String[] paths) {
        if (paths == null) return null;
        final String[] result = new String[paths.length];
        for (int i = 0; i < paths.length; i++) {
            result[i] = rewriteAfterRename(beforeDir, afterDir, paths[i]);
        }
        return result;
    }

    /**
     * Given a path under the "before" directory, rewrite it to live under the
     * "after" directory. For example, {@code /before/foo/bar.txt} would become
     * {@code /after/foo/bar.txt}.
     */
    public static File rewriteAfterRename(File beforeDir, File afterDir, File file) {
        if (file == null) return null;
        if (contains(beforeDir, file)) {
            final String splice = file.getAbsolutePath().substring(
                    beforeDir.getAbsolutePath().length());
            return new File(afterDir, splice);
        }
        return null;
    }

    public static long getDirectorySize(File dir) {
        long size = 0L;

        if (!dir.isDirectory()) {
            return size;
        }

        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                size += getDirectorySize(f);
            } else {
                size += f.length();
            }
        }

        return size;
    }

    public static String makeNewPath(File source, String targetPath) {
        String newPath = targetPath + File.separator + source.getName();
        File target = new File(newPath);
        if (!target.exists()) {
            return newPath;
        } else if (source.isDirectory() || source.getName().lastIndexOf(".") == -1) {
            for (int i = 1; ; i++) {
                File f = new File(newPath + "_" + i);
                if (!f.exists()) {
                    return f.getPath();
                }
            }
        } else { //file
            int dotIndex = source.getName().lastIndexOf(".");
            String name = source.getName();
            String extension = "";
            if (dotIndex > 0) {
                extension = name.substring(dotIndex + 1, name.length());
                name = name.substring(0, dotIndex);
            }

            for (int i = 1; ; i++) {
                newPath = targetPath + File.separator + name + "_" + i + "." + extension;
                File f = new File(newPath);
                if (!f.exists()) {
                    return f.getPath();
                }
            }
        }
    }

    public static String getFileExtension(File file) {
        int dotIndex = file.getName().lastIndexOf(".");
        String extension = "";
        if (dotIndex > 0) {
            extension = file.getName().substring(dotIndex + 1, file.getName().length());
        }
        return extension.trim();
    }

    public static String getFileNameWithoutExtension(File file) {
        int dotIndex = file.getName().lastIndexOf(".");
        String name = file.getName();
        if (dotIndex > 0) {
            name = file.getName().substring(0, dotIndex);
        }
        return name.trim();
    }

    private static Map<String, String> sMIMETypeMaps = new HashMap<String, String>();

    static {
        sMIMETypeMaps.put("htm", "text/html");
        sMIMETypeMaps.put("html", "text/html");
        sMIMETypeMaps.put("epub", "application/epub+zip");
        sMIMETypeMaps.put("mobi", "application/x-mobipocket-ebook");
        sMIMETypeMaps.put("pdf", "application/pdf");
        sMIMETypeMaps.put("txt", "text/plain");
        sMIMETypeMaps.put("doc", "application/msword");
        sMIMETypeMaps.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        sMIMETypeMaps.put("ppt", "application/vnd.ms-powerpoint");
        sMIMETypeMaps.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        sMIMETypeMaps.put("xls", "application/vnd.ms-excel");
        sMIMETypeMaps.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        sMIMETypeMaps.put("rss", "application/rss+xml");
        sMIMETypeMaps.put("pub", "application/x-mspublisher");
        sMIMETypeMaps.put("csv", "text/csv");
        // apk
        sMIMETypeMaps.put("apk", "application/vnd.android.package-archive");
        // pic
        sMIMETypeMaps.put("jpg", "image/jpeg");
        sMIMETypeMaps.put("jpeg", "image/jpeg");
        sMIMETypeMaps.put("png", "image/png");
        sMIMETypeMaps.put("gif", "image/gif");
        sMIMETypeMaps.put("bmp", "image/bmp");
        sMIMETypeMaps.put("webp", "image/webp");
        sMIMETypeMaps.put("dng", "image/x-adobe-dng");

        sMIMETypeMaps.put("tif", "image/tiff");
        sMIMETypeMaps.put("tiff", "image/tiff");
        sMIMETypeMaps.put("ico", "image/x-icon");
        sMIMETypeMaps.put("wbmp", "image/vnd.wap.wbmp");

        // audio
        // sMIMETypeMaps.put("3gp", "audio/*");
        sMIMETypeMaps.put("aac", "audio/x-aac");
        sMIMETypeMaps.put("mp3", "audio/mpeg");
        sMIMETypeMaps.put("ogg", "audio/ogg");
        sMIMETypeMaps.put("wav", "audio/x-wav");
        sMIMETypeMaps.put("flac", "audio/flac");
        sMIMETypeMaps.put("m4a", "audio/mp4a-latm");
        sMIMETypeMaps.put("mid", "audio/midi");

        sMIMETypeMaps.put("mxmf", "audio/mobile-xmf");
        sMIMETypeMaps.put("amr", "audio/amr");
        sMIMETypeMaps.put("wma", "audio/x-ms-wma");
        sMIMETypeMaps.put("mpg", "audio/mpeg");
        sMIMETypeMaps.put("imy", "audio/x-imelody");
        sMIMETypeMaps.put("midi", "audio/midi");
        sMIMETypeMaps.put("ra", "audio/x-pn-realaudio");
        sMIMETypeMaps.put("ram", "audio/x-pn-realaudio");
        sMIMETypeMaps.put("weba", "audio/webm");
        sMIMETypeMaps.put("mka", "audio/x-matroska");

        // video
        sMIMETypeMaps.put("3gp", "video/3gpp");
        sMIMETypeMaps.put("swf", "application/x-shockwave-flash");
        sMIMETypeMaps.put("mkv", "video/x-matroska");
        sMIMETypeMaps.put("mp4", "video/mp4");
        sMIMETypeMaps.put("avi", "video/x-msvideo");
        sMIMETypeMaps.put("asf", "video/x-ms-asf");
        sMIMETypeMaps.put("ts", "application/octet-stream");
        sMIMETypeMaps.put("webm", "video/webm");

        sMIMETypeMaps.put("flv", "video/x-flv");
        sMIMETypeMaps.put("3gpp", "video/3gpp");
        sMIMETypeMaps.put("mpeg", "video/mpeg");
        sMIMETypeMaps.put("mov", "video/quicktime");
        sMIMETypeMaps.put("m4v", "video/x-m4v");
        sMIMETypeMaps.put("wmv", "video/x-ms-wmv");
        sMIMETypeMaps.put("asf", "video/x-ms-asf");
        sMIMETypeMaps.put("mp4v", "video/mp4v-es");
        sMIMETypeMaps.put("mpg4", "video/mp4");
        sMIMETypeMaps.put("mpg", "video/mpeg");
        sMIMETypeMaps.put("m1v", "video/mpeg");
        sMIMETypeMaps.put("m2v", "video/mpeg");
        sMIMETypeMaps.put("rm", "application/vnd.rn-realmedia");
//        sMIMETypeMaps.put("rmvb", "application/vnd.rn-realmedia-vbr");
        sMIMETypeMaps.put("rmvb", "video/x-pn-realvideo");
        sMIMETypeMaps.put("ogv", "video/ogg");
        sMIMETypeMaps.put("f4v", "video/x-f4v");

        // zip
        sMIMETypeMaps.put("zip", "application/zip");
        sMIMETypeMaps.put("rar", "application/x-rar-compressed");
        sMIMETypeMaps.put("7z", "application/x-7z-compressed");
        sMIMETypeMaps.put("ace", "application/x-ace-compressed");
        sMIMETypeMaps.put("tar", "application/x-tar");

        // more
        sMIMETypeMaps.put("323", "text/h323");
        sMIMETypeMaps.put("bas", "text/plain");
        sMIMETypeMaps.put("c", "text/plain");
        sMIMETypeMaps.put("css", "text/css");
        sMIMETypeMaps.put("etx", "text/x-setext");
        sMIMETypeMaps.put("h", "text/plain");
        sMIMETypeMaps.put("htc", "text/x-component");
        sMIMETypeMaps.put("htt", "text/webviewhtml");
        sMIMETypeMaps.put("tsv", "text/tab-separated-values");
        sMIMETypeMaps.put("uls", "text/iuls");
        sMIMETypeMaps.put("stm", "text/html");
        sMIMETypeMaps.put("rtx", "text/richtext");
        sMIMETypeMaps.put("sct", "text/scriptlet");
        sMIMETypeMaps.put("vcf", "text/x-vcard");

        sMIMETypeMaps.put("aif", "audio/x-aiff");
        sMIMETypeMaps.put("aifc", "audio/x-aiff");
        sMIMETypeMaps.put("aiff", "audio/x-aiff");
        sMIMETypeMaps.put("au", "audio/basic");
        sMIMETypeMaps.put("m3u", "audio/x-mpegurl");
        sMIMETypeMaps.put("snd", "audio/basic");
        sMIMETypeMaps.put("rmi", "audio/mid");

        sMIMETypeMaps.put("asr", "video/x-ms-asf");
        sMIMETypeMaps.put("asx", "video/x-ms-asf");
        sMIMETypeMaps.put("lsf", "video/x-la-asf");
        sMIMETypeMaps.put("lsx", "video/x-la-asf");
        sMIMETypeMaps.put("movie", "video/x-sgi-movie");
        sMIMETypeMaps.put("mp2", "video/mpeg");
        sMIMETypeMaps.put("mpa", "video/mpeg");
        sMIMETypeMaps.put("mpe", "video/mpeg");
        sMIMETypeMaps.put("mpv2", "video/mpeg");
        sMIMETypeMaps.put("qt", "video/quicktime");

        sMIMETypeMaps.put("cmx", "image/x-cmx");
        sMIMETypeMaps.put("cod", "image/cis-cod");
        sMIMETypeMaps.put("ief", "image/ief");
        sMIMETypeMaps.put("jfif", "image/pipeg");
        sMIMETypeMaps.put("jpe", "image/jpeg");
        sMIMETypeMaps.put("pnm", "image/x-portable-anymap");
        sMIMETypeMaps.put("pbm", "image/x-portable-bitmap");
        sMIMETypeMaps.put("pgm", "image/x-portable-graymap");
        sMIMETypeMaps.put("ppm", "image/x-portable-pixmap");
        sMIMETypeMaps.put("ras", "image/x-cmu-raster");
        sMIMETypeMaps.put("rgb", "image/x-rgb");
        sMIMETypeMaps.put("svg", "image/svg+xml");
        sMIMETypeMaps.put("xbm", "image/x-xbitmap");
        sMIMETypeMaps.put("xpm", "image/x-xpixmap");
        sMIMETypeMaps.put("xwd", "image/x-xwindowdump");

        sMIMETypeMaps.put("acx", "application/internet-property-stream");
        sMIMETypeMaps.put("ai", "application/postscript");
        sMIMETypeMaps.put("axs", "application/olescript");
        sMIMETypeMaps.put("bcpio", "application/x-bcpio");
        sMIMETypeMaps.put("bin", "application/octet-stream");
        sMIMETypeMaps.put("cat", "application/vnd.ms-pkiseccat");
        sMIMETypeMaps.put("cdf", "application/x-cdf");
        sMIMETypeMaps.put("cer", "application/x-x509-ca-cert");
        sMIMETypeMaps.put("class", "application/octet-stream");
        sMIMETypeMaps.put("clp", "application/x-msclip");
        sMIMETypeMaps.put("cpio", "application/x-cpio");
        sMIMETypeMaps.put("crd", "application/x-mscardfile");
        sMIMETypeMaps.put("crl", "application/pkix-crl");
        sMIMETypeMaps.put("crt", "application/x-x509-ca-cert");
        sMIMETypeMaps.put("csh", "application/x-csh");
        sMIMETypeMaps.put("dcr", "application/x-director");
        sMIMETypeMaps.put("der", "application/x-x509-ca-cert");
        sMIMETypeMaps.put("dir", "application/x-director");
        sMIMETypeMaps.put("dll", "application/x-msdownload");
        sMIMETypeMaps.put("dms", "application/octet-stream");
        sMIMETypeMaps.put("dot", "application/msword");
        sMIMETypeMaps.put("dvi", "application/x-dvi");
        sMIMETypeMaps.put("dxr", "application/x-director");
        sMIMETypeMaps.put("eps", "application/postscript");
        sMIMETypeMaps.put("evy", "application/envoy");
        sMIMETypeMaps.put("exe", "application/octet-stream");
        sMIMETypeMaps.put("fif", "application/fractals");
        sMIMETypeMaps.put("flr", "x-world/x-vrml");
        sMIMETypeMaps.put("gtar", "application/x-gtar");
        sMIMETypeMaps.put("gz", "application/x-gzip");
        sMIMETypeMaps.put("hdf", "application/x-hdf");
        sMIMETypeMaps.put("hlp", "application/winhlp");
        sMIMETypeMaps.put("hqx", "application/mac-binhex40");
        sMIMETypeMaps.put("hta", "application/hta");
        sMIMETypeMaps.put("iii", "application/x-iphone");
        sMIMETypeMaps.put("ins", "application/x-internet-signup");
        sMIMETypeMaps.put("isp", "application/x-internet-signup");
        sMIMETypeMaps.put("js", "application/x-javascript");
        sMIMETypeMaps.put("latex", "application/x-latex");
        sMIMETypeMaps.put("lha", "application/octet-stream");
        sMIMETypeMaps.put("lzh", "application/octet-stream");
        sMIMETypeMaps.put("m13", "application/x-msmediaview");
        sMIMETypeMaps.put("m14", "application/x-msmediaview");
        sMIMETypeMaps.put("man", "application/x-troff-man");
        sMIMETypeMaps.put("mdb", "application/x-msaccess");
        sMIMETypeMaps.put("me", "application/x-troff-me");
        sMIMETypeMaps.put("mht", "message/rfc822");
        sMIMETypeMaps.put("mhtml", "message/rfc822");
        sMIMETypeMaps.put("mny", "application/x-msmoney");
        sMIMETypeMaps.put("mpp", "application/vnd.ms-project");
        sMIMETypeMaps.put("ms", "application/x-troff-ms");
        sMIMETypeMaps.put("mvb", "application/x-msmediaview");
        sMIMETypeMaps.put("nws", "message/rfc822");
        sMIMETypeMaps.put("oda", "application/oda");
        sMIMETypeMaps.put("p10", "application/pkcs10");
        sMIMETypeMaps.put("p12", "application/x-pkcs12");
        sMIMETypeMaps.put("p7b", "application/x-pkcs7-certificates");
        sMIMETypeMaps.put("p7c", "application/x-pkcs7-mime");
        sMIMETypeMaps.put("p7m", "application/x-pkcs7-mime");
        sMIMETypeMaps.put("p7r", "application/x-pkcs7-certreqresp");
        sMIMETypeMaps.put("p7s", "application/x-pkcs7-signature");
        sMIMETypeMaps.put("pfx", "application/x-pkcs12");
        sMIMETypeMaps.put("pko", "application/ynd.ms-pkipko");
        sMIMETypeMaps.put("pma", "application/x-perfmon");
        sMIMETypeMaps.put("pmc", "application/x-perfmon");
        sMIMETypeMaps.put("pml", "application/x-perfmon");
        sMIMETypeMaps.put("pmr", "application/x-perfmon");
        sMIMETypeMaps.put("pmw", "application/x-perfmon");
        sMIMETypeMaps.put("pot,", "application/vnd.ms-powerpoint");
        sMIMETypeMaps.put("pps", "application/vnd.ms-powerpoint");
        sMIMETypeMaps.put("prf", "application/pics-rules");
        sMIMETypeMaps.put("ps", "application/postscript");
        sMIMETypeMaps.put("roff", "application/x-troff");
        sMIMETypeMaps.put("rtf", "application/rtf");
        sMIMETypeMaps.put("scd", "application/x-msschedule");
        sMIMETypeMaps.put("setpay", "application/set-payment-initiation");
        sMIMETypeMaps.put("setreg", "application/set-registration-initiation");
        sMIMETypeMaps.put("sh", "application/x-sh");
        sMIMETypeMaps.put("shar", "application/x-shar");
        sMIMETypeMaps.put("sit", "application/x-stuffit");
        sMIMETypeMaps.put("spc", "application/x-pkcs7-certificates");
        sMIMETypeMaps.put("spl", "application/futuresplash");
        sMIMETypeMaps.put("src", "application/x-wais-source");
        sMIMETypeMaps.put("sst", "application/vnd.ms-pkicertstore");
        sMIMETypeMaps.put("stl", "application/vnd.ms-pkistl");
        sMIMETypeMaps.put("sv4cpio", "application/x-sv4cpio");
        sMIMETypeMaps.put("sv4crc", "application/x-sv4crc");
        sMIMETypeMaps.put("t", "application/x-troff");
        sMIMETypeMaps.put("tcl", "application/x-tcl");
        sMIMETypeMaps.put("tex", "application/x-tex");
        sMIMETypeMaps.put("texi", "application/x-texinfo");
        sMIMETypeMaps.put("texinfo", "application/x-texinfo");
        sMIMETypeMaps.put("tgz", "application/x-compressed");
        sMIMETypeMaps.put("tr", "application/x-troff");
        sMIMETypeMaps.put("trm", "application/x-msterminal");
        sMIMETypeMaps.put("ustar", "application/x-ustar");
        sMIMETypeMaps.put("vrml", "x-world/x-vrml");
        sMIMETypeMaps.put("wcm", "application/vnd.ms-works");
        sMIMETypeMaps.put("wdb", "application/vnd.ms-works");
        sMIMETypeMaps.put("wks", "application/vnd.ms-works");
        sMIMETypeMaps.put("wmf", "application/x-msmetafile");
        sMIMETypeMaps.put("wps", "application/vnd.ms-works");
        sMIMETypeMaps.put("wri", "application/x-mswrite");
        sMIMETypeMaps.put("wrl", "x-world/x-vrml");
        sMIMETypeMaps.put("wrz", "x-world/x-vrml");
        sMIMETypeMaps.put("xaf", "x-world/x-vrml");
        sMIMETypeMaps.put("xla", "application/vnd.ms-excel");
        sMIMETypeMaps.put("xlc", "application/vnd.ms-excel");
        sMIMETypeMaps.put("xlm", "application/vnd.ms-excel");
        sMIMETypeMaps.put("xlt", "application/vnd.ms-excel");
        sMIMETypeMaps.put("xlw", "application/vnd.ms-excel");
        sMIMETypeMaps.put("xof", "x-world/x-vrml");
        sMIMETypeMaps.put("z", "application/x-compress");
    }

    public static String getMIMEType(File file) {
        String type = "*/*";
        String fileExt = getFileExtension(file).toLowerCase(Locale.ENGLISH);

        if (fileExt.length() > 0) {
            type = sMIMETypeMaps.get(fileExt);
        }
        return type == null ? "*/*" : type;
    }

    public static String getMIMEType(ArrayList<String> paths) {
        if (paths == null || paths.size() <= 0) {
            return "*/*";
        }

        String type = getMIMEType(new File(paths.get(0)));
        for (int i = 1; i < paths.size(); i++) {
            String t = getMIMEType(new File(paths.get(i)));
            if (type == null || !type.equals(t)) {
                type = "*/*";
                break;
            }
        }

        return type;
    }

    public static Uri getContentUri(Context context, File file, String type) {
        Uri mUri = null;
        Uri queryUri = null;
        if (type.contains("image/")) {
            mUri = Uri.parse("content://media/external/images/media");
            queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if (type.contains("video/")) {
            mUri = Uri.parse("content://media/external/video/media");
            queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else {
            return Uri.fromFile(file);
        }
        Uri mImageUri = null;

        Cursor cursor = context.getContentResolver().query(queryUri, null,
                " _data = '" + file.getPath() + "'", null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor
                    .getColumnIndex(MediaStore.MediaColumns.DATA));
            int ringtoneID = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID);
            break;
        }

        if (mImageUri != null) {
            return mImageUri;
        } else {
            return Uri.fromFile(file);
        }

    }

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

    public static boolean exists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        return new File(path).exists();
    }

    public static boolean containsParent(ArrayList<String> fileList, String targetDir) {
        if (fileList.isEmpty() || TextUtils.isEmpty(targetDir)) {
            return false;
        }

        for (String path : fileList) {
            if (targetDir.startsWith(path)) {
                return true;
            }
        }

        return false;
    }
}

