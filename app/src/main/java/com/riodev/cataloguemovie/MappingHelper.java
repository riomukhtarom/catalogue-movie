package com.riodev.cataloguemovie;

import android.database.Cursor;


import com.riodev.cataloguemovie.model.Movie;

import java.util.ArrayList;

import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.GENRES;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.LANGUAGE;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.OVERVIEW;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.POPULARITY;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.POSTER_PATH;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.RATING;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.RELEASE_DATE;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.TITLE;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn._ID;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> movieList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            String id = notesCursor.getString(notesCursor.getColumnIndexOrThrow(_ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW));
            String posterPath = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POSTER_PATH));
            String rating = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RATING));
            String popularity = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POPULARITY));
            String language = notesCursor.getString(notesCursor.getColumnIndexOrThrow(LANGUAGE));
            String genres = notesCursor.getString(notesCursor.getColumnIndexOrThrow(GENRES));
            String releaseDate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASE_DATE));
            movieList.add(new Movie(id, title, overview, posterPath, rating, popularity, language, genres, releaseDate));
        }

        return movieList;
    }
}
