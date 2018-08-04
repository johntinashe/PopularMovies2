package com.github.johntinashe.popularmovies.model;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Movie implements Parcelable {

    private static final String imageBaseURL = "http://image.tmdb.org/t/p/w342/";
    private static final String imageBaseBackdrop = "http://image.tmdb.org/t/p/w780/";

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<>();
    @SerializedName("id")
    private Integer id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("vote_average")
    private float voteAverage;

    public Movie() {
    }

    @SerializedName("genres")
    @Nullable
    private ArrayList<Genre> genres;

    public Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readInt();
        originalLanguage = in.readString();
        posterPath = in.readString();
        voteAverage = in.readFloat();
        originalTitle = in.readString();
        backdropPath = in.readString();
    }

    public Movie(String posterPath, String overview, String releaseDate, List<Integer> genreIds, Integer id, String originalTitle, String originalLanguage, String title, String backdropPath, float voteAverage, ArrayList<Genre> genres) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.genres = genres;
    }

    @BindingAdapter({"android:posterPath"})
    public static void loadPoster(ImageView view, String imageUrl) {
        Picasso.get().load(imageBaseURL+imageUrl).into(view);
    }

    @BindingAdapter({"android:backdropPath"})
    public static void loadBackdrop(ImageView view, String imageUrl) {
        Picasso.get().load(imageBaseBackdrop+imageUrl).into(view);
    }

    @BindingAdapter({"android:voteAverage"})
    public static void setRatingBar(RatingBar ratingBar ,float value) {
        ratingBar.setRating((float) (value/2.0));
    }

    @BindingAdapter({"android:voteAverage"})
    public static void setRating(TextView rating , float value) {
        rating.setText(String.format("%s", value));
    }

    @Nullable
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(@Nullable ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(id);
        dest.writeString(originalLanguage);
        dest.writeString(posterPath);
        dest.writeFloat(voteAverage);
        dest.writeString(originalTitle);
        dest.writeString(backdropPath);
    }

}
