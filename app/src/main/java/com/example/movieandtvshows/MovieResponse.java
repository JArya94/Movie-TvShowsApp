package com.example.movieandtvshows;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {

    @SerializedName("results")
    @Expose

    private MoviesData movie;

    public MoviesData getMovie() {
        return movie;
    }
}
