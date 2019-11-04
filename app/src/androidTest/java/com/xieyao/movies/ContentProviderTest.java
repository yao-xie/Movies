package com.xieyao.movies;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.local.MovieDatabase;
import com.xieyao.movies.data.local.MovieLocalRepo;
import com.xieyao.movies.data.local.MovieLocalRepoImpl;
import com.xieyao.movies.data.provider.MovieContentProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ContentProviderTest {

    private Context context;
    private MovieLocalRepo movieLocalRepo;
    private ContentResolver contentResolver;
    private MovieContentProvider provider;
    public static final String[] MOVIE_PROJECTION = new String[]{
            "id", // 0
            "favorite" // 1
    };

    private MovieItem[] movieItems = new MovieItem[4];


    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
        MovieDatabase database = MovieDatabase.getInstance(context);
        movieLocalRepo = new MovieLocalRepoImpl(database.movieDao());
        contentResolver = context.getContentResolver();
        provider = new MovieContentProvider();
    }

    @After
    public void cleanUp() {
        movieLocalRepo.removeMovies();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(context);
        assertNotNull(provider);
    }

    @Test
    public void insert_Query() {

        movieItems[0] = new MovieItem(1, 1);
        movieItems[1] = new MovieItem(2, 1);
        movieItems[2] = new MovieItem(3, 1);
        movieItems[3] = new MovieItem(4, 1);

        for (int i = 1; i < 5; i++) {
            insertMovie(new MovieItem(i, 1));
        }
        for (int i = 5; i < 10; i++) {
            insertMovie(new MovieItem(i, 0));
        }

        Cursor cursor = contentResolver.query(MovieContentProvider.URI_MOVIE, MOVIE_PROJECTION, null, null, null);

        cursor.moveToFirst();

        Log.w("xieyao", "cursor.getCount() = " + cursor.getCount());

        Assert.assertTrue(cursor.getCount() == 4);

        if (cursor.getCount() != 0) {
            do {
                Assert.assertEquals(
                        movieItems[cursor.getPosition()].getId(),
                        cursor.getInt(cursor.getColumnIndex("id")));
            } while (cursor.moveToNext());
        }

    }

    private void insertMovie(MovieItem movieItem) {
        Uri uri = contentResolver.insert(MovieContentProvider.URI_MOVIE, getContentValue(movieItem));
        Log.w("xieyao", "insertMovie, uri = " + uri);
    }

    private ContentValues getContentValue(MovieItem movieItem) {
        ContentValues values;
        values = new ContentValues();
        values.put("id", movieItem.getId());
        values.put("favorite", movieItem.getFavorite());
        return values;
    }

}
