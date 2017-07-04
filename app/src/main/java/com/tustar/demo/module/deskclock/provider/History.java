package com.tustar.demo.module.deskclock.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tustar on 11/5/16.
 */
public class History implements Parcelable, HistoryContract.HistoriesColumns {

    public long id;
    public String formula;
    public String result;
    public Date createdAt;

    /**
     * Histories start with an invalid id when it hasn't been saved to the database.
     */
    public static final long INVALID_ID = -1;

    /**
     * The default format
     */
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS Z");

    /**
     * The default sort order for this table
     */
    private static final String DEFAULT_SORT_ORDER = HistoryDatabaseHelper.HISTORIES_TABLE_NAME + "."
            + CREATED_AT + " DESC";

    private static final String[] QUERY_COLUMNS = {
            _ID,
            FORMULA,
            RESULT,
            CREATED_AT
    };

    /**
     * These save calls to cursor.getColumnIndexOrThrow()
     * THEY MUST BE KEPT IN SYNC WITH ABOVE QUERY COLUMNS
     */
    private static final int ID_INDEX = 0;
    private static final int FORMULA_INDEX = 1;
    private static final int RESULT_INDEX = 2;
    private static final int CREATED_AT_INDEX = 3;
    private static final int COLUMN_COUNT = CREATED_AT_INDEX + 1;


    public History() {
        id = INVALID_ID;
    }

    public History(long id, String formula, String result, Date createdAt) {
        this.id = id;
        this.formula = formula;
        this.result = result;
        this.createdAt = createdAt;
    }

    public History(Cursor cursor) {
        id = cursor.getLong(ID_INDEX);
        formula = cursor.getString(FORMULA_INDEX);
        result = cursor.getString(RESULT_INDEX);
        try {
            createdAt = DEFAULT_DATE_FORMAT.parse(cursor.getString(CREATED_AT_INDEX));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected History(Parcel in) {
        id = in.readLong();
        formula = in.readString();
        result = in.readString();
        createdAt = (Date) in.readSerializable();
    }

    public static ContentValues createContentValues(History history) {
        ContentValues values = new ContentValues(COLUMN_COUNT);
        if (history.id != INVALID_ID) {
            values.put(_ID, history.id);
        }
        values.put(FORMULA, history.formula);
        values.put(RESULT, history.result);
        values.put(CREATED_AT, DEFAULT_DATE_FORMAT.format(history.createdAt));
        return values;
    }

    public static Intent createIntent(Context context, Class<?> cls, long historyId) {
        return new Intent(context, cls).setData(getUri(historyId));
    }

    public static Uri getUri(long historyId) {
        return ContentUris.withAppendedId(CONTENT_URI, historyId);
    }

    public static long getId(Uri contentUri) {
        return ContentUris.parseId(contentUri);
    }

    /**
     * Get history cursor loader for all histories.
     *
     * @param context to query the database.
     * @return cursor loader with all the histories.
     */
    public static CursorLoader getHistoriesCursorLoader(Context context) {
        return new CursorLoader(context, CONTENT_URI, QUERY_COLUMNS, null, null,
                DEFAULT_SORT_ORDER);
    }

    /**
     * Get history by id.
     *
     * @param cr        to perform the query on.
     * @param historyId for the desired history.
     * @return alarm if found, null otherwise
     */
    public static History getHistory(ContentResolver cr, long historyId) {
        try (Cursor cursor = cr.query(getUri(historyId), QUERY_COLUMNS, null, null, null)) {
            if (cursor.moveToFirst()) {
                return new History(cursor);
            }
        }

        return null;
    }

    /**
     * Get all histories given conditions.
     *
     * @param cr            to perform the query on.
     * @param selection     A filter declaring which rows to return, formatted as an
     *                      SQL WHERE clause (excluding the WHERE itself). Passing null will
     *                      return all rows for the given URI.
     * @param selectionArgs You may include ?s in selection, which will be
     *                      replaced by the values from selectionArgs, in the order that they
     *                      appear in the selection. The values will be bound as Strings.
     * @return list of histories matching where clause or empty list if none found.
     */
    public static List<History> getHistories(ContentResolver cr, String selection,
                                             String... selectionArgs) {
        final List<History> histories = new LinkedList<>();
        try (Cursor cursor = cr.query(CONTENT_URI, QUERY_COLUMNS, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    histories.add(new History(cursor));
                } while (cursor.moveToNext());
            }
        }

        return histories;
    }

    public static History addHistory(ContentResolver cr, History history) {
        ContentValues values = createContentValues(history);
        Uri uri = cr.insert(CONTENT_URI, values);
        history.id = getId(uri);
        return history;
    }

    public static boolean updateHistory(ContentResolver cr, History history) {
        if (history.id == INVALID_ID) {
            return false;
        }
        ContentValues values = createContentValues(history);
        long updatedRows = cr.update(getUri(history.id), values, null, null);
        return updatedRows == 1;
    }


    public static boolean deleteHistory(ContentResolver cr, long historyId) {
        if (historyId == INVALID_ID) {
            return false;
        }
        long deletedRows = cr.delete(getUri(historyId), "", null);
        return deletedRows == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof History)) {
            return false;
        }

        final History other = (History) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", formula='" + formula + '\'' +
                ", result='" + result + '\'' +
                ", createdAt=" + DEFAULT_DATE_FORMAT.format(createdAt) +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(formula);
        dest.writeString(result);
        dest.writeSerializable(createdAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
