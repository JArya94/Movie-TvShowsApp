package com.example.movieandtvshows;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Popular_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {


    OnMovieListener onMovieListener;
    RatingBar ratingBar22;
    ImageView imageView22;

    public Popular_View_Holder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);
        this.onMovieListener = onMovieListener;
        imageView22 = itemView.findViewById(R.id.movie_img_popular);
        ratingBar22 = itemView.findViewById(R.id.rating_pop);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
