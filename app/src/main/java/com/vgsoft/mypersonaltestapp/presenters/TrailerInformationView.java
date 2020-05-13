package com.vgsoft.mypersonaltestapp.presenters;

import com.vgsoft.mypersonaltestapp.entiti.MovieTrailerResult;

public interface TrailerInformationView {
    void onMovieTrailerResultReceived(MovieTrailerResult movieTrailerResult);
    void onError(String error);
}