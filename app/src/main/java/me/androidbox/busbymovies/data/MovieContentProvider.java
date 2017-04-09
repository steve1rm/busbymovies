package me.androidbox.busbymovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by steve on 4/2/17.
 */

public class MovieContentProvider extends ContentProvider {
    private MovieDbHelper mMoveDbHelper;

    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        sUriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE, 100);
        sUriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE + "/#", 101);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mMoveDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mMoveDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        Uri insertUri;

        switch(match) {
            case MOVIES: {
                final long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if(id > 0) {
                    insertUri = ContentUris.withAppendedId(MovieContract.BASE_CONTENT_URI, id);
                }
                else {
                    throw new SQLException("Failed to insert into DB");
                }
            }
            break;

            default:
                throw new UnsupportedOperationException("Incorrect matcher");
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return insertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
