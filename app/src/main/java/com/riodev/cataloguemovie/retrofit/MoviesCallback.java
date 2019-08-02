package com.riodev.cataloguemovie.retrofit;

import com.riodev.cataloguemovie.model.Movie;

import java.util.List;

public interface MoviesCallback {

    void onSuccess(List<Movie> movies);
    void onError(String error);
}
