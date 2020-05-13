package com.vgsoft.mypersonaltestapp.utility;

import com.vgsoft.mypersonaltestapp.entiti.Movie;

@SuppressWarnings("ALL")
public interface MovieClickListener {
    void onMovieClick(Movie movie, int position);

}
