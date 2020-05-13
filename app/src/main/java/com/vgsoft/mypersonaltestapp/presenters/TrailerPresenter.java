package com.vgsoft.mypersonaltestapp.presenters;

import android.util.Log;

import com.vgsoft.mypersonaltestapp.entiti.MovieTrailerResult;
import com.vgsoft.mypersonaltestapp.network.GetMovieTrailerService;
import com.vgsoft.mypersonaltestapp.network.RetrofitInstance;
import com.vgsoft.mypersonaltestapp.utility.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerPresenter {

    private TrailerInformationView view;
    private String API_KEY = "d80c46d1b7be62014544cd47c0c76da2";

    public void attachView(TrailerInformationView view) {
        this.view = view;
    }

    public void getTrailer(int movieId) {
        GetMovieTrailerService movieTrailerService = RetrofitInstance.getRetrofitInstance().create(GetMovieTrailerService.class);
        Call<MovieTrailerResult> call = movieTrailerService.getTrailers(movieId, API_KEY);


        call.enqueue(new Callback<MovieTrailerResult>() {
            @Override
            public void onResponse(Call<MovieTrailerResult> call, Response<MovieTrailerResult> response) {
                MovieTrailerResult movieTrailerResult = response.body();
                if (response.body() != null
                        && response.body().getTrailerResult().size() > 0) {
                    view.onMovieTrailerResultReceived(movieTrailerResult);
                    Log.i("MovieActivity", "https://youtube.com/watch?v=" + response.body().getTrailerResult().get(0).getKey());
                }

            }

            @Override
            public void onFailure(Call<MovieTrailerResult> call, Throwable t) {
                Utils.makeToast(t.getMessage());
            }
        });
    }


}
