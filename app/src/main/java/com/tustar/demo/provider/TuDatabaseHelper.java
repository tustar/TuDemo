package com.tustar.demo.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tustar.demo.util.Logger;

/**
 * Created by tustar on 11/5/16.
 */
public class TuDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "TuDatabaseHelper";
    /**
     * Original Tu Database.
     **/
    private static final int VERSION_1 = 1;

    // Database and table names
    static final String DATABASE_NAME = "tu.db";
    static final String HISTORIES_TABLE_NAME = "histories";

    private static void createHistoriesTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + HISTORIES_TABLE_NAME + " (" +
                TuContract.HistoriesColumns._ID + " INTEGER PRIMARY KEY," +
                TuContract.HistoriesColumns.FORMULA + " TEXT NOT NULL, " +
                TuContract.HistoriesColumns.RESULT + " TEXT NOT NULL, " +
                TuContract.HistoriesColumns.CREATED_AT + " TEXT NOT NULL);");
        Logger.i(TAG, "Histories Table created");
    }

    public TuDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createHistoriesTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.v(TAG, "onUpgrade :: oldVersion = " + oldVersion + ", newVersion = " + newVersion);
        if (oldVersion <= VERSION_1) {
            // This was not used in VERSION_7 or prior, so we can just drop it.
            db.execSQL("DROP TABLE IF EXISTS " + HISTORIES_TABLE_NAME + ";");

            createHistoriesTable(db);
        }

    }
}
