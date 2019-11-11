package com.xieyao.movies.data.remote;

import android.util.Pair;

import com.xieyao.movies.api.ApiClient;
import com.xieyao.movies.api.TmdbApi;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.MovieResult;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.ReviewResult;
import com.xieyao.movies.data.bean.TrailerItem;
import com.xieyao.movies.data.bean.TrailerResult;
import com.xieyao.movies.utils.ConfigUtils;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by xieyao on 2019-10-12.
 */
public class MovieRemoteRepoImpl implements MovieRemoteRepo {

    @Override
    public Observable<List<MovieItem>> getMovies(int listMode) throws Exception {
        TmdbApi api = ApiClient.getInstance().getService();
        Observable<MovieResult> movieResultObservable = null;
        switch (listMode) {
            case ConfigUtils.MODE_TOP_RATED_MOVIES:
                movieResultObservable = api.getTopRatedMovies(1);
                break;
            case ConfigUtils.MODE_FAVORITE_MOVIES:
                return Observable.fromCallable(new Callable<List<MovieItem>>() {
                    @Override
                    public List<MovieItem> call() throws Exception {
                        return null;
                    }
                });
            default:
                movieResultObservable = api.getPopularMovies(1);
                break;
        }
        return movieResultObservable.map(new Function<MovieResult, List<MovieItem>>() {
            @Override
            public List<MovieItem> apply(MovieResult movieResult) throws Exception {
                if (null != movieResult) {
                    return movieResult.getResults();
                }
                return null;
            }
        });
    }

    @Override
    public Observable<Pair<Integer, List<MovieItem>>> getMovies(int listMode, int page) throws Exception {
        TmdbApi api = ApiClient.getInstance().getService();
        Observable<MovieResult> movieResultObservable = null;
        switch (listMode) {
            case ConfigUtils.MODE_TOP_RATED_MOVIES:
                movieResultObservable = api.getTopRatedMovies(page);
                break;
            case ConfigUtils.MODE_FAVORITE_MOVIES:
                return Observable.fromCallable(new Callable<Pair<Integer, List<MovieItem>>>() {
                    @Override
                    public Pair<Integer, List<MovieItem>> call() throws Exception {
                        return null;
                    }
                });
            default:
                movieResultObservable = api.getPopularMovies(page);
                break;
        }
        return movieResultObservable.map(new Function<MovieResult, Pair<Integer, List<MovieItem>>>() {
            @Override
            public Pair<Integer, List<MovieItem>> apply(MovieResult movieResult) throws Exception {
                if (null != movieResult) {
                    return new Pair<Integer, List<MovieItem>>(movieResult.getPage(), movieResult.getResults());
                }
                return null;
            }
        });
    }

    @Override
    public Observable<MovieItem> getMovieDetail(int movieId) throws Exception {
        return ApiClient.getInstance().getService().getMovieDetail(movieId);
    }

    @Override
    public Observable<List<TrailerItem>> getMovieTrailers(int movieId) throws Exception {
        return ApiClient.getInstance().getService().getMovieTrailers(movieId).map(new Function<TrailerResult, List<TrailerItem>>() {
            @Override
            public List<TrailerItem> apply(TrailerResult trailerResult) throws Exception {
                if (null != trailerResult) {
                    return trailerResult.getResults();
                }
                return null;
            }
        });
    }

    @Override
    public Observable<List<ReviewItem>> getMovieReviews(int movieId) throws Exception {
        return ApiClient.getInstance().getService().getMovieReviewss(movieId).map(new Function<ReviewResult, List<ReviewItem>>() {
            @Override
            public List<ReviewItem> apply(ReviewResult reviewResult) throws Exception {
                if (null != reviewResult) {
                    return reviewResult.getResults();
                }
                return null;
            }
        });
    }

}
