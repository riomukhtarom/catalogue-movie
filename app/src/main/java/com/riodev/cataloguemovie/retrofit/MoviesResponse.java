package com.riodev.cataloguemovie.retrofit;

import com.google.gson.annotations.SerializedName;
import com.riodev.cataloguemovie.model.Movie;

import java.util.List;

public class MoviesResponse {
    @SerializedName("results")
    private List<Movie> listMovie;

    public List<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<Movie> listMovie) {
        this.listMovie = listMovie;
    }
}
