/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.johntinashe.popularmovies.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.github.johntinashe.popularmovies.database.AppDatabase;
import com.github.johntinashe.popularmovies.database.MovieEntry;
import com.github.johntinashe.popularmovies.model.Movie;

public class AddToFavoritesService extends IntentService {

    private Movie movie;

    public AddToFavoritesService() {
        super("AddToFavoritesService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        assert intent != null;
        if (intent.getParcelableExtra("movie") != null) {
           movie = intent.getParcelableExtra("movie");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        AppDatabase database = AppDatabase.getInstance(this);
        MovieEntry movieEntry = null;
        if (movie != null) {
            movieEntry = new MovieEntry();
            movieEntry.setDate(movie.getReleaseDate());
            movieEntry.setId(movie.getId());
            movieEntry.setLanguage(movie.getOriginalLanguage());
            movieEntry.setPlot(movie.getOverview());
            movieEntry.setTitle(movie.getTitle());
            movieEntry.setBackdrop(movie.getBackdropPath());
            movieEntry.setPoster(movie.getPosterPath());
            movieEntry.setRating(movie.getVoteAverage());

        }
        if (action != null) {
            if (action.equals("add_to_fav") && movieEntry != null) {

                database.movieDao().insertTask(movieEntry);

            } else if (action.equals("remove_from_fav") && movieEntry != null) {

                database.movieDao().deleteTask(movieEntry);
            }
        }

    }
}