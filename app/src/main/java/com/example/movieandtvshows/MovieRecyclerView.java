package com.example.movieandtvshows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //TextView title, release_date, duration;
    //ImageView imageView;
    //RatingBar ratingBar;
    private OnMovieListener onMovieListener;
    private List<MoviesData> mMovies;

    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;


    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == DISPLAY_SEARCH) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
            return new MovieViewHolder(view, onMovieListener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout
                    , parent, false);
            return new Popular_View_Holder(view, onMovieListener);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == DISPLAY_SEARCH) {

            // ((MovieViewHolder) holder).title.setText(mMovies.get(position).getTitle());
            // ((MovieViewHolder) holder).release_date.setText(mMovies.get(position).getRelease_Date());
            //  ((MovieViewHolder) holder).duration.setText(String.valueOf(mMovies.get(position).getRuntime()));

            ((MovieViewHolder) holder).ratingBar.setRating((mMovies.get(position).getVote_average()) / 2);


            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500"
                            + mMovies.get(position).getPoster_path())
                    .fitCenter()
                    .into(((MovieViewHolder) holder).imageView);
        } else {
            //  ((MovieViewHolder) holder).title.setText(mMovies.get(position).getTitle());
            //   ((MovieViewHolder) holder).release_date.setText(mMovies.get(position).getRelease_Date());
            //   ((MovieViewHolder) holder).duration.setText(String.valueOf(mMovies.get(position).getRuntime()));

            ((Popular_View_Holder) holder).ratingBar22.setRating((mMovies.get(position).getVote_average()) / 2);


            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500"
                            + mMovies.get(position).getPoster_path())
                    .fitCenter()
                    .into(((Popular_View_Holder) holder).imageView22);
        }
    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MoviesData> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    public MoviesData getSelectedMovie(int position) {
        if (mMovies != null) {
            if (mMovies.size() > 0) {
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR){
            return DISPLAY_POP;
        } else
            return DISPLAY_SEARCH;
    }
}

