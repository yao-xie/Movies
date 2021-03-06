package com.xieyao.movies;

import android.app.Application;

import com.xieyao.movies.api.ApiClient;
import com.xieyao.movies.data.local.MovieDatabase;

/**
 * Created by xieyao on 2019-10-11.
 */
public class App extends Application {

    private static volatile App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        try {
            ApiClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MovieDatabase.getInstance(getApplicationContext());

        if (BuildConfig.DEBUG) {
            com.facebook.stetho.Stetho.initializeWithDefaults(this);
        }
    }

    public static App getInstance() {
        return mInstance;
    }
}
