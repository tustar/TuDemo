package com.tustar.demo.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.tustar.demo.BuildConfig;

/**
 * Created by tustar on 11/5/16.
 */
public final class TuContract {

    /**
     * This authority is used for writing to or querying from the tu
     * provider.
     */
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID;

    /**
     * This utility class cannot be instantiated
     */
    private TuContract() {}

    /**
     * Constants for the Histories table, which contains the user created histories.
     */
    protected interface HistoriesColumns extends BaseColumns {

        /**
         * The content:// style URL for this table.
         */
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/histories");

        /**
         * History formula
         *
         * <p>Type: STRING</p>
         */
        String FORMULA = "formula";

        /**
         * History result
         *
         * <p>Type: STRING</p>
         */
        String RESULT = "result";

        /**
         * History created_at
         *
         * <p>Format: yyyy-MM-dd HH:mm:ss.SSS Z</p>
         *
         * <p>Type: STRING</p>
         */
        String CREATED_AT = "created_at";

    }
}
