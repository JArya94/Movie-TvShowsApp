package com.example.movieandtvshows;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose
    private int total_count;


    @SerializedName("results")
    @Expose
    private List<MoviesData> movies;


    public int getTotal_count() {
        return total_count;
    }

    public List<MoviesData> getMovies() {
        return movies;
    }
}
