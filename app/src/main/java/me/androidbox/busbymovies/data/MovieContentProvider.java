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

import static me.androidbox.busbymovies.data.MovieContract.MovieEntry.TABLE_NAME;

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

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE, 100);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE + "/#", 101);

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
        final SQLiteDatabase db = mMoveDbHelper.getReadableDatabase();

        Cursor retCursor;
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case MOVIES: {
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            break;

            case MOVIES_WITH_ID: {
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            break;

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        String type;

        switch(match) {
            case MOVIES: {
                type = "vnd.android.cursor.dir" + "/" + MovieContract.AUTHORITY + "/" + MovieContract.PATH_MOVIE;
            }
            break;

            case MOVIES_WITH_ID: {
                type = "vnd.android.cursor.item" + "/" + MovieContract.AUTHORITY + "/" + MovieContract.PATH_MOVIE;
            }
            break;

            default: {
                throw new UnsupportedOperationException("Unknown uri " + uri);
            }
        }

        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mMoveDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        Uri insertUri;

        switch(match) {
            case MOVIES: {
                final long id = db.insert(TABLE_NAME, null, values);
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
        final SQLiteDatabase db = mMoveDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        int rowDeleted;

        switch(match) {
            case MOVIES_WITH_ID: {
                rowDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
            }
            break;

            default: {
                throw new UnsupportedOperationException("Unknown uri " + uri);
            }
        }

        /* Update the content provider with the after the delete */
        if(rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mMoveDbHelper.getWritableDatabase();

        int taskUpdated;
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case MOVIES_WITH_ID: {
                final String id = uri.getPathSegments().get(1);
                taskUpdated = db.update(TABLE_NAME, values, "_id=?", new String[]{id});
            }
            break;

            default: {
                throw new UnsupportedOperationException("Unknown uri " + uri);
            }
        }

        if(taskUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return taskUpdated;
    }
}
