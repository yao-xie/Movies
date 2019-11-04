package com.xieyao.movies.data;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.xieyao.movies.R;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;
import com.xieyao.movies.data.local.MovieLocalRepo;
import com.xieyao.movies.data.remote.MovieRemoteRepo;
import com.xieyao.movies.utils.CollectionUtils;
import com.xieyao.movies.utils.ConfigUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xieyao on 2019-10-12.
 */
public class MovieRepoImpl implements MovieRepo {

    private MovieLocalRepo localRepo;
    private MovieRemoteRepo remoteRepo;

    public MovieRepoImpl(MovieLocalRepo localRepo, MovieRemoteRepo remoteRepo) {
        this.localRepo = localRepo;
        this.remoteRepo = remoteRepo;
    }

    @Override
    public Observable<List<MovieItem>> refreshMovies() throws Exception {
        if (ConfigUtils.getListMode() == R.id.action_favorite_movies) {
            //get favorite movies from local database
            return localRepo.getFavoriteMovies();
        } else {
            //get popular or top rated movies from remote or local database
            Observable<List<MovieItem>> remoteData = remoteRepo.getMovies().doOnNext(new Consumer<List<MovieItem>>() {
                @Override
                public void accept(List<MovieItem> movieItems) throws Exception {
                    localRepo.saveMovies(movieItems);
                }

            });
            Observable<List<MovieItem>> localData = localRepo.getMovies();
            return Observable.zip(remoteData, localData, new BiFunction<List<MovieItem>, List<MovieItem>, List<MovieItem>>() {

                @Override
                public List<MovieItem> apply(List<MovieItem> remote, List<MovieItem> local) throws Exception {
                    return CollectionUtils.isEmpty(remote) ? local : remote;
                }
            }).subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Observable<Pair<Integer, List<MovieItem>>> loadMoreMovies(int page) throws Exception {
        return remoteRepo.getMovies(page).doOnNext(new Consumer<Pair<Integer, List<MovieItem>>>() {
            @Override
            public void accept(Pair<Integer, List<MovieItem>> pair) throws Exception {
                localRepo.saveMovies(pair.second);
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public LiveData<List<MovieItem>> getFavoriteMovies() {
        return localRepo.getFavoriteMoviesLiveData();
    }

    @Override
    public Observable<MovieItem> updateMovieFavoriteStatus(int movieId, final int favorite) {
        return localRepo.getMovieById(movieId).doOnNext(new Consumer<MovieItem>() {
            @Override
            public void accept(MovieItem movieItem) throws Exception {
                movieItem.setFavorite(favorite);
            }
        }).flatMap(new Function<MovieItem, ObservableSource<MovieItem>>() {
            @Override
            public ObservableSource<MovieItem> apply(MovieItem movieItem) throws Exception {
                return localRepo.updateMovie(movieItem);
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MovieItem> getMovieFromLocal(int movieId) {
        return localRepo.getMovieById(movieId);
    }

    @Override
    public Observable<MovieItem> getMovieDetail(int movieId) throws Exception {
        return Observable.zip(remoteRepo.getMovieDetail(movieId), localRepo.getMovieById(movieId),
                new BiFunction<MovieItem, MovieItem, MovieItem>() {
                    @Override
                    public MovieItem apply(MovieItem remoteMovieItem, MovieItem localMovieItem) throws Exception {
                        //keep the favorite value from database
                        if (null != localMovieItem) {
                            remoteMovieItem.setFavorite(localMovieItem.getFavorite());
                        }
                        return remoteMovieItem;
                    }
                }).flatMap(new Function<MovieItem, ObservableSource<MovieItem>>() {
            @Override
            public ObservableSource<MovieItem> apply(MovieItem movieItem) throws Exception {
                return localRepo.updateMovie(movieItem);
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<TrailerItem>> getMovieTrailers(final int movieId) throws Exception {
        return remoteRepo.getMovieTrailers(movieId).doOnNext(new Consumer<List<TrailerItem>>() {
            @Override
            public void accept(List<TrailerItem> trailerItems) throws Exception {
                for (TrailerItem item : trailerItems) {
                    item.movieId = movieId;
                }
                localRepo.saveTrailers(trailerItems);
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<ReviewItem>> getMovieReviews(final int movieId) throws Exception {
        return remoteRepo.getMovieReviews(movieId).doOnNext(new Consumer<List<ReviewItem>>() {
            @Override
            public void accept(List<ReviewItem> reviewItems) throws Exception {
                for (ReviewItem item : reviewItems) {
                    item.movieId = movieId;
                }
                localRepo.saveReviews(reviewItems);
            }
        }).subscribeOn(Schedulers.io());
    }

}
