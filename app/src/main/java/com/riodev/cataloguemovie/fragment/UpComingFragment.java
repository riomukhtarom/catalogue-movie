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

import com.riodev.cataloguemovie.NetworkUtils;
import com.riodev.cataloguemovie.R;
import com.riodev.cataloguemovie.RecyclerViewClickListener;
import com.riodev.cataloguemovie.activity.DetailMovieActivity;
import com.riodev.cataloguemovie.adapter.ListMovieAdapter;
import com.riodev.cataloguemovie.api.MovieDBApi;
import com.riodev.cataloguemovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class UpComingFragment extends Fragment implements RecyclerViewClickListener {

    private RecyclerView rvListMovie;
    private ProgressBar progressBarMovie;
    private ArrayList<Movie> listMovie;
    private ListMovieAdapter listMovieAdapter;

    public UpComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_up_coming, container, false);
        rvListMovie = view.findViewById(R.id.rv_ListMovie);
        progressBarMovie = view.findViewById(R.id.pb_movie);

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
        outState.putParcelableArrayList("list", listMovie);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClicked(int position) {
        Movie movie = listMovie.get(position);

        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra("movie", movie);

        startActivity(intent);
    }

    private void loadData(){
        URL url = MovieDBApi.getUpComingMovie();
        new MovieAsyncTask().execute(url);
    }

    private class MovieAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rvListMovie.setVisibility(View.GONE);
            progressBarMovie.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result = null;
            try {
                result = NetworkUtils.getFromNetwork(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            rvListMovie.setVisibility(View.VISIBLE);
            progressBarMovie.setVisibility(View.GONE);

            Log.e("data up", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Movie movie = new Movie(object);
                    listMovie.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
