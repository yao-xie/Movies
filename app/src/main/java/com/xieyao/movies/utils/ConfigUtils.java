package com.xieyao.movies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xieyao.movies.App;
import com.xieyao.movies.R;

/**
 * Created by xieyao on 2019-10-11.
 */
public class ConfigUtils {

    private static final String KEY_MOVIE_LIST_MODE = "key_movie_list_mode";

    public static final int MODE_POPULAR_MOVIES = 0;
    public static final int MODE_TOP_RATED_MOVIES = 1;
    public static final int MODE_FAVORITE_MOVIES = 2;

    private ConfigUtils() {
    }


    public static int getTabTitle(int position){
        switch (position){
            case MODE_TOP_RATED_MOVIES:{
                return R.string.action_top_rated_movies;
            }
            case MODE_FAVORITE_MOVIES:{
                return R.string.action_favorite_movies;
            }
            default:{
                return R.string.action_popular_movies;
            }

        }
    }

    public static void setListMode(int listMode) {
        setIntegerPreference(App.getInstance(), KEY_MOVIE_LIST_MODE, listMode);
    }

    public static int getListMode() {
        return getIntegerPreference(App.getInstance(), KEY_MOVIE_LIST_MODE, MODE_POPULAR_MOVIES);
    }

    private static int getIntegerPreference(Context context, String key, int defaultValue) {
        int value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getInt(key, defaultValue);
        }
        return value;
    }

    private static boolean setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        return false;
    }

}
