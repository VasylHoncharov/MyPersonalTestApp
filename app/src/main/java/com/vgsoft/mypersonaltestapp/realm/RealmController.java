package com.vgsoft.mypersonaltestapp.realm;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vgsoft.mypersonaltestapp.entiti.Movie;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmController {

    private static final String TAG = RealmController.class.getName();
    private Realm realm;
    private Gson gson = new Gson();

    public RealmController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void addOrUpdateMovie(Movie movie) {
        Movie findMovie = realm.where(Movie.class).equalTo("id", movie.getId()).findFirst();
        if (findMovie == null) {
            addMovie(movie);
        }

        Log.i(TAG, "onMoviePageResultReceived: " + " id - " + movie.getId() + " name - " + movie.getTitle());

    }

    private void addMovie(Movie movie) {
        realm.beginTransaction();

        Movie realmObject = realm.createObject(Movie.class, movie.getId());
        int id = getNextKey();
        Log.d("Realm", "id " + id);
        realmObject.setAdult(movie.isAdult());
        realmObject.setBackdropPath(movie.getBackdropPath());
        realmObject.setGenreId((RealmList<Integer>) movie.getGenreId());
        realmObject.setOriginalLanguage(movie.getOriginalLanguage());
        realmObject.setOriginalTitle(movie.getOriginalTitle());
        realmObject.setOverview(movie.getOverview());
        realmObject.setPopularity(movie.getPopularity());
        realmObject.setPosterPath(movie.getPosterPath());
        realmObject.setReleaseDate(movie.getReleaseDate());
        realmObject.setTitle(movie.getTitle());
        realmObject.setVideo(movie.isVideo());
        realmObject.setVoteAverage(movie.getVoteAverage());
        realmObject.setVoteCount(movie.getVoteCount());

        realm.commitTransaction();
    }

    public void saveSettings(int totalPages, int currentPage, int position) {
        realm.beginTransaction();
        SettingsModel realmObject = realm.where(SettingsModel.class).equalTo("id", 777).findFirst();
        if (realmObject == null) {
            SettingsModel newRealmObject = realm.createObject(SettingsModel.class, 777);
            newRealmObject.setTotalPages(totalPages);
            newRealmObject.setPage(currentPage);
            newRealmObject.setPosition(position);

        } else {
            realmObject.setTotalPages(totalPages);
            realmObject.setPage(currentPage);
            realmObject.setPosition(position);
        }
        realm.commitTransaction();

    }


    public SettingsModel getSettings() {
        return realm.where(SettingsModel.class).equalTo("id", 777).findFirst();
    }

    public ArrayList<Movie> getMoves(ArrayList<Movie> movies) {
        RealmResults<Movie> dbMovies = realm.where(Movie.class).findAll();
        for (Movie movie : dbMovies) {
            Movie nMovie = new Movie();

            nMovie.setAdult(movie.isAdult());
            nMovie.setBackdropPath(movie.getBackdropPath());
            nMovie.setGenreId((RealmList<Integer>) movie.getGenreId());
            nMovie.setOriginalLanguage(movie.getOriginalLanguage());
            nMovie.setOriginalTitle(movie.getOriginalTitle());
            nMovie.setOverview(movie.getOverview());
            nMovie.setPopularity(movie.getPopularity());
            nMovie.setPosterPath(movie.getPosterPath());
            nMovie.setReleaseDate(movie.getReleaseDate());
            nMovie.setTitle(movie.getTitle());
            nMovie.setVideo(movie.isVideo());
            nMovie.setVoteAverage(movie.getVoteAverage());
            nMovie.setVoteCount(movie.getVoteCount());

            movies.add(nMovie);

        }
        return movies;
    }

    public void removeAll() {
        RealmResults<Movie> results = realm.where(Movie.class).findAll();
        RealmResults<SettingsModel> resultsCursor = realm.where(SettingsModel.class).findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        resultsCursor.deleteAllFromRealm();
        realm.commitTransaction();
    }

    private int getNextKey() {
        return realm.where(Movie.class).max("id").intValue() + 1;
    }
}

