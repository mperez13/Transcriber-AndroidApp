package com.transcriber.com.transcriber.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static com.transcriber.com.transcriber.data.Contract.TABLE_TODO.COLUMN_NAME_CATEGORY;
import static com.transcriber.com.transcriber.data.Contract.TABLE_TODO.COLUMN_NAME_TEXT;
import static com.transcriber.com.transcriber.data.Contract.TABLE_TODO.COLUMN_NAME_TITLE;
import static com.transcriber.com.transcriber.data.Contract.TABLE_TODO.TABLE_NAME;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "items.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_TODO.TABLE_NAME + " ("+
                Contract.TABLE_TODO._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_CATEGORY + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_TEXT + " TEXT NULL " +"); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table " + Contract.TABLE_TODO.TABLE_NAME + " if exists;");
    }

    public boolean insertItem(String title, String text, String catagory) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE, title);
        contentValues.put(COLUMN_NAME_TEXT, text);
        contentValues.put(COLUMN_NAME_CATEGORY, catagory);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateItem(Integer id, String title, String text, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE, title);
        contentValues.put(COLUMN_NAME_TEXT, text);
        contentValues.put(COLUMN_NAME_CATEGORY, category);
        db.update(TABLE_NAME, contentValues, _ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
}