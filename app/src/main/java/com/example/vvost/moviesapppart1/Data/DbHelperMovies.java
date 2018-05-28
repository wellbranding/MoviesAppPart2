package com.example.vvost.moviesapppart1.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelperMovies extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "movies1.db";

    public DbHelperMovies(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_MOVIE = "CREATE TABLE " + MovieContractDB.SingleMovieEntry.TABLE_NAME + "(" +
                MovieContractDB.SingleMovieEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieContractDB.SingleMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContractDB.SingleMovieEntry.MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContractDB.SingleMovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL " + ");";

        db.execSQL(CREATE_TABLE_MOVIE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContractDB.SingleMovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
