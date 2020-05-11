package com.vgsoft.mypersonaltestapp.presenters;

import com.vgsoft.mypersonaltestapp.model.MoviePageResult;
import com.vgsoft.mypersonaltestapp.model.MovieTrailerResult;

public interface TrailerInformationView {
    void onMovieTrailerResultReceived(MovieTrailerResult movieTrailerResult);
    void onError(String error);
}