package com.example.movieandtvshows;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MovieDetails extends AppCompatActivity {

    private ImageView movieDetailImage;
    private TextView movieTitle, movieDescription;
    private RatingBar ratingBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieDetailImage = findViewById(R.id.imageView_details);
        movieTitle = findViewById(R.id.textView_title_details);
        movieDescription = findViewById(R.id.textView_description_details);
        ratingBar = findViewById(R.id.details_ratingBar);

        GetDataFromIntent();

    }

    private void GetDataFromIntent() {
        if(getIntent().hasExtra("movie")) {
            MoviesData moviesData = getIntent().getParcelableExtra("movie");
            Log.v("Tag", "incoming intent " +moviesData.getTitle());

            movieTitle.setText(moviesData.getTitle());
            movieDescription.setText(moviesData.getMovie_overview());
            ratingBar.setRating(moviesData.getVote_average()/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500"
                    +moviesData.getPoster_path())
                    .into(movieDetailImage);


        }
    }
}
