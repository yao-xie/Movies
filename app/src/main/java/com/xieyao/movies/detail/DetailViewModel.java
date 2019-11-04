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

    private Disposable mDetailRemoteDisposable;

    public View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setFavorite(view.isSelected() ? 0 : 1);
        }
    };

    public DetailViewModel(MovieRepo movieRepo) {
        this.mMovieRepo = movieRepo;
    }

    public void start(MovieItem movie) {
        mMovie.setValue(movie);
    }

    public void fetchRemoteData() {
        getMovieDetail();
        getMovieTrailers();
        getMovieReviews();
    }

    // get movie detail data from remote
    private void getMovieDetail() {
        try {
            mDetailRemoteDisposable = mMovieRepo.getMovieDetail(mMovie.getValue().getId())
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
    private void getMovieTrailers() {}

    // get reviews list data
    private void getMovieReviews() {}

    //set favorite
    public void setFavorite(int favorite) {}


    @Override
    protected void onCleared() {
        super.onCleared();
        DisposeUtils.dispose(mDetailRemoteDisposable);
    }
}
