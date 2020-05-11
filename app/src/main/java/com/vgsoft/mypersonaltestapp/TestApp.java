package com.vgsoft.mypersonaltestapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import io.realm.Realm;


public class TestApp extends Application {
    private static TestApp singleton;
    private static Context context;
    private static Resources resources;

// Возвращает экземпляр данного класса

    public static TestApp getInstance() {
        return singleton;
    }

    @Override

    public final void onCreate() {
        super.onCreate();
        singleton = this;
        Realm.init(this);
        TestApp.context = getApplicationContext();
        resources = getResources();
    }

    public static Context getAppContext() {
        return TestApp.context;
    }

    public static Resources getRes() {
        return resources;
    }
}
