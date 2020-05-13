package com.vgsoft.mypersonaltestapp.presenters;

import com.vgsoft.mypersonaltestapp.entiti.MoviePageResult;

public interface MovieInformationView {
    void onMoviePageResultReceived(MoviePageResult moviePageResult, int page);
    void onError(String error);
}