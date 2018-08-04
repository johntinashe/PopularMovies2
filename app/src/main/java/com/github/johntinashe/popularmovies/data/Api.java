package com.github.johntinashe.popularmovies.data;

import com.github.johntinashe.popularmovies.model.CastResponse;
import com.github.johntinashe.popularmovies.model.Movie;
import com.github.johntinashe.popularmovies.model.MoviesResponse;
import com.github.johntinashe.popularmovies.model.ReviewsResponse;
import com.github.johntinashe.popularmovies.model.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface Api {

    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey , @Query("page") int pageNumber);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey , @Query("page") int pageNumber);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<CastResponse> getCasts(@Path("movie_id") int movie_id , @Query("api_key") String api_key);

    @GET("movie/{movie_id}/videos")
    Call<TrailersResponse> getTrailers(@Path("movie_id") int movie_id , @Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse> getReviews(@Path("movie_id") int movie_id , @Query("api_key") String api_key);

}