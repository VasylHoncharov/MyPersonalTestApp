package com.vgsoft.mypersonaltestapp.presenters;

import com.vgsoft.mypersonaltestapp.model.MoviePageResult;
import com.vgsoft.mypersonaltestapp.network.GetMovieDataService;
import com.vgsoft.mypersonaltestapp.network.RetrofitInstance;
import com.vgsoft.mypersonaltestapp.utility.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter {

    private MovieInformationView view;
    static final String API_KEY = "d80c46d1b7be62014544cd47c0c76da2";

    public void attachView(MovieInformationView view) {
        this.view = view;
    }

    public void loadPage(final int page) {
        GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
        Call<MoviePageResult> call;
        call = movieDataService.getPopularMovies(page, API_KEY);

        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                MoviePageResult pageResult = response.body();
                view.onMoviePageResultReceived(pageResult, page);
            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
                Utils.makeToast(t.getMessage());
            }
        });
    }





}
