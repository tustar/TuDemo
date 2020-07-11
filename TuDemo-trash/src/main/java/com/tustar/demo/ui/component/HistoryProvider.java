package com.tustar.demo.ui.component;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.tustar.util.Logger;

import java.util.Map;

import androidx.annotation.Nullable;

import static com.tustar.demo.ui.component.HistoryContract.HistoriesColumns;
import static com.tustar.demo.ui.component.HistoryDatabaseHelper.HISTORIES_TABLE_NAME;

/**
 * Created by tustar on 11/5/16.
 */
public class HistoryProvider extends ContentProvider {
    private static final String TAG = "HistoryProvider";
    private HistoryDatabaseHelper mOpenHelper;

    private static final int HISTORIES = 1;
    private static final int HISTORIES_ID = 2;

    private static final Map<String, String> sHistoriesProjection = new ArrayMap<>();

    static {
        sHistoriesProjection.put(HISTORIES_TABLE_NAME + "." + HistoriesColumns._ID,
                HISTORIES_TABLE_NAME + "." + HistoriesColumns._ID);
        sHistoriesProjection.put(HISTORIES_TABLE_NAME + "." + HistoriesColumns.FORMULA,
                HISTORIES_TABLE_NAME + "." + HistoriesColumns.FORMULA);
        sHistoriesProjection.put(HISTORIES_TABLE_NAME + "." + HistoriesColumns.RESULT,
                HISTORIES_TABLE_NAME + "." + HistoriesColumns.RESULT);
        sHistoriesProjection.put(HISTORIES_TABLE_NAME + "." + HistoriesColumns.CREATED_AT,
                HISTORIES_TABLE_NAME + "." + HistoriesColumns.CREATED_AT);
    }

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(HistoryContract.AUTHORITY, "histories", HISTORIES);
        sURIMatcher.addURI(HistoryContract.AUTHORITY, "histories/#", HISTORIES_ID);
    }

    public HistoryProvider() {

    }

    @Override
    public boolean onCreate() {
        final Context context = getContext();
        final Context storageContext;
//        if (DeviceUtils.isNOrLater()) {
            // All N devices have split storage areas, but we may need to
            // migrate existing database into the new device encrypted
            // storage area, which is where our DATAS lives from now on.
//            storageContext = context.createDeviceProtectedStorageContext();
//            if (!storageContext.moveDatabaseFrom(context, HistoryDatabaseHelper.DATABASE_NAME)) {
//                Logger.wtf("Failed to migrate database: %s", HistoryDatabaseHelper.DATABASE_NAME);
//            }
//        } else {
            storageContext = context;
//        }

        mOpenHelper = new HistoryDatabaseHelper(storageContext);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        // Generate the body of the query
        int match = sURIMatcher.match(uri);
        switch (match) {
            case HISTORIES:
                qb.setTables(HISTORIES_TABLE_NAME);
                break;
            case HISTORIES_ID:
                qb.setTables(HISTORIES_TABLE_NAME);
                qb.appendWhere(HistoriesColumns._ID + "=");
                qb.appendWhere(uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        if (cursor == null) {
            Logger.e(TAG, "Histories.query: failed");
        } else {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = sURIMatcher.match(uri);
        switch (match) {
            case HISTORIES:
                return "vnd.android.cursor.dir/histories";
            case HISTORIES_ID:
                return "vnd.android.cursor.item/histories";
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sURIMatcher.match(uri);
        switch (match) {
            case HISTORIES:
                rowId = db.insert(HISTORIES_TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Cannot insert from URI: " + uri);
        }

        Uri resultUri = ContentUris.withAppendedId(uri, rowId);
        notifyChange(resultUri);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        int count;
        String primaryKey;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sURIMatcher.match(uri);
        switch (match) {
            case HISTORIES:
                count = db.delete(HISTORIES_TABLE_NAME, where, whereArgs);
                break;
            case HISTORIES_ID:
                primaryKey = uri.getLastPathSegment();
                if (TextUtils.isEmpty(where)) {
                    where = HistoriesColumns._ID + "=" + primaryKey;
                } else {
                    where = HistoriesColumns._ID + "=" + primaryKey + " AND (" + where + ")";
                }
                count = db.delete(HISTORIES_TABLE_NAME, where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Cannot delete from URI: " + uri);
        }
        notifyChange(uri);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        int count;
        String historyId;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sURIMatcher.match(uri);
        switch (match) {
            case HISTORIES_ID:
                historyId = uri.getLastPathSegment();
                count = db.update(HISTORIES_TABLE_NAME, values,
                        HistoriesColumns._ID + "=" + historyId,
                        null);
                break;
            default:
                throw new UnsupportedOperationException("Cannot update URI: " + uri);
        }

        notifyChange(uri);
        return count;
    }

    /**
     * Notify affected URIs of changes.
     */
    private void notifyChange(Uri uri) {
        ContentResolver resolver = getContext().getContentResolver();
        resolver.notifyChange(uri, null);
        final int match = sURIMatcher.match(uri);
        // Also notify the joined table of changes to instances or alarms.
        if (match == HISTORIES || match == HISTORIES_ID) {
            resolver.notifyChange(HistoriesColumns.CONTENT_URI, null);
        }
    }
}
