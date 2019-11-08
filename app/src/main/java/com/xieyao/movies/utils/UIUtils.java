package com.xieyao.movies.utils;

import android.util.DisplayMetrics;

import com.xieyao.movies.App;

public class UIUtils {

    public static int getSpanCount() {
        DisplayMetrics displayMetrics = App.getInstance().getApplicationContext().getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;
        float screenHeight = displayMetrics.heightPixels;
        float screenAcreage = screenWidth * screenHeight;
        float xdpi = displayMetrics.xdpi;
        int count = Math.round(screenWidth / xdpi);
        return getCount(screenWidth, screenAcreage, count);
    }

    private static int getCount(float screenWidth, float screenAcreage, int count) {
        int expectMovieitemWidth = (int) (screenWidth / count);
        int expectMovieitemHeight = expectMovieitemWidth * 3 / 2;
        int expectMovieitemAcreage = expectMovieitemWidth * expectMovieitemHeight;
        if (screenAcreage / expectMovieitemAcreage > 20) {
            return getCount(screenWidth, screenAcreage, count);
        }
        return count;
    }

}
