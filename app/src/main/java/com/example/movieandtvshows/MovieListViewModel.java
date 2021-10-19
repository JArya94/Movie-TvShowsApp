package com.example.movieandtvshows;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MoviesData>> getMovies(){
        return movieRepository.getMovies();
    }
    public LiveData<List<MoviesData>> getPop(){
        return  movieRepository.getPop();
    }


    public void searchMovieApi(String query, int pageNumber) {

        movieRepository.searchMovieApi(query, pageNumber);
    }

    public void searchMoviePop(int pageNumber) {

        movieRepository.searchMoviePop(pageNumber);
    }

    public void searchNextPage() {
        movieRepository.searchNextPage();
    }
}
