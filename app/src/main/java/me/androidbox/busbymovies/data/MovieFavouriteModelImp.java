package me.androidbox.busbymovies.data;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import me.androidbox.busbymovies.data.MovieContract.MovieEntry;
import me.androidbox.busbymovies.models.Favourite;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import timber.log.Timber;

/**
 * Created by steve on 3/26/17.
 */

public class MovieFavouriteModelImp implements MovieFavouriteModelContract {
    private MovieDbHelper mMovieDbHelper;
    private WeakReference<Context> mContext;

    /* Constructor injector */
    @Inject
    public MovieFavouriteModelImp(Context context) {
        mContext = new WeakReference<>(context);
    }

    @Override
    public void startup() {
        if(mContext != null) {
            mMovieDbHelper = new MovieDbHelper(mContext.get());
        }
    }

    @Override
    public void closeDown() {
        mMovieDbHelper.close();
        mContext.clear();
    }

    @Override
    public void insert(Movie favourite, InsertListener insertListener) {

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.MOVIE_ID, favourite.getId());
        contentValues.put(MovieEntry.BACKDROP_PATH, favourite.getBackdrop_path());
        contentValues.put(MovieEntry.OVERVIEW, favourite.getOverview());
        contentValues.put(MovieEntry.HOMEPATH, favourite.getHomepage());
        contentValues.put(MovieEntry.POSTER_PATH, favourite.getPoster_path());
        contentValues.put(MovieEntry.RELEASE_DATE, favourite.getRelease_date());
        contentValues.put(MovieEntry.RUNTIME, favourite.getRuntime());
        contentValues.put(MovieEntry.TAGLINE, favourite.getTagline());
        contentValues.put(MovieEntry.TITLE, favourite.getTitle());
        contentValues.put(MovieEntry.VOTE_AVERAGE, favourite.getVote_average());

        if(mContext.get().getContentResolver().insert(MovieEntry.CONTENT_URI, contentValues) != null) {
            Timber.d("Success to insert movie %d into database", favourite.getId());

            insertListener.onInsertSuccess();
        }
        else {
            Timber.e("Failed to insert movie %d into database", favourite.getId());
            insertListener.onInsertFailed("Failed to insert movie into database");
        }

        contentValues.clear();
    }

    /* try with resources don't work for API under API 19 */
    private void retrieveAPI16(RetrieveListener retrieveListener) {
        Cursor cursor = null;
        try {
            cursor = mContext.get().getContentResolver().query(
                    MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    MovieEntry.MOVIE_ID);

            if (cursor != null) {
                /* Return the favourite movies */
                final List<Movie> favouriteList = new ArrayList<>();

                while (cursor.moveToNext()) {
                    final Movie favourite = populateFavourite(cursor);
                    favouriteList.add(favourite);
                }

                Results<Movie> data = new Results<>(favouriteList);
                retrieveListener.onRetrievedSuccess(data);
            }
            else {
                retrieveListener.onRetrieveFailed("Failed to retrieve from database");
            }
        }
        catch (Exception e) {
            Timber.d(e, "Failed to query database: %s", e.getMessage());
            retrieveListener.onRetrieveFailed(e.getMessage());
        }
        finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void retrieve(RetrieveListener retrieveListener) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try (Cursor cursor = mContext.get().getContentResolver().query(
                    MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    MovieEntry.MOVIE_ID)) {

                if (cursor != null) {
                    /* Return the favourite movies */
                    final List<Movie> favouriteList = new ArrayList<>();

                    while (cursor.moveToNext()) {
                        final Movie favourite = populateFavourite(cursor);
                        favouriteList.add(favourite);
                    }

                    Results<Movie> data = new Results<>(favouriteList);
                    retrieveListener.onRetrievedSuccess(data);
                }
                else {
                    retrieveListener.onRetrieveFailed("Failed to retrieve from database");
                }
            } catch (Exception e) {
                Timber.d(e, "Failed to query database: %s", e.getMessage());
                retrieveListener.onRetrieveFailed(e.getMessage());
            }
        }
        else {
            /* Target api 16 as try - resources only works with API 19 and above */
            retrieveAPI16(retrieveListener);
        }
    }

    @Override
    public void delete(int movieId, DeleteListener deleteListener) {
        final String strMovieId = String.valueOf(movieId);
        final Uri uri = MovieEntry.CONTENT_URI.buildUpon().appendPath(strMovieId).build();

        final String[] selectionArgs = new String[]{strMovieId};
        final int rowId =
                mContext.get().getContentResolver().delete(uri, MovieEntry.MOVIE_ID +"=?", selectionArgs);
        if(rowId != 0) {
            Timber.d("Deleted movie %d from the database", movieId);
            deleteListener.onDeleteSuccess(rowId);
        }
        else {
            Timber.e("Failed to delete movie %d from the database", movieId);
            deleteListener.onDeleteFailed("Failed to delete movie from the database");
        }
    }

    @Override
    public void queryMovie(int movieId, QueryMovieListener queryMovieListener) {
        final String strMovieId = String.valueOf(movieId);
        final Uri uri = MovieEntry.CONTENT_URI.buildUpon().appendPath(strMovieId).build();

        final String[] selectionArgs = new String[]{strMovieId};
        final Cursor cursor =
                mContext.get().getContentResolver().query(uri, null, MovieEntry.MOVIE_ID + "=?", selectionArgs, null);

        if(cursor == null) {
            /* Movie movie cannot be found */
            queryMovieListener.onQueryMovieFailed("Failed to query database");
        }
        else {
            if(cursor.getCount() == 1) {
                queryMovieListener.onQueryMovieSuccess(movieId, true);
            }
            else {
                queryMovieListener.onQueryMovieSuccess(movieId, false);
            }

            cursor.close();
        }
    }

    @Override
    public void getMovieFavourite(int movieId, GetMovieFavourite getMovieFavourite) {
        final String strMovieId = String.valueOf(movieId);
        final Uri uri = MovieEntry.CONTENT_URI.buildUpon().appendPath(strMovieId).build();

        final String[] selectionArgs = new String[]{strMovieId};
        final Cursor cursor =
                mContext.get().getContentResolver().query(uri, null, MovieEntry.MOVIE_ID + "=?", selectionArgs, null);

        if(cursor == null) {
            /* Movie cannot be found */
            getMovieFavourite.onGetMovieFavouriteFailure("Failed to query database");
        }
        else {
            if(cursor.getCount() == 1) {
                cursor.moveToFirst();
                final Movie favourite = populateFavourite(cursor);
                getMovieFavourite.onGetMovieFavouriteSuccess(favourite);
            }
            else {
                getMovieFavourite.onGetMovieFavouriteFailure("Movie was not found");
            }
            cursor.close();
        }
    }

    private Movie populateFavourite(Cursor cursor) {
        final Movie favourite = new Movie(
                cursor.getInt(cursor.getColumnIndex(MovieEntry.MOVIE_ID)),
                cursor.getString(cursor.getColumnIndex(MovieEntry.POSTER_PATH)),
                cursor.getString(cursor.getColumnIndex(MovieEntry.OVERVIEW)),
                cursor.getString(cursor.getColumnIndex(MovieEntry.RELEASE_DATE)),
                cursor.getString(cursor.getColumnIndex(MovieEntry.TITLE)),
                cursor.getString(cursor.getColumnIndex(MovieEntry.BACKDROP_PATH)),
                cursor.getFloat(cursor.getColumnIndex(MovieEntry.VOTE_AVERAGE)),
                cursor.getString(cursor.getColumnIndex(MovieEntry.TAGLINE)),
                cursor.getString(cursor.getColumnIndex(MovieEntry.HOMEPATH)),
                cursor.getInt(cursor.getColumnIndex(MovieEntry.RUNTIME)));

        return favourite;
    }
}
