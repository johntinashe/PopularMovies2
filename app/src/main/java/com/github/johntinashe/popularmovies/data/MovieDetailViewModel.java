package com.github.johntinashe.popularmovies.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.github.johntinashe.popularmovies.BuildConfig;
import com.github.johntinashe.popularmovies.database.AppDatabase;
import com.github.johntinashe.popularmovies.database.MovieEntry;
import com.github.johntinashe.popularmovies.model.Cast;
import com.github.johntinashe.popularmovies.model.CastResponse;
import com.github.johntinashe.popularmovies.model.Genre;
import com.github.johntinashe.popularmovies.model.Movie;
import com.github.johntinashe.popularmovies.model.Review;
import com.github.johntinashe.popularmovies.model.ReviewsResponse;
import com.github.johntinashe.popularmovies.model.Trailer;
import com.github.johntinashe.popularmovies.model.TrailersResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailViewModel extends AndroidViewModel{

    private MutableLiveData<ArrayList<Cast>> mutableLiveData;
    private MutableLiveData<ArrayList<Genre>> genreLiveData;
    private MutableLiveData<ArrayList<Trailer>> trailerLiveData;
    private MutableLiveData<ArrayList<Review>> reviewsLiveData;

    private static final String API_KEY = BuildConfig.API_KEY;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Cast>> getCasts(int movieId) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            loadCast(movieId);
        }
        return mutableLiveData;
    }

    public MutableLiveData<ArrayList<Genre>> getGenresLiveData(int movieId) {
        if (genreLiveData == null) {
            genreLiveData = new MutableLiveData<>();
            getGenres(movieId);
        }
        return genreLiveData;
    }

    public MutableLiveData<ArrayList<Trailer>> getTrailersLiveData(int movieId) {
        if (trailerLiveData == null) {
            trailerLiveData = new MutableLiveData<>();
            getTrailers(movieId);
        }
        return trailerLiveData;
    }

    public MutableLiveData<ArrayList<Review>> getReviewsLiveData(int movieId) {
        if (reviewsLiveData == null) {
            reviewsLiveData = new MutableLiveData<>();
            getReviews(movieId);
        }
        return reviewsLiveData;
    }

    private void getReviews(int movieId) {

        Api api = RetrofitClient.getRetrofit().create(Api.class);
        Call<ReviewsResponse> call = api.getReviews(movieId,API_KEY);

        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewsResponse> call, @NonNull Response<ReviewsResponse> response) {
                if (reviewsLiveData != null) {
                    reviewsLiveData.setValue(Objects.requireNonNull(response.body()).getReviews());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewsResponse> call, @NonNull Throwable t) {
                trailerLiveData.setValue(null);
            }
        });
    }

    private void getTrailers(int movieId) {

        Api api = RetrofitClient.getRetrofit().create(Api.class);
        Call<TrailersResponse> call = api.getTrailers(movieId,API_KEY);

        call.enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailersResponse> call, @NonNull Response<TrailersResponse> response) {
                if (trailerLiveData != null) {
                    trailerLiveData.setValue(Objects.requireNonNull(response.body()).getTrailers());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailersResponse> call, @NonNull Throwable t) {
                trailerLiveData.setValue(null);
            }
        });
    }


    private void getGenres(int movieId) {

        Api api = RetrofitClient.getRetrofit().create(Api.class);
        Call<Movie> call = api.getMovieDetails(movieId,API_KEY);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (genreLiveData != null) {
                    genreLiveData.setValue(Objects.requireNonNull(response.body()).getGenres());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                genreLiveData.setValue(null);
            }
        });

    }

    private void loadCast(int movieId) {

        Api api = RetrofitClient.getRetrofit().create(Api.class);
        Call<CastResponse> call = api.getCasts(movieId,API_KEY);

        call.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(@NonNull Call<CastResponse> call, @NonNull Response<CastResponse> response) {
                if (mutableLiveData != null) {
                    mutableLiveData.setValue(Objects.requireNonNull(response.body()).getCasts());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CastResponse> call, @NonNull Throwable t) {
                mutableLiveData = null;
            }
        });
    }

    public LiveData<MovieEntry> checkFavorites(int id) {
        AppDatabase database = AppDatabase.getInstance(getApplication());
        return database.movieDao().loadById(id);
    }
}
