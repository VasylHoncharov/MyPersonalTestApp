package com.vgsoft.mypersonaltestapp.realm;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vgsoft.mypersonaltestapp.model.Movie;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmController {

    private Realm realm;
    private Gson gson = new Gson();

    public RealmController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void addMovie(Movie movie) {
        realm.beginTransaction();

        Movie realmObject = realm.createObject(Movie.class, movie.getId());
        int id = getNextKey();
        Log.d("Realm", "id "+id);
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

    public void addCursor(CursorModel cursorModel){
        realm.beginTransaction();
        CursorModel realmObject = realm.where(CursorModel.class).equalTo("id", 777).findFirst();
        if (realmObject == null){
            CursorModel newRealmObject = realm.createObject(CursorModel.class, 777);
            newRealmObject.setPage(cursorModel.getPage());
            newRealmObject.setPosition(cursorModel.getPosition());
            newRealmObject.setTotalPages(cursorModel.getTotalPages());
        } else {
            realmObject.setPage(cursorModel.getPage());
            realmObject.setTotalPages(cursorModel.getTotalPages());
            realmObject.setPosition(cursorModel.getPosition());
        }
        realm.commitTransaction();
    }


    public CursorModel getCursor() {
        return realm.where(CursorModel.class).equalTo("id", 777).findFirst();
    }

    public RealmResults<Movie> getMoves() {
        return realm.where(Movie.class).findAll();
    }

    public String getJson() {
        String resaultAsString;
        try (Realm realm = Realm.getDefaultInstance()) {

            RealmResults<Movie> realmResult=realm.where(Movie.class).findAll();
            resaultAsString = gson.toJson(realm.copyFromRealm(realmResult));
        }
        return resaultAsString;
    }



    public void updateInfo(Movie fueling) {
        realm.beginTransaction();

        Movie realmObject = realm.where(Movie.class).equalTo("id", fueling.getId()).findFirst();
        //for future

        realm.commitTransaction();
    }

    public String removeItemById(int id, String res) {

        Log.d("Realm", "id "+id);
        if (id==getNextKey()-1){
            realm.beginTransaction();
            RealmResults<Movie> rows = realm.where(Movie.class).equalTo("id", id).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
            res = "ok";
            return res;
        }else{
            res = "Вы можете удалить только последнюю заправку";
            return res;
        }
    }

    public void removeAll(){
        RealmResults<Movie> results = realm.where(Movie.class).findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    private int getNextKey() {
        return realm.where(Movie.class).max("id").intValue() + 1;
    }
}

