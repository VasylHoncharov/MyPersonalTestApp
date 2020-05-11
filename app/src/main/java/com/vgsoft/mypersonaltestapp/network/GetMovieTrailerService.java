package com.vgsoft.mypersonaltestapp.network;

import com.vgsoft.mypersonaltestapp.model.MovieTrailerResult;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMovieTrailerService {
    @GET("movie/{id}/videos")
    Call<MovieTrailerResult> getTrailers(@Path("id") int movieId, @Query("api_key") String userkey);
}