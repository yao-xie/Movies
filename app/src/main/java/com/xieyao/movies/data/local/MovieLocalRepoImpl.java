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
public class MovieLocalRepoImpl implements MovieLocalRepo {

    public MovieLocalRepoImpl() {

    }

    @Override
    public Observable<List<MovieItem>> getMovies() {
        return null;
    }

    @Override
    public Observable<MovieItem> getMovieById(final int movieId) {
        return null;
    }

    @Override
    public Observable<List<MovieItem>> getFavoriteMovies() {
        return null;
    }

    @Override
    public LiveData<List<MovieItem>> getFavoriteMoviesLiveData() {
        return null;
    }

    @Override
    public void saveMovies(List<MovieItem> movieItems) {
        // TODO
    }

    @Override
    public Observable<MovieItem> updateMovie(final MovieItem movieItem) {
        return null;
    }

    @Override
    public void removeMovies() {
        // TODO
    }

    @Override
    public void saveTrailers(List<TrailerItem> trailerItems) {
        // TODO
    }

    @Override
    public void saveReviews(List<ReviewItem> reviewItems) {
        // TODO
    }
}
