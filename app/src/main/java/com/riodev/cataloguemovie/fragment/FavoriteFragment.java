package com.riodev.cataloguemovie.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.riodev.cataloguemovie.R;
import com.riodev.cataloguemovie.RecyclerViewClickListener;
import com.riodev.cataloguemovie.activity.DetailMovieActivity;
import com.riodev.cataloguemovie.adapter.ListMovieAdapter;
import com.riodev.cataloguemovie.model.Movie;
import com.riodev.cataloguemovie.model.MovieHelper;

import java.util.ArrayList;

import static com.riodev.cataloguemovie.database.DatabaseContract.TABLE_NAME;

public class FavoriteFragment extends Fragment implements RecyclerViewClickListener {

    private RecyclerView rvListMovie;
    private ProgressBar progressBarMovie;
    private ArrayList<Movie> listMovie;
    private MovieHelper movieHelper;
    private ListMovieAdapter listMovieAdapter;
    private final int INTENT_CODE = 1;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);
        rvListMovie = view.findViewById(R.id.rv_ListMovie);
        progressBarMovie = view.findViewById(R.id.pb_movie);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieHelper = new MovieHelper(getActivity());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CODE){
            listMovie.clear();
            loadData();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", listMovieAdapter.getListMovie());
    }

    @Override
    public void onItemClicked(int position) {
        Movie movie = listMovie.get(position);

        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra("movie", movie);

        startActivityForResult(intent, INTENT_CODE);
    }

    private void loadData(){
        new MovieAsyncTask().execute();
    }

    private class MovieAsyncTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rvListMovie.setVisibility(View.GONE);
            progressBarMovie.setVisibility(View.VISIBLE);
            movieHelper.open();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return movieHelper.getAllData(TABLE_NAME);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            rvListMovie.setVisibility(View.VISIBLE);
            progressBarMovie.setVisibility(View.GONE);

            listMovie.addAll(movies);
            movieHelper.close();
        }

    }

}
