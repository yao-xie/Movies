package com.xieyao.movies.data.remote;

import android.util.Pair;

import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by xieyao on 2019-10-12.
 */
public interface MovieRemoteRepo {

    Observable<List<MovieItem>> getMovies(int listMode) throws Exception;

    Observable<Pair<Integer,List<MovieItem>>> getMovies(int listMode,int page) throws Exception;

    Observable<MovieItem> getMovieDetail(int movieId) throws Exception;

    Observable<List<TrailerItem>> getMovieTrailers(int movieId) throws Exception;

    Observable<List<ReviewItem>> getMovieReviews(int movieId) throws Exception;

}
