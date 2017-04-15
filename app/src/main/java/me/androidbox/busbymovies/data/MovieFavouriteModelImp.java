package me.androidbox.busbymovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.androidbox.busbymovies.data.MovieContract.MovieEntry;
import me.androidbox.busbymovies.models.Favourite;
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
    public void insert(Favourite favourite, InsertListener insertListener) {

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.MOVIE_ID, favourite.getId());
        contentValues.put(MovieEntry.BACKDROP_PATH, favourite.getBackdrop_path());
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

    @Override
    public void retrieve(RetrieveListener retrieveListener) {

        try (Cursor cursor = mContext.get().getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieEntry.MOVIE_ID)) {

            if (cursor != null) {
                /* Return the favourite movies */
                final List<Favourite> favouriteList = new ArrayList<>();

                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    final Favourite favourite = new Favourite(
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

                    favouriteList.add(favourite);
                }

                retrieveListener.onRetrievedSuccess(favouriteList);
            }
            else {
                retrieveListener.onRetrieveFailed("Failed to retrieve from database");
            }
        }
        catch(Exception e) {
            Timber.d(e, "Failed to query database: %s", e.getMessage());
            retrieveListener.onRetrieveFailed(e.getMessage());
        }
    }

    @Override
    public void delete(int movieId, DeleteListener deleteListener) {
        final String strMovieId = String.valueOf(movieId);

        final Uri uri = MovieEntry.CONTENT_URI.buildUpon().appendPath(strMovieId).build();

        if(mContext.get().getContentResolver().delete(uri, null, null) != 0) {
            Timber.d("Deleted movie %d from the database", movieId);
            deleteListener.onDeleteSuccess();
        }
        else {
            Timber.e("Failed to delete movie %d from the database", movieId);
            deleteListener.onDeleteFailed("Failed to delete movie from the database");
        }
    }
}
