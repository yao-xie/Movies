package com.xieyao.movies.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;

/**
 * Created by xieyao on 2019-10-12.
 */
@Database(entities = {MovieItem.class, TrailerItem.class, ReviewItem.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static volatile MovieDatabase mInstance;

    public abstract MovieDao movieDao();

    public static MovieDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MovieDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, DBConstant.DB_NAME)
                            .build();
                }
            }
        }
        return mInstance;
    }
}
