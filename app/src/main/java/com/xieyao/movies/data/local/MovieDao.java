package com.xieyao.movies.data.local;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;

import java.util.List;

/**
 * Created by xieyao on 2019-10-12.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM " + DBConstant.MOVIE_TABLE_NAME + " ORDER BY popularity DESC LIMIT 1000")
    List<MovieItem> getPopularMovies();

    @Query("SELECT * FROM " + DBConstant.MOVIE_TABLE_NAME + " ORDER BY vote_average DESC LIMIT 1000")
    List<MovieItem> getTopRatedMovies();

    @Query("SELECT * FROM " + DBConstant.MOVIE_TABLE_NAME + " WHERE favorite = 1 LIMIT 1000")
    List<MovieItem> getFavoriteMovies();

    @Query("SELECT * FROM " + DBConstant.MOVIE_TABLE_NAME + " WHERE favorite = 1 LIMIT 1000")
    LiveData<List<MovieItem>> getFavoriteMoviesLiveData();

    @Query("SELECT * FROM " + DBConstant.MOVIE_TABLE_NAME + " WHERE id = :id")
    MovieItem getMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<MovieItem> movieItems);

    @Update
    int updateMovie(MovieItem movieItem);

    @Query("DELETE FROM " + DBConstant.MOVIE_TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrailers(List<TrailerItem> trailerItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReviews(List<ReviewItem> movieItems);

    /*** for content provider ***/

    @Query("SELECT * FROM " + DBConstant.MOVIE_TABLE_NAME + " WHERE favorite = 1 LIMIT 1000")
    Cursor selectFavoriteMovies();

    @Query("SELECT * FROM " + DBConstant.MOVIE_TABLE_NAME + " WHERE favorite = 1 AND id = :id")
    Cursor selectFavoriteMovieById(long id);

    @Insert
    long insert(MovieItem movieItem);

    @Query("DELETE FROM " + DBConstant.MOVIE_TABLE_NAME + " WHERE id = :id")
    int deleteById(int id);

    @Insert
    long[] insertAll(MovieItem[] movieItems);

}
