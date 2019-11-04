package com.xieyao.movies.data.local;

import androidx.lifecycle.LiveData;

import com.xieyao.movies.R;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;
import com.xieyao.movies.utils.ConfigUtils;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by xieyao on 2019-10-12.
 */
public class MovieLocalRepoImpl implements MovieLocalRepo {

    private MovieDao movieDao;

    public MovieLocalRepoImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Observable<List<MovieItem>> getMovies() {
        return Observable.fromCallable(new Callable<List<MovieItem>>() {
            @Override
            public List<MovieItem> call() throws Exception {
                switch (ConfigUtils.getListMode()) {
                    case R.id.action_top_rated_movies:
                        return movieDao.getTopRatedMovies();
                    case R.id.action_favorite_movies:
                        return movieDao.getFavoriteMovies();
                    default:
                        return movieDao.getPopularMovies();
                }
            }
        });
    }

    @Override
    public Observable<MovieItem> getMovieById(final int movieId) {
        return Observable.fromCallable(new Callable<MovieItem>() {
            @Override
            public MovieItem call() throws Exception {
                return movieDao.getMovieById(movieId);
            }
        });
    }

    @Override
    public Observable<List<MovieItem>> getFavoriteMovies() {
        return Observable.fromCallable(new Callable<List<MovieItem>>() {
            @Override
            public List<MovieItem> call() throws Exception {
                return movieDao.getFavoriteMovies();
            }
        });
    }

    @Override
    public LiveData<List<MovieItem>> getFavoriteMoviesLiveData() {
        return movieDao.getFavoriteMoviesLiveData();
    }

    @Override
    public void saveMovies(List<MovieItem> movieItems) {
        movieDao.insertAll(movieItems);
    }

    @Override
    public Observable<MovieItem> updateMovie(final MovieItem movieItem) {
        return Observable.fromCallable(new Callable<MovieItem>() {
            @Override
            public MovieItem call() throws Exception {
                movieDao.updateMovie(movieItem);
                return movieDao.getMovieById(movieItem.getId());
            }
        });
    }

    @Override
    public void removeMovies() {
        movieDao.deleteAll();
    }

    @Override
    public void saveTrailers(List<TrailerItem> trailerItems) {
        movieDao.insertTrailers(trailerItems);
    }

    @Override
    public void saveReviews(List<ReviewItem> reviewItems) {
        movieDao.insertReviews(reviewItems);
    }
}
