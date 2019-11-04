package com.xieyao.movies.detail;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xieyao.movies.R;
import com.xieyao.movies.data.MovieRepo;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;
import com.xieyao.movies.utils.DisposeUtils;
import com.xieyao.movies.utils.ToastUtils;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xieyao on 2019-10-13.
 */
public class DetailViewModel extends ViewModel {

    private Scheduler ioScheduler = Schedulers.io();
    private Scheduler mainThreadScheduler = AndroidSchedulers.mainThread();

    //for movie detail
    public MutableLiveData<MovieItem> mMovie = new MutableLiveData<>();
    //for movie trailers list
    public MutableLiveData<List<TrailerItem>> trailers = new MutableLiveData<>();
    //for movie reviews list
    public MutableLiveData<List<ReviewItem>> reviews = new MutableLiveData<>();

    private MovieRepo mMovieRepo;
    private int mMovieId;

    private Disposable mDetailLocalDisposable, mDetailRemoteDisposable, mDetailUpdateDisposable, mTrailersDisposable, mReviewsDisposable;

    public View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setFavorite(view.isSelected() ? 0 : 1);
        }
    };

    public DetailViewModel(MovieRepo movieRepo) {
        this.mMovieRepo = movieRepo;
    }

    public void start(int movieId) {
        this.mMovieId = movieId;
        fetchLocalData();
    }

    // get movie detail data from local db
    public void fetchLocalData() {
        mDetailLocalDisposable = mMovieRepo.getMovieFromLocal(mMovieId)
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribeWith(new DisposableObserver<MovieItem>() {

                    @Override
                    public void onNext(MovieItem movieItem) {
                        mMovie.setValue(movieItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.toast(R.string.error_get_favorite_failed);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void fetchRemoteData() {
        getMovieDetail();
        getMovieTrailers();
        getMovieReviews();
    }

    // get movie detail data from remote
    private void getMovieDetail() {
        try {
            mDetailRemoteDisposable = mMovieRepo.getMovieDetail(mMovieId)
                    .subscribeOn(ioScheduler)
                    .observeOn(mainThreadScheduler)
                    .subscribeWith(new DisposableObserver<MovieItem>() {

                        @Override
                        public void onNext(MovieItem movieItem) {
                            mMovie.setValue(movieItem);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.toast(R.string.error_get_movie_detail_failed);
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.toast(R.string.error_get_movie_detail_failed);
        }
    }

    // get trailers list data
    private void getMovieTrailers() {
        try {
            mTrailersDisposable = mMovieRepo.getMovieTrailers(mMovieId)
                    .subscribeOn(ioScheduler)
                    .observeOn(mainThreadScheduler)
                    .subscribeWith(new DisposableObserver<List<TrailerItem>>() {
                        @Override
                        public void onNext(List<TrailerItem> trailerItems) {
                            trailers.setValue(trailerItems);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.toast(R.string.error_get_trailers_failed);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.toast(R.string.error_get_trailers_failed);
        }
    }

    // get reviews list data
    private void getMovieReviews() {
        try {
            mReviewsDisposable = mMovieRepo.getMovieReviews(mMovieId)
                    .subscribeOn(ioScheduler)
                    .observeOn(mainThreadScheduler)
                    .subscribeWith(new DisposableObserver<List<ReviewItem>>() {
                        @Override
                        public void onNext(List<ReviewItem> reviewItems) {
                            reviews.setValue(reviewItems);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.toast(R.string.error_get_reviews_failed);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.toast(R.string.error_get_reviews_failed);
        }
    }

    public void setFavorite(int favorite) {
        if (null != mMovieRepo) {
            mDetailUpdateDisposable = mMovieRepo.updateMovieFavoriteStatus(mMovieId, favorite)
                    .subscribeOn(ioScheduler)
                    .observeOn(mainThreadScheduler)
                    .subscribeWith(new DisposableObserver<MovieItem>() {

                        @Override
                        public void onNext(MovieItem movieItem) {
                            mMovie.setValue(movieItem);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.toast(R.string.error_update_movie_detail_failed);
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        DisposeUtils.dispose(mDetailLocalDisposable, mDetailRemoteDisposable, mDetailUpdateDisposable, mReviewsDisposable, mTrailersDisposable);
    }
}
