package com.riodev.cataloguemovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.riodev.cataloguemovie.model.MovieHelper;

import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.CONTENT_URI;
import static com.riodev.cataloguemovie.database.DatabaseContract.TABLE_NAME;
import static com.riodev.cataloguemovie.database.DatabaseContract.AUTHORITY;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);

        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        movieHelper.open();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider(TABLE_NAME);
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(TABLE_NAME, uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
//        cursor.setNotificationUri(getContext(),getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        movieHelper.open();
        long added;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(TABLE_NAME, values);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Nullable
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        movieHelper.open();
        int deleted;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                deleted = movieHelper.deleteProvider(TABLE_NAME, uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI,null);

        return deleted;
    }

    @Nullable
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        movieHelper.open();
        int updated;
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = movieHelper.updateProvider(TABLE_NAME, uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return updated;
    }
}
