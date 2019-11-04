package com.xieyao.movies.data.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.stetho.common.LogRedirector;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.local.DBConstant;
import com.xieyao.movies.data.local.MovieDao;
import com.xieyao.movies.data.local.MovieDatabase;

import java.util.ArrayList;


/**
 * expose Favorite Movies vis this ContentProvider
 */
public class MovieContentProvider extends ContentProvider {

    private static final String TAG = "MovieContentProvider";

    public static final String AUTHORITY = "com.xieyao.movies.provider";

    public static final Uri URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/" + DBConstant.MOVIE_TABLE_NAME);

    private static final int CODE_MOVIE_DIR = 1;

    private static final int CODE_MOVIE_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, DBConstant.MOVIE_TABLE_NAME, CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, DBConstant.MOVIE_TABLE_NAME + "/*", CODE_MOVIE_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            MovieDao movieDao = MovieDatabase.getInstance(context).movieDao();
            final Cursor cursor;
            if (code == CODE_MOVIE_DIR) {
                cursor = movieDao.selectFavoriteMovies();
            } else {
                cursor = movieDao.selectFavoriteMovieById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + DBConstant.MOVIE_TABLE_NAME;
            case CODE_MOVIE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + DBConstant.MOVIE_TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = MovieDatabase.getInstance(context).movieDao()
                        .insert(MovieItem.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_MOVIE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = MovieDatabase.getInstance(context).movieDao()
                        .deleteById((int) ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final MovieItem movieItem = MovieItem.fromContentValues(values);
                movieItem.setId((int) ContentUris.parseId(uri));
                final int count = MovieDatabase.getInstance(context).movieDao()
                        .updateMovie(movieItem);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final MovieDatabase database = MovieDatabase.getInstance(context);
        final ContentProviderResult[] result = super.applyBatch(operations);
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                LogRedirector.d(TAG, "runInTransaction");
            }
        });
        return result;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final MovieDatabase database = MovieDatabase.getInstance(context);
                final MovieItem[] movieItems = new MovieItem[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    movieItems[i] = MovieItem.fromContentValues(valuesArray[i]);
                }
                return database.movieDao().insertAll(movieItems).length;
            case CODE_MOVIE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}
