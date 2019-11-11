package com.xieyao.movies.data;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by xieyao on 2019-10-12.
 */
public interface MovieRepo {

    Observable<List<MovieItem>> refreshMovies(int listMode) throws Exception;

    Observable<Pair<Integer,List<MovieItem>>> loadMoreMovies(int listMode,int page) throws Exception;

    LiveData<List<MovieItem>> getFavoriteMovies();

    Observable<MovieItem> updateMovieFavoriteStatus(int movieId, final int favorite);

    Observable<MovieItem> getMovieFromLocal(int movieId);

    Observable<MovieItem> getMovieDetail(int movieId) throws Exception;

    Observable<List<TrailerItem>> getMovieTrailers(int movieId) throws Exception;

    Observable<List<ReviewItem>> getMovieReviews(int movieId) throws Exception;

}
