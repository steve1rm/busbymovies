package me.androidbox.busbymovies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import me.androidbox.busbymovies.data.MovieContract.MovieEntry;
import me.androidbox.busbymovies.data.MovieDbHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by steve on 3/26/17.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    /* Context used to perform operations on the database and create MovieDbHelper */
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    /* Class reference to help load the constructor on runtime */
    private final Class mMovieDbHelper = MovieDbHelper.class;

    /**
     * Run before every test begins to create a clean state before testing
     */
    @Before
    public void setup() {
        deleteDatabase();
    }

    /**
     * Deletes the entire database
     */
    private void deleteDatabase() {
        try {
            /* Use reflection to get database name from the db helper class */
            final Field field = mMovieDbHelper.getDeclaredField("DATABASE_NAME");
            field.setAccessible(true);
            mContext.deleteDatabase(field.get(null).toString());
        }
        catch(NoSuchFieldException nfe) {
            fail("Make sure you have a member called DATABASE_NAME in MovieDbHelper");
        }
        catch(Exception ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * This method tests that our database contains all the requred tables
     */
    @Test
    public void shouldCreateDatabase() throws Exception {
        /* Use reflection to try to run the correct constructor whenever implemented */
        final SQLiteOpenHelper sqLiteOpenHelper =
                (SQLiteOpenHelper)mMovieDbHelper.getConstructor(Context.class).newInstance(mContext);

        /* Use MovieDBHelper to get access to a writable database */
        final SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        /* We think the database is open, let's verify that here */
        final String databaseIsNotOpen = "The database should be open and it is not";
        assertEquals(databaseIsNotOpen, true, sqLiteDatabase.isOpen());

        /* This cursor will contain the names of each table in our database */
        final Cursor tableNameCursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM sqlite_master WHERE type='table' AND 'name'" + MovieEntry.TABLE_NAME + "'", null);

        /* If tableNameCursor.moveToFirst returns false from this query, it means the database
         * wasn't created property. In actuality, it means that your database contains no tables */
        final String errorInCreatingDatabase = "Error: This means that the database has not been created correctly";
        assertTrue(errorInCreatingDatabase, tableNameCursor.moveToFirst());

        /* If this fails, it means that your database doesn't contain the expected table(s) */
        assertEquals("Error: Your database was created without the expected table(s)",
                MovieEntry.TABLE_NAME, tableNameCursor.getString(0));

        /* Always close the cursor to avoid memory leaks */
        tableNameCursor.close();
    }
}
