package com.riodev.cataloguemovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.riodev.cataloguemovie.database.DatabaseContract.DATABASE_NAME;
import static com.riodev.cataloguemovie.database.DatabaseContract.DATABASE_VERSION;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.GENRES;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.LANGUAGE;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.OVERVIEW;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.POPULARITY;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.POSTER_PATH;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.RATING;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.RELEASE_DATE;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.TITLE;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn._ID;
import static com.riodev.cataloguemovie.database.DatabaseContract.TABLE_NAME;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static String CREATE_TABLE=
            "create table "+ TABLE_NAME +" ("+
                    _ID+" text not null, "+
                    TITLE+" text not null, "+
                    OVERVIEW+" text not null, "+
                    POSTER_PATH+" text not null, "+
                    RATING+" text not null, "+
                    POPULARITY+" text not null, "+
                    LANGUAGE+" text not null, "+
                    GENRES+" text not null, "+
                    RELEASE_DATE+" text not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME+";";
        db.execSQL(query);
        onCreate(db);
    }
}
