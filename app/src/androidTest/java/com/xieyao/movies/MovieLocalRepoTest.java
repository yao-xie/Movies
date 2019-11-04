package com.xieyao.movies;


import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.local.MovieLocalRepo;
import com.xieyao.movies.data.local.MovieLocalRepoImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MovieLocalRepoTest {

    private MovieLocalRepo movieLocalRepo;

    @Before
    public void setup() {
        MovieDatabase database = MovieDatabase.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        movieLocalRepo = new MovieLocalRepoImpl(database.movieDao());
    }

    @After
    public void cleanUp() {
        Observable.empty().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                movieLocalRepo.removeMovies();
            }
        });
    }

    @Test
    public void testPreConditions() {
        assertNotNull(movieLocalRepo);
    }

    @Test
    public void retrieveMovie() {
        movieLocalRepo.getMovies().subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<MovieItem>>() {
                    @Override
                    public void accept(List<MovieItem> movieItems) throws Exception {
                        Log.d("xieyao", "retrieveMovie count = " + (null != movieItems ? movieItems.size() : 0));
                        assertNotNull(movieItems);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        assertNotNull(throwable);
                    }
                });
    }


    @Test
    public void retrieveFavoriteMovies() {
        movieLocalRepo.getFavoriteMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<MovieItem>>() {
                    @Override
                    public void accept(List<MovieItem> movieItems) throws Exception {
                        Log.d("xieyao", "getFavoriteMovies count = " + (null != movieItems ? movieItems.size() : 0));
                        assertNotNull(movieItems);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

}
