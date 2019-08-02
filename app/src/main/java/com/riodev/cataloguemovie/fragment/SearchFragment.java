package com.riodev.cataloguemovie.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.riodev.cataloguemovie.activity.DetailMovieActivity;
import com.riodev.cataloguemovie.NetworkUtils;
import com.riodev.cataloguemovie.R;
import com.riodev.cataloguemovie.RecyclerViewClickListener;
import com.riodev.cataloguemovie.adapter.ListMovieAdapter;
import com.riodev.cataloguemovie.api.MovieDBApi;
import com.riodev.cataloguemovie.model.Movie;
import com.riodev.cataloguemovie.retrofit.Api;
import com.riodev.cataloguemovie.retrofit.MoviesCallback;
import com.riodev.cataloguemovie.retrofit.MoviesResponse;
import com.riodev.cataloguemovie.retrofit.NetworkClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.riodev.cataloguemovie.BuildConfig.BASE_URL;
import static com.riodev.cataloguemovie.BuildConfig.TMDB_API_KEY;


public class SearchFragment extends Fragment implements RecyclerViewClickListener, SearchView.OnQueryTextListener {

    private RecyclerView rvListMovie;
    private ProgressBar progressBarMovie;
    private SearchView searchView;
    private ListMovieAdapter listMovieAdapter;
    private ArrayList<Movie> listMovie;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        rvListMovie = view.findViewById(R.id.rv_ListMovie);
        progressBarMovie = view.findViewById(R.id.pb_movie);
        searchView = new SearchView(getActivity());
        searchView = view.findViewById(R.id.sv_searchMovie);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listMovie = new ArrayList<>();

        rvListMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovieAdapter = new ListMovieAdapter(getActivity());
        rvListMovie.setAdapter(listMovieAdapter);

        listMovieAdapter.setListMovie(listMovie);
        listMovieAdapter.setRecyclerViewClickListener(this);

        searchView.setOnQueryTextListener(this);

        if (savedInstanceState == null) {
            loadData();
        } else {
            listMovie = savedInstanceState.getParcelableArrayList("list");
            if (listMovie!=null){
                listMovieAdapter.setListMovie(listMovie);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", listMovieAdapter.getListMovie());
    }

    private void loadData(){
//        URL url = MovieDBApi.getPopularMovie();
//        new MovieAsyncTask().execute();

        rvListMovie.setVisibility(View.GONE);
        progressBarMovie.setVisibility(View.VISIBLE);

        NetworkClient.getInstance().getMovies(new MoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                listMovie.addAll(movies);
                listMovieAdapter.setListMovie(listMovie);

                rvListMovie.setVisibility(View.VISIBLE);
                progressBarMovie.setVisibility(View.GONE);

            }

            @Override
            public void onError(String error) {
                Log.e("searcf", error);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listMovie.clear();
        String search = query.toLowerCase();
        searchQuery(search);
        listMovieAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onItemClicked(int position) {
        Movie movie = listMovie.get(position);

        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra("movie", movie);

        startActivity(intent);
    }

    private void searchQuery(String query){
        URL url = MovieDBApi.getMovieSearch(query);
        new MovieAsyncTask().execute();
    }

    private class MovieAsyncTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            ArrayList<Movie> list = new ArrayList<>();
            NetworkClient.getInstance().getApi().getPopularMovie(TMDB_API_KEY).enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    if(response.isSuccessful()){
                        MoviesResponse moviesResponse = response.body();
                        if (moviesResponse!=null && moviesResponse.getListMovie()!=null){
                            Log.e("respose", Arrays.toString(moviesResponse.getListMovie().toArray()));
//                            callback.onSuccess(moviesResponse.getListMovie());
                            listMovie.addAll(moviesResponse.getListMovie());
                            listMovieAdapter.setListMovie(listMovie);
                        } else {
//                            callback.onError("Err");
                        }
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
//                    callback.onError(t.toString());
                }
            });
            Log.e("data search backgroud", Arrays.toString(list.toArray()));
            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rvListMovie.setVisibility(View.GONE);
            progressBarMovie.setVisibility(View.VISIBLE);
        }

//        @Override
//        protected String doInBackground(URL... urls) {
////            URL url = urls[0];
//            String result = "";
////            try {
////                result = NetworkUtils.getFromNetwork(url);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            return result;
//
//            return result;
//        }

        @Override
        protected void onPostExecute(ArrayList<Movie> list) {
            super.onPostExecute(list);
            rvListMovie.setVisibility(View.VISIBLE);
            progressBarMovie.setVisibility(View.GONE);

            Log.e("data search", Arrays.toString(list.toArray()));

            listMovie.addAll(list);
            listMovieAdapter.setListMovie(listMovie);

        }
    }


}
