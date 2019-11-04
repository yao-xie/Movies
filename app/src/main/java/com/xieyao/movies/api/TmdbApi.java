package com.xieyao.movies.api;

import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.MovieResult;
import com.xieyao.movies.data.bean.ReviewResult;
import com.xieyao.movies.data.bean.TrailerResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by xieyao on 2019-10-11.
 */
public interface TmdbApi {

    @GET("movie/popular?")
    Observable<MovieResult> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated?")
    Observable<MovieResult> getTopRatedMovies(@Query("page") int page);

    @GET("movie/{movie_id}")
    Observable<MovieItem> getMovieDetail(@Path("movie_id") int movie_id);

    @GET("movie/{movie_id}/videos")
    Observable<TrailerResult> getMovieTrailers(@Path("movie_id") int movie_id);

    @GET("movie/{movie_id}/reviews")
    Observable<ReviewResult> getMovieReviewss(@Path("movie_id") int movie_id);
}
