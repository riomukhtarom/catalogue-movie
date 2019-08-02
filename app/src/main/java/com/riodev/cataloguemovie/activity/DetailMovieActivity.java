package com.riodev.cataloguemovie.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.riodev.cataloguemovie.R;
import com.riodev.cataloguemovie.api.MovieDBApi;
import com.riodev.cataloguemovie.model.Movie;
import com.riodev.cataloguemovie.model.MovieHelper;

import java.util.ArrayList;

import static com.riodev.cataloguemovie.database.DatabaseContract.TABLE_NAME;

public class DetailMovieActivity extends AppCompatActivity {
    Movie movie;
    ImageView ivPoster;
    TextView tvTitle;
    TextView tvRating;
    TextView tvDuration;
    TextView tvLanguage;
    TextView tvRelease;
    TextView tvOverview;

    private boolean isFavorite = false;
    private Menu menuItem = null;
    private MovieHelper movieHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        if (getSupportActionBar()!=null){getSupportActionBar().setTitle(R.string.detail_movie);}

        movieHelper = new MovieHelper(this);

        ivPoster = findViewById(R.id.iv_Poster);
        tvTitle = findViewById(R.id.tv_MovieTitle);
        tvRating = findViewById(R.id.tv_MovieRatingValue);
        tvDuration = findViewById(R.id.tv_MovieDurationValue);
        tvLanguage = findViewById(R.id.tv_MovieLanguageValue);
        tvRelease = findViewById(R.id.tv_MovieReleaseValue);
        tvOverview = findViewById(R.id.tv_MovieOverviewValue);

        movie = getIntent().getParcelableExtra("movie");

        Glide.with(this).load(MovieDBApi.getPoster(movie.getPosterPath()) )
                .apply(new RequestOptions().override(120,180))
                .into(ivPoster);

        tvTitle.setText(movie.getTitle());
        tvRating.setText(movie.getRating());
        tvDuration.setText(movie.getPopularity());
        tvLanguage.setText(movie.getLanguage().toUpperCase());
        tvRelease.setText(movie.getReleaseDate());
        tvOverview.setText(movie.getOverview());

        favoriteState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_detail, menu);
        menuItem = menu;
        setFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_favorite :
                if (isFavorite){
                    removeFromFavoriteMovie();
                } else {
                    addToFavoriteMovie();
                }

                isFavorite = !isFavorite;
                setFavorite();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite(){
        if (isFavorite) {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites));
        } else {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites));
        }
    }

    private void addToFavoriteMovie(){
        movieHelper.open();
        long result = movieHelper.insert(TABLE_NAME, movie);
        if (result > 0){
            movie.setId(movie.getId());
            setToast(getString(R.string.add_to_favorite));
        } else {
            setToast(getString(R.string.add_failed));
        }
        movieHelper.close();
    }

    private void removeFromFavoriteMovie(){
        movieHelper.open();
        long result = movieHelper.delete(TABLE_NAME, movie.getId());
        if (result > 0){
            setToast(getString(R.string.delete_from_favorite));
        } else {
            setToast(getString(R.string.delete_failed));
        }
        movieHelper.close();
    }

    private void favoriteState(){
        movieHelper.open();
        ArrayList<Movie> favMovie = movieHelper.getMovie(TABLE_NAME, movie.getTitle());
        if (!favMovie.isEmpty()){
            isFavorite = true;
        }
        movieHelper.close();
    }

    private void setToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
