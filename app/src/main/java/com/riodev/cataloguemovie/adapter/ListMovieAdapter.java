package com.riodev.cataloguemovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.riodev.cataloguemovie.R;
import com.riodev.cataloguemovie.RecyclerViewClickListener;
import com.riodev.cataloguemovie.api.MovieDBApi;
import com.riodev.cataloguemovie.model.Movie;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.MovieViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private RecyclerViewClickListener recyclerViewClickListener;

    public void setRecyclerViewClickListener(RecyclerViewClickListener recyclerViewClickListener) {
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public ListMovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MovieViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.tvTitle.setText(getListMovie().get(i).getTitle());
        movieViewHolder.tvOverview.setText(getListMovie().get(i).getOverview());
        String posterPath = getListMovie().get(i).getPosterPath();
        Glide.with(context).load(MovieDBApi.getPoster(posterPath) )
                .apply(new RequestOptions().override(60,90))
                .into(movieViewHolder.ivPoster);

        movieViewHolder.itemView.setOnClickListener(v -> {
            int itemPosition = movieViewHolder.getAdapterPosition();
            Log.e("position", String.valueOf(itemPosition));
            recyclerViewClickListener.onItemClicked(itemPosition);
        });
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvTitle = itemView.findViewById(R.id.tv_MovieTitle);
            tvOverview = itemView.findViewById(R.id.tv_MovieDesc);
            ivPoster = itemView.findViewById(R.id.iv_Poster);
        }

    }
}
