package com.github.johntinashe.popularmovies.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.github.johntinashe.popularmovies.database.AppDatabase;
import com.github.johntinashe.popularmovies.database.MovieEntry;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private final LiveData<List<MovieEntry>> movies;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        movies = database.movieDao().loadAllMovies();
    }

    public LiveData<List<MovieEntry>> getMovies() {
        return movies;
    }
}
