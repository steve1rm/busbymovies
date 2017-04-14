package me.androidbox.busbymovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private SQLiteDatabase mDb;
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
        if(mDb.isOpen()) {
            mDb.close();
        }

        mMovieDbHelper.close();
        mContext.clear();
    }

    @Override
    public void insert(Favourite favourite, InsertListener insertListener) {

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.MOVIE_ID, favourite.getMovieId());
        contentValues.put(MovieEntry.BACKDROP_PATH, favourite.getBackdropPath());
        contentValues.put(MovieEntry.HOMEPATH, favourite.getHomepage());
        contentValues.put(MovieEntry.POSTER_PATH, favourite.getPosterPath());
        contentValues.put(MovieEntry.RELEASE_DATE, favourite.getReleaseData());
        contentValues.put(MovieEntry.RUNTIME, favourite.getRuntime());
        contentValues.put(MovieEntry.TAGLINE, favourite.getTagline());
        contentValues.put(MovieEntry.TITLE, favourite.getTitle());
        contentValues.put(MovieEntry.VOTE_AVERAGE, favourite.getVoteAverage());

        if(mContext.get().getContentResolver().insert(MovieEntry.CONTENT_URI, contentValues) != null) {
            Timber.d("Success to insert movie %d into database", favourite.getMovieId());
            insertListener.onInsertSuccess();
        }
        else {
            Timber.e("Failed to insert movie %d into database", favourite.getMovieId());
            insertListener.onInsertFailed();
        }

        contentValues.clear();


/*
        mDb = mMovieDbHelper.getWritableDatabase();

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.MOVIE_ID, favourite.getMovieId());
        contentValues.put(MovieEntry.BACKDROP_PATH, favourite.getBackdropPath());
        contentValues.put(MovieEntry.HOMEPATH, favourite.getHomepage());
        contentValues.put(MovieEntry.POSTER_PATH, favourite.getPosterPath());
        contentValues.put(MovieEntry.RELEASE_DATE, favourite.getReleaseData());
        contentValues.put(MovieEntry.RUNTIME, favourite.getRuntime());
        contentValues.put(MovieEntry.TAGLINE, favourite.getTagline());
        contentValues.put(MovieEntry.TITLE, favourite.getTitle());
        contentValues.put(MovieEntry.VOTE_AVERAGE, favourite.getVoteAverage());

        if(mDb.insert(MovieEntry.TABLE_NAME, null, contentValues) > 0) {
            Timber.d("Success to insert movie %d into database", favourite.getMovieId());
            insertListener.onInsertSuccess();
        }
        else {
            Timber.e("Failed to insert movie %d into database", favourite.getMovieId());
            insertListener.onInsertFailed();
        }

        mDb.close();
*/
    }

    @Override
    public void retrieve(RetrieveListener retrieveListener) {
        final String sqlSelectAll = "SELECT * FROM " + MovieEntry.TABLE_NAME;

        mDb = mMovieDbHelper.getReadableDatabase();
        final Cursor cursor = mDb.rawQuery(sqlSelectAll, null);

        if(cursor == null) {
            Timber.e("Failed to retrieve movies from db");
            retrieveListener.onRetrieveFailed();
        }
        else {
            final List<Favourite> favouriteList = new ArrayList<>();

            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                final Favourite favourite = new Favourite(
                        cursor.getInt(cursor.getColumnIndex(MovieEntry.MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(MovieEntry.POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndex(MovieEntry.RELEASE_DATE)),
                        cursor.getString(cursor.getColumnIndex(MovieEntry.TITLE)),
                        cursor.getString(cursor.getColumnIndex(MovieEntry.BACKDROP_PATH)),
                        cursor.getFloat(cursor.getColumnIndex(MovieEntry.VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndex(MovieEntry.TAGLINE)),
                        cursor.getString(cursor.getColumnIndex(MovieEntry.HOMEPATH)),
                        cursor.getInt(cursor.getColumnIndex(MovieEntry.RUNTIME)));

                favouriteList.add(favourite);
            }

            cursor.close();
            Timber.d("Movies retrieved from the db %d", favouriteList.size());
            retrieveListener.onRetrievedSuccess(favouriteList);
        }

        mDb.close();
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
            deleteListener.onDeleteFailed();
        }

/*
        mDb = mMovieDbHelper.getWritableDatabase();
        if(mDb.delete(MovieEntry.TABLE_NAME, "WHERE movieId = " + movieId, null) == 1) {
            Timber.d("Deleted movie %d from the database", movieId);
            deleteListener.onDeleteSuccess();
        }
        else {
            Timber.e("Failed to delete movie %d from the database", movieId);
            deleteListener.onDeleteFailed();
        }
        mDb.close();
*/

    }
}
