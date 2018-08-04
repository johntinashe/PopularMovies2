package com.github.johntinashe.popularmovies.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.github.johntinashe.popularmovies.BuildConfig;
import com.github.johntinashe.popularmovies.model.Movie;
import com.github.johntinashe.popularmovies.model.MoviesResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularMoviesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> moviesList = null;
    public int pageNumber;
    public int totalPages;
    private MutableLiveData<ArrayList<Movie>> newMovies;
    private  Call<MoviesResponse> call;

    private static final String API_KEY = BuildConfig.API_KEY;

    public MutableLiveData<ArrayList<Movie>> getMovies() {
        if (moviesList == null) {
            moviesList = new MutableLiveData<>();
            loadMovies();
        }
        return moviesList;
    }

    public MutableLiveData<ArrayList<Movie>> getNewMovies() {
        if (newMovies == null) {
            newMovies = new MutableLiveData<>();
            loadMore();
        }
        return newMovies;
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public void loadMore() {

        Api api = getRetrofit().create(Api.class);
        Call<MoviesResponse> call = api.getPopularMovies(API_KEY ,++pageNumber);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (moviesList != null){
                    newMovies.setValue(Objects.requireNonNull(response.body()).getResults());
                    pageNumber = Objects.requireNonNull(response.body()).getPage();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                newMovies.setValue(null);
            }
        });
    }

    private void loadMovies() {
         call = getCall();
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (moviesList != null){
                            moviesList.setValue(Objects.requireNonNull(response.body()).getResults());
                        }
                        totalPages = Objects.requireNonNull(response.body()).getTotalResults();
                        pageNumber = Objects.requireNonNull(response.body()).getPage();
                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                }
            });
    }
    private Call<MoviesResponse> getCall() {

        Api api = getRetrofit().create(Api.class);
        call = null;
        return call = api.getPopularMovies(API_KEY ,1);
    }

    public void refresh() {
        moviesList = null;
        getMovies();
    }

}
