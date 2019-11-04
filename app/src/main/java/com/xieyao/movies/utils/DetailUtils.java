package com.xieyao.movies.utils;

/**
 * Created by xieyao on 2019-10-20.
 */
public class DetailUtils {

    public static String getBackDropTitle(String movieTitle) {
        return (null != movieTitle && movieTitle.length() < 8) ? "    " + movieTitle + "    " : movieTitle;
    }

    public static String getReleaseYear(String releaseDate) {
        return (null != releaseDate && releaseDate.length() > 4) ? releaseDate.substring(0, 4) : "";
    }

}
