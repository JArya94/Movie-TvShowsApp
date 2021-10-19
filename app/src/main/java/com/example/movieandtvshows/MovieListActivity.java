package com.example.movieandtvshows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;

    private MovieListViewModel moviesListViewModel;
    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar appBarLayout = findViewById(R.id.toolbar);
        setSupportActionBar(appBarLayout);

        // SearchView
        SetupSearchView();

        recyclerView = findViewById(R.id.recyclerView);



        moviesListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        ObservePopularMovies();

        //Getting Data For Popular
        moviesListViewModel.searchMoviePop(1);
    }

    private void ObserveAnyChange() {
        moviesListViewModel.getMovies().observe(this, new Observer<List<MoviesData>>() {
            @Override
            public void onChanged(List<MoviesData> moviesDatas) {

                if(moviesDatas != null) {



                    for (MoviesData movies : moviesDatas) {

                        Log.v("Tag", "onChanged: " + movies.getTitle());



                    }
                    movieRecyclerAdapter.setmMovies(moviesDatas);

                }

            }
        });
    }

    private void ObservePopularMovies() {
        moviesListViewModel.getPop().observe(this, new Observer<List<MoviesData>>() {
            @Override
            public void onChanged(List<MoviesData> moviesDatas) {
                if(moviesDatas != null) {
                    for(MoviesData movies : moviesDatas) {

                        Log.v("Tag", "onChanged: "+movies.getTitle());

                        movieRecyclerAdapter.setmMovies(moviesDatas);
                    }
                }

            }
        });
    }

    private void searchMovieApi(String query, int pageNumber) {
        moviesListViewModel.searchMovieApi(query, pageNumber);
    }

    private void ConfigureRecyclerView(){
        movieRecyclerAdapter = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        // RecyclerView Pagination
        // Loading next page of api response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    moviesListViewModel.searchNextPage();
                }
            }
        });


    }

    @Override
    public void onMovieClick(int position) {
       // Toast.makeText(this, "The position " +position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                moviesListViewModel.searchMovieApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });
    }
}