package com.riodev.cataloguemovie.retrofit;

import com.riodev.cataloguemovie.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovie(
            @Query("api_key") String apiKey
    );
}
