package com.github.johntinashe.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<MovieEntry>> loadAllMovies();

    @Insert
    void insertTask(MovieEntry movieEntry);

    @Delete
    void deleteTask(MovieEntry movieEntry);

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<MovieEntry> loadById(int id);
}
