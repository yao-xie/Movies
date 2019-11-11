package com.xieyao.movies.data.local;

import androidx.lifecycle.LiveData;

import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by xieyao on 2019-10-12.
 */
public interface MovieLocalRepo {

    Observable<List<MovieItem>> getMovies(int listMode);

    Observable<MovieItem> getMovieById(int movieId);

    Observable<List<MovieItem>> getFavoriteMovies();

    LiveData<List<MovieItem>> getFavoriteMoviesLiveData();

    void saveMovies(List<MovieItem> movieItems);

    Observable<MovieItem> updateMovie(MovieItem movieItem);

    void removeMovies();

    void saveTrailers(List<TrailerItem> trailerItems);

    void saveReviews(List<ReviewItem> reviewItems);
}
