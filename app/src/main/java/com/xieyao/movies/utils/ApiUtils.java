package com.xieyao.movies.utils;

import com.xieyao.movies.BuildConfig;

/**
 * Created by xieyao on 2019-10-11.
 */
public class ApiUtils {

    /**
     * get Poster Image Path
     *
     * @param posterAbsPath
     * @return
     */
    public static String getPoster(String posterAbsPath) {
        return BuildConfig.BASE_URL_IMG + BuildConfig.BASE_URL_IMG_POSTER_SIZE + posterAbsPath;
    }

    /**
     * get Back Drop Image Path
     *
     * @param backDropAbsPath
     * @return
     */
    public static String getBackDrop(String backDropAbsPath) {
        return BuildConfig.BASE_URL_IMG + BuildConfig.BASE_URL_IMG_BACKDROP_SIZE + backDropAbsPath;
    }

    /**
     * get Youtube url
     *
     * @param key
     * @return
     */
    public static String getYoutube(String key) {
        return "https://www.youtube.com/watch?v=" + key;
    }

}
