package com.example.vvost.moviesapppart1.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MoviesContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelperMovies dbHelperMovies;
    static final int MOVIESNUMBER = 100;
    static final int MOVIESNUMBERID = 101;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelperMovies = new DbHelperMovies(context);
        return true;
    }

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContractDB.AUTHORITY;
        matcher.addURI(authority, MovieContractDB.PATH_MOVIES, MOVIESNUMBER);
        matcher.addURI(authority, MovieContractDB.PATH_MOVIES + "/#", MOVIESNUMBERID);

        return matcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIESNUMBERID:
                String queryid = uri.getPathSegments().get(1);
                selection = "movie_id=" + queryid;
                break;
            case MOVIESNUMBER:
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor = dbHelperMovies.getReadableDatabase().query(
                MovieContractDB.SingleMovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase insertdatabase = dbHelperMovies.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri UriForReturn;
        switch (match) {
            case MOVIESNUMBER: {

                long _id = insertdatabase.insert(MovieContractDB.SingleMovieEntry.TABLE_NAME, null, values);
                if (_id >= 0) {
                    UriForReturn = MovieContractDB.SingleMovieEntry.buildMovieUri(_id);
                } else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return UriForReturn;


    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int howmuchdeleted = 0;
        final SQLiteDatabase dbdelete = dbHelperMovies.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIESNUMBERID:
                String id = uri.getPathSegments().get(1);
                howmuchdeleted = dbdelete.delete(
                        MovieContractDB.SingleMovieEntry.TABLE_NAME, "movie_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (howmuchdeleted != 0)
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        return howmuchdeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
