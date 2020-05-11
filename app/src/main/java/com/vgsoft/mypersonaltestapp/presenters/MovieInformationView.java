package com.vgsoft.mypersonaltestapp.presenters;

import com.vgsoft.mypersonaltestapp.model.MoviePageResult;

public interface MovieInformationView {
    void onMoviePageResultReceived(MoviePageResult moviePageResult, int page);
    void onError(String error);
}