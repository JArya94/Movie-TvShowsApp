package com.example.movieandtvshows;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );


    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );


    @GET("/3/movie/{movie_id}")
    Call<MoviesData> getMovie(
            @Query("api_key") String api_key,
            @Query("movie_id") int movie_id
    );


}
