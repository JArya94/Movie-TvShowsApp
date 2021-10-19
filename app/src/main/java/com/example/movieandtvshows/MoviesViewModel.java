package com.example.movieandtvshows;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MoviesViewModel extends ViewModel {

    private MovieRepository movieRepository;


    public LiveData<List<MoviesData>> getMtvShows() {
        return movieRepository.getMovies();
    }

    public MoviesViewModel() {

        movieRepository = MovieRepository.getInstance();

    }
}
