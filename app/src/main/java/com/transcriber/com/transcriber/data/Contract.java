package com.transcriber.com.transcriber.data;

import android.provider.BaseColumns;


public class Contract {

    public static class TABLE_TODO implements BaseColumns {
        public static final String TABLE_NAME = "todoitems";

        // was going to use a edit view to edit the done opition but did not get around to it

        public static final String COLUMN_NAME_TITLE = "description";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_TEXT = "done";

    }
}