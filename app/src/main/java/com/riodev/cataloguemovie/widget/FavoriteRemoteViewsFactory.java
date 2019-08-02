package com.riodev.cataloguemovie.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.riodev.cataloguemovie.BuildConfig;
import com.riodev.cataloguemovie.R;
import com.riodev.cataloguemovie.api.MovieDBApi;
import com.riodev.cataloguemovie.model.Movie;
import com.riodev.cataloguemovie.model.MovieHelper;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.riodev.cataloguemovie.MappingHelper.mapCursorToArrayList;
import static com.riodev.cataloguemovie.database.DatabaseContract.MovieColumn.CONTENT_URI;
import static com.riodev.cataloguemovie.database.DatabaseContract.TABLE_NAME;

public class FavoriteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Movie> listMovie = new ArrayList<>();
    private Context context;
    private MovieHelper movieHelper;

    public FavoriteRemoteViewsFactory(Context context) {
        this.context = context;
        movieHelper = new MovieHelper(context);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        listMovie.clear();
        movieHelper.open();
        listMovie = movieHelper.getAllData(TABLE_NAME);
        movieHelper.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = null;
        String posterPath = listMovie.get(position).getPosterPath();
        String title = listMovie.get(position).getTitle();

        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(MovieDBApi.getPoster(posterPath))
                    .into(800,600).get();
        } catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load ", "error");
        }

        remoteViews.setImageViewBitmap(R.id.fav_img, bitmap);
        remoteViews.setTextViewText(R.id.fav_title, title);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
