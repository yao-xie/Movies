package com.xieyao.movies;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.xieyao.movies.data.MovieRepo;
import com.xieyao.movies.detail.DetailViewModel;
import com.xieyao.movies.list.ListViewModel;

/**
 * Created by xieyao on 2019-10-17.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory mInstance;

    private MovieRepo mMovieRepo;

    public static ViewModelFactory getInstance(Application application) {
        if (mInstance == null) {
            synchronized (ViewModelFactory.class) {
                if (mInstance == null) {
                    mInstance = new ViewModelFactory(Injection.provideMovieRepo(application.getApplicationContext()));
                }
            }
        }
        return mInstance;
    }

    private ViewModelFactory(MovieRepo movieRepo) {
        mMovieRepo = movieRepo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(mMovieRepo);
        } else if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel(mMovieRepo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
