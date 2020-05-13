package com.vgsoft.mypersonaltestapp.ui.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vgsoft.mypersonaltestapp.R;
import com.vgsoft.mypersonaltestapp.entiti.Movie;
import com.vgsoft.mypersonaltestapp.utility.MovieClickListener;
import com.vgsoft.mypersonaltestapp.utility.Utils;

import java.util.ArrayList;


@SuppressWarnings("ALL")
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final MovieClickListener movieClickListener;
    private final ArrayList<Movie> movieList;

    public MovieAdapter(ArrayList<Movie> movieList, MovieClickListener movieClickListener) {
        this.movieList = movieList;
        this.movieClickListener = movieClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = this.movieList.get(position);
        holder.bind(movie, movieClickListener, position);

    }


    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    @Override
    public void onViewRecycled(MovieViewHolder holder) {
        super.onViewRecycled(holder);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView mMoviePoster;
        LinearLayout mMoviePlace;
        TextView mMovieName;
        TextView mMovieDate;
        TextView mMovieRate;

        public MovieViewHolder(final View itemView) {
            super(itemView);
            mMoviePoster = itemView.findViewById(R.id.iv_movie_poster);
            mMoviePlace = itemView.findViewById(R.id.ll_movie_poster);
            mMovieName = itemView.findViewById(R.id.tv_movie_title);
            mMovieDate = itemView.findViewById(R.id.tv_movie_release_date);
            mMovieRate = itemView.findViewById(R.id.tv_movie_rating);


        }

        public void bind(final Movie movie, final MovieClickListener movieClickListener, final int position) {
            mMoviePlace.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth() / 2, getMeasuredPosterHeight(getScreenWidth() / 2)));
            Picasso.with(mMoviePoster.getContext()).load(Utils.getPosterUri(movie.getPosterPath())).placeholder(R.drawable.placeholder).fit().centerCrop().into(mMoviePoster);
            mMovieName.setText(movie.getTitle());
            mMovieDate.setText(movie.getReleaseDate());
            mMovieRate.setText(Utils.getRatingString(movie.getVoteAverage()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieClickListener.onMovieClick(movie, position);
                }
            });
        }

        private int getScreenWidth() {
            return Resources.getSystem().getDisplayMetrics().widthPixels;
        }

        private int getMeasuredPosterHeight(int width) {
            return (int) (width * 1.5f);
        }

    }


}
