package com.xieyao.movies.list;

import android.util.Pair;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xieyao.movies.R;
import com.xieyao.movies.data.MovieRepo;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.utils.CollectionUtils;
import com.xieyao.movies.utils.ConfigUtils;
import com.xieyao.movies.utils.DisposeUtils;
import com.xieyao.movies.utils.ToastUtils;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel implements SwipeRefreshLayout.OnRefreshListener {

    private Scheduler ioScheduler = Schedulers.io();
    private Scheduler mainThreadScheduler = AndroidSchedulers.mainThread();

    private MovieRepo mMovieRepo;
    private Disposable mRefreshDisposable, mLoadMoreDisposable;

    public MutableLiveData<Boolean> mRefreshing = new MutableLiveData<>();
    //save the listposition, the the fragment be visible from the backstack, scrool to the original position
    public MutableLiveData<Integer> mTabSelectPosition = new MutableLiveData<>();
    public MutableLiveData<Integer> mListPosition = new MutableLiveData<>();
    public MutableLiveData<List<MovieItem>> mListData = new MutableLiveData<>();


    private int mPage = 1;

    public ListViewModel(MovieRepo movieRepo) {
        this.mMovieRepo = movieRepo;
        this.mRefreshing.postValue(false);
        this.mTabSelectPosition.postValue(ConfigUtils.getListMode());
    }

    public void setTabPosition(int tabPosition) {
        this.mTabSelectPosition.postValue(tabPosition);
    }

    /**
     * use LiveData to update favorite list
     */
    public void retrieveFavoriteMovies(LifecycleOwner owner) {
        if (null == mMovieRepo) {
            return;
        }
        try {
            mMovieRepo.getFavoriteMovies().observe(owner, new Observer<List<MovieItem>>() {
                @Override
                public void onChanged(List<MovieItem> movieItems) {
                    if (ConfigUtils.getListMode() == ConfigUtils.MODE_FAVORITE_MOVIES) {
                        mListData.setValue(movieItems);
                    }
                }
            });
        } catch (Exception e) {
        }
    }


    public void refreshMovies() {
        this.mRefreshing.setValue(true);
        mPage = 1;
        try {
            mRefreshDisposable = mMovieRepo.refreshMovies()
                    .subscribeOn(ioScheduler)
                    .observeOn(mainThreadScheduler)
                    .subscribeWith(new DisposableObserver<List<MovieItem>>() {
                        @Override
                        public void onNext(List<MovieItem> movieItems) {
                            mPage = 1;
                            mListData.setValue(movieItems);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.toast(R.string.error_refresh_failed);
                        }

                        @Override
                        public void onComplete() {
                            mRefreshing.setValue(false);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.toast(R.string.error_refresh_failed);
        }
    }

    public void loadMoreMovies() {
        try {
            mLoadMoreDisposable = mMovieRepo.loadMoreMovies(++mPage)
                    .subscribeOn(ioScheduler)
                    .observeOn(mainThreadScheduler)
                    .subscribeWith(new DisposableObserver<Pair<Integer, List<MovieItem>>>() {
                        @Override
                        public void onNext(Pair<Integer, List<MovieItem>> pair) {
                            mPage = pair.first;
                            List<MovieItem> data = mListData.getValue();
                            if (!CollectionUtils.isEmpty(data)) {
                                data.addAll(pair.second);
                            }
                            mListData.setValue(data);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        DisposeUtils.dispose(mRefreshDisposable, mLoadMoreDisposable);
    }

    @Override
    public void onRefresh() {
        refreshMovies();
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(mListData.getValue());
    }

    public void saveListPosition(int position) {
        if (null != mListPosition)
            mListPosition.postValue(position);
    }
}
