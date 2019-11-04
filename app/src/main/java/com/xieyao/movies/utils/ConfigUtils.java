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

    private ConfigUtils() {
    }

    public static void setListMode(int resId) {
        setIntegerPreference(App.getInstance(), KEY_MOVIE_LIST_MODE, resId);
    }

    public static int getListMode() {
        return getIntegerPreference(App.getInstance(), KEY_MOVIE_LIST_MODE, R.id.action_popular_movies);
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
