package com.example.movieandtvshows;



import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class MovieApiClient {

    // LiveData for search
    private MutableLiveData<List<MoviesData>> mMovies;

    private static MovieApiClient instance;

    private RetriveMoviesRunnable retriveMoviesRunnable;

    //LiveData for popular movies
    private MutableLiveData<List<MoviesData>> mMoviesPop;

   private RetriveMoviesRunnablePop retriveMoviesRunnablePop;

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }

        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
    }

    public LiveData<List<MoviesData>> getMovies() {
        return mMovies;
    }

    public LiveData<List<MoviesData>> getmMoviesPop() {
        return mMoviesPop;
    }

    //retriveMoviesRunnablePop = new RetriveMoviesRunnablePop(pageNumber);

    public void searchMoviesApi(String query, int pageNumber) {

        if (retriveMoviesRunnable != null) {
            retriveMoviesRunnable = null;
        }

        retriveMoviesRunnable = new RetriveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);


    }
    public void searchMoviesPop(int pageNumber) {

        if (retriveMoviesRunnablePop != null) {
            retriveMoviesRunnablePop = null;
        }

        retriveMoviesRunnablePop = new RetriveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnablePop);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);


    }



    private class RetriveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetriveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try{
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;

                }
                if (response.code() == 200) {
                    List<MoviesData> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        mMovies.postValue(list);
                    } else {
                        List<MoviesData> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag","Error" +error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Servicey.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );

        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;


        }
    }


    private class RetriveMoviesRunnablePop implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetriveMoviesRunnablePop(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try{
                Response response2 = getPop(pageNumber).execute();
                if (cancelRequest) {
                    return;

                }
                if (response2.code() == 200) {
                    List<MoviesData> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if(pageNumber == 1){
                        mMoviesPop.postValue(list);
                    } else {
                        List<MoviesData> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }
                } else {
                    String error = response2.errorBody().string();
                    Log.v("Tag","Error" +error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

            private Call<MovieSearchResponse> getPop(int pageNumber) {
                return Servicey.getMovieApi().getPopular(
                        Credentials.API_KEY,
                        pageNumber
                );

        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;


        }
    }
}