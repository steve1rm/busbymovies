package me.androidbox.busbymovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by steve on 3/26/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " +
                MovieContract.MovieEntry.TABLE_NAME + "( " +
                MovieContract.MovieEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.MOVIE_ID + "INTEGER NOT NULL, " +
                MovieContract.MovieEntry.POSTER_PATH + "TEXT, " +
                MovieContract.MovieEntry.RELEASE_DATE + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.TITLE + "TEXT NOT NULL, " +
                MovieContract.MovieEntry.BACKDROP_PATH + "TEXT, " +
                MovieContract.MovieEntry.VOTE_AVERAGE + "FLOAT, " +
                MovieContract.MovieEntry.TAGLINE + "TEXT, " +
                MovieContract.MovieEntry.HOMEPATH + "TEXT, " +
                MovieContract.MovieEntry.RUNTIME + "INTEGER" + ");";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            final String DROP_TABLE = "DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME;
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }
}
