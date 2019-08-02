package com.riodev.cataloguemovie.retrofit;

import android.util.Log;

import com.riodev.cataloguemovie.BuildConfig;
import com.riodev.cataloguemovie.model.Movie;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static final String BASE_URL = BuildConfig.BASE_URL+"/";
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String EN = "en-US";
    private static NetworkClient mInstance;
    private Retrofit retrofit;


    private NetworkClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.e("URL", BASE_URL);
    }

    public static synchronized NetworkClient getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

    public void getMovies(final MoviesCallback callback){
        getApi().getPopularMovie(API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if(response.isSuccessful()){
                    MoviesResponse moviesResponse = response.body();
                    if (moviesResponse!=null && moviesResponse.getListMovie()!=null){
                        Log.e("respose", Arrays.toString(moviesResponse.getListMovie().toArray()));
                        callback.onSuccess(moviesResponse.getListMovie());
                    } else {
                        callback.onError("Err");
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                callback.onError(t.toString());
            }
        });
    }
}
