package com.xieyao.movies;

import android.content.Context;

import androidx.annotation.NonNull;

import com.xieyao.movies.data.MovieRepo;
import com.xieyao.movies.data.MovieRepoImpl;
import com.xieyao.movies.data.local.MovieDatabase;
import com.xieyao.movies.data.local.MovieLocalRepo;
import com.xieyao.movies.data.local.MovieLocalRepoImpl;
import com.xieyao.movies.data.remote.MovieRemoteRepo;
import com.xieyao.movies.data.remote.MovieRemoteRepoImpl;

/**
 * Created by xieyao on 2019-10-17.
 */
public class Injection {

    public static MovieRepo provideMovieRepo(@NonNull Context context) {
        MovieLocalRepo movieLocalRepo = new MovieLocalRepoImpl(MovieDatabase.getInstance(context).movieDao());
        MovieRemoteRepo movieRemoteRepo = new MovieRemoteRepoImpl();
        return new MovieRepoImpl(movieLocalRepo, movieRemoteRepo);
    }

}
