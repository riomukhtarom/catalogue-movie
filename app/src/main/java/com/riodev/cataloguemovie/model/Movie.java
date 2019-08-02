package com.riodev.cataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Movie implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("original_language")
    private String language;

    @SerializedName("genre_ids")
    private int[] genres;

    @SerializedName("release_date")
    private String releaseDate;

    public Movie(JSONObject object) {
        try {
            String id = object.getString("id");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String posterPath = object.getString("poster_path");
            String rating = object.getString("vote_average");
            String popularity = object.getString("popularity");
            String language = object.getString("original_language");
            String genres = object.getString("genre_ids");
            String releaseDate = object.getString("release_date");
            this.id = id;
            this.title = title;
            this.overview = overview;
            this.posterPath = posterPath;
            this.rating = rating;
            this.popularity = popularity;
            this.language = language;
//            this.genres = genres;
            this.releaseDate = releaseDate;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Movie(){}

    public Movie(String title, String overview, String posterPath) {
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public Movie(String id, String title, String overview, String posterPath, String rating, String popularity, String language, String genres, String releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.rating = rating;
        this.popularity = popularity;
        this.language = language;
//        this.genres = genres;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

//    public String getGenres() {
//        return genres;
//    }
//
//    public void setGenres(String genres) {
//        this.genres = genres;
//    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.rating);
        dest.writeString(this.popularity);
        dest.writeString(this.language);
//        dest.writeString(this.genres);
        dest.writeString(this.releaseDate);
    }

    protected Movie(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.rating = in.readString();
        this.popularity = in.readString();
        this.language = in.readString();
//        this.genres = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
