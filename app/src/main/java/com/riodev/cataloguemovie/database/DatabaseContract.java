package com.riodev.cataloguemovie.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.riodev.cataloguemovie";
    public static final  String SCHEME = "content";

    public static final int DATABASE_VERSION = 1;
    public static final  String DATABASE_NAME = "dbMovie";
    public static final  String TABLE_NAME = "favoriteMovie";

    public static final class MovieColumn implements BaseColumns {
        public static final  String _ID = "_id";
        public static final  String TITLE = "title";
        public static final  String OVERVIEW = "overview";
        public static final  String POSTER_PATH = "poster_path";
        public static final  String RATING = "rating";
        public static final  String POPULARITY = "popularity";
        public static final  String LANGUAGE = "language";
        public static final  String GENRES = "genres";
        public static final  String RELEASE_DATE = "release_date";

        public static Uri CONTENT_URI = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
