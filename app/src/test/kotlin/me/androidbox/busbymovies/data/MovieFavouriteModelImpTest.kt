package me.androidbox.busbymovies.data

import android.content.ContentResolver
import android.content.ContentValues
import android.content.res.Resources
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.nhaarman.mockito_kotlin.whenever
import me.androidbox.busbymovies.R
import me.androidbox.busbymovies.data.MovieContract.MovieEntry.*
import me.androidbox.busbymovies.data.MovieFavouriteModelContract.*
import me.androidbox.busbymovies.models.Movie
import me.androidbox.busbymovies.models.Results
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import support.BaseRobolectricTestRunner

class MovieFavouriteModelImpTest: BaseRobolectricTestRunner() {

    private lateinit var movieFavouriteModelImp: MovieFavouriteModelImp
    private val contentResolver = mock(ContentResolver::class.java)
    private val insertListener = mock(InsertListener::class.java)
    private val retrieveListener = mock(RetrieveListener::class.java)
    private val deleteListener = mock(DeleteListener::class.java)
    private val queryMovieListener = mock(QueryMovieListener::class.java)
    private val getMovieFavouriteListener = mock(GetMovieFavouriteListener::class.java)
    private val resources = mock(Resources::class.java)

    @Before
    fun setup() {
        movieFavouriteModelImp = MovieFavouriteModelImp(contentResolver, resources)
    }

    @Test
    fun testMovieFavouriteModelImpIsNotNullValue() {
        assertNotNull(movieFavouriteModelImp)
    }

    @Test
    fun testInsertMovieFavouriteInserted() {
        val favouriteMovie = createFavouriteMovie()
        val contentValues = createFavouriteMovieContentValues(favouriteMovie)

        whenever(contentResolver.insert(CONTENT_URI, contentValues)).thenReturn(Uri.EMPTY)

        movieFavouriteModelImp.insert(favouriteMovie, insertListener)

        verify(contentResolver).insert(CONTENT_URI, contentValues)
        verifyNoMoreInteractions(contentResolver)
        verify(insertListener).onInsertSuccess()
        verifyNoMoreInteractions(insertListener)
    }

    @Test
    fun testInsertMovieFavouriteFailWhenContentResolveReturnNull() {
        val favouriteMovie = createFavouriteMovie()
        val contentValues = createFavouriteMovieContentValues(favouriteMovie)

        whenever(contentResolver.insert(CONTENT_URI, contentValues)).thenReturn(null)

        movieFavouriteModelImp.insert(favouriteMovie, insertListener)

        verify(contentResolver).insert(CONTENT_URI, contentValues)
        verifyNoMoreInteractions(contentResolver)
        verify(insertListener).onInsertFailed("Failed to insert movie into database")
        verify(insertListener, never()).onInsertSuccess()
        verifyNoMoreInteractions(insertListener)
    }

    @Test
    fun testRetrieveFavouriteMoviesWhenCursorNullFailToQuery() {
        val movieResults = Results<Movie>()

        whenever(resources.getString(R.string.database_retrieve_failure))
                .thenReturn("Failed to retrieve from database")
        whenever(contentResolver.query(
                CONTENT_URI,
                null,
                null,
                null,
                MOVIE_ID))
                .thenReturn(null)

        movieFavouriteModelImp.retrieve(retrieveListener)

        verify(retrieveListener).onRetrieveFailed(resources.getString(R.string.database_retrieve_failure))
        verify(retrieveListener, never()).onRetrievedSuccess(movieResults)
        verifyNoMoreInteractions(retrieveListener)
    }

    @Test
    fun testRetrieveFavouriteMovies() {
        val movie = createFavouriteMovie()
        val movieResults = Results<Movie>(listOf(movie))
        val cursor = createCursorWithRow()

        whenever(resources.getString(R.string.database_retrieve_failure))
                .thenReturn("Failed to retrieve from database")
        whenever(contentResolver.query(
                CONTENT_URI,
                null,
                null,
                null,
                MOVIE_ID))
                .thenReturn(cursor)

        movieFavouriteModelImp.retrieve(retrieveListener)

        verify(retrieveListener).onRetrievedSuccess(ArgumentMatchers.any())
        verify(retrieveListener, never())
                .onRetrieveFailed(resources.getString(R.string.database_retrieve_failure))
        verifyNoMoreInteractions(retrieveListener)
    }

    @Test
    fun testRetrieveFavouriteMoviesCursorThrowsException() {
        whenever(resources.getString(R.string.database_retrieve_failure))
                .thenReturn("Failed to retrieve from database")
        whenever(contentResolver.query(
                CONTENT_URI,
                null,
                null,
                null,
                MOVIE_ID))
                .thenThrow(IllegalArgumentException("Something bad happened"))

        movieFavouriteModelImp.retrieve(retrieveListener)

        verify(retrieveListener).onRetrieveFailed("Something bad happened")
        verify(retrieveListener, never()).onRetrievedSuccess(ArgumentMatchers.any())
        verifyNoMoreInteractions(retrieveListener)
    }

    @Test
    fun testDeleteOnDeleteFailed() {
        val movieId = 63536
        val strMovieId = movieId.toString()
        val selectionArgs = arrayOf(strMovieId)

        val uri = CONTENT_URI.buildUpon().appendPath("63536").build()
        whenever(contentResolver.delete(uri, MOVIE_ID + "=?", selectionArgs))
                .thenReturn(0)

        movieFavouriteModelImp.delete(63536, deleteListener)

        verify(contentResolver).delete(uri, MOVIE_ID + "=?", selectionArgs)
        verifyNoMoreInteractions(contentResolver)
        verify(deleteListener).onDeleteFailed("Failed to delete movie from the database")
        verify(deleteListener, never()).onDeleteSuccess(0)
    }

    @Test
    fun testDeleteOnDeleteSuccess() {
        val movieId = 63536
        val strMovieId = movieId.toString()
        val selectionArgs = arrayOf(strMovieId)

        val uri = CONTENT_URI.buildUpon().appendPath("63536").build()
        whenever(contentResolver.delete(uri, MOVIE_ID + "=?", selectionArgs))
                .thenReturn(1)

        movieFavouriteModelImp.delete(63536, deleteListener)

        verify(contentResolver).delete(uri, MOVIE_ID + "=?", selectionArgs)
        verifyNoMoreInteractions(contentResolver)
        verify(deleteListener).onDeleteSuccess(1)
        verify(deleteListener, never()).onDeleteFailed("Failed to delete movie from the database")
    }

    @Test
    fun testQueryMovieFoundMovie() {
        val movieId = 35764
        val strMovieId = movieId.toString()
        val uri = CONTENT_URI.buildUpon().appendPath(strMovieId).build()
        val selectionArgs = arrayOf(strMovieId)
        val cursor = createCursorWithRow()

        whenever(contentResolver.query(uri, null, MOVIE_ID + "=?", selectionArgs, null))
                .thenReturn(cursor)

        movieFavouriteModelImp.queryMovie(movieId, queryMovieListener)

        verify(contentResolver).query(uri, null, MOVIE_ID + "=?", selectionArgs, null)
        verifyNoMoreInteractions(contentResolver)
        verify(queryMovieListener).onQueryMovieSuccess(movieId, true)
        verify(queryMovieListener, never()).onQueryMovieFailed("Failed to query database")
        verifyNoMoreInteractions(queryMovieListener)
    }

    @Test
    fun testQueryMovieFoundMovieFailedWithNullCursor() {
        val movieId = 35764
        val strMovieId = movieId.toString()
        val uri = CONTENT_URI.buildUpon().appendPath(strMovieId).build()
        val selectionArgs = arrayOf(strMovieId)

        whenever(contentResolver.query(uri, null, MOVIE_ID + "=?", selectionArgs, null))
                .thenReturn(null)

        movieFavouriteModelImp.queryMovie(movieId, queryMovieListener)

        verify(contentResolver).query(uri, null, MOVIE_ID + "=?", selectionArgs, null)
        verifyNoMoreInteractions(contentResolver)
        verify(queryMovieListener, never()).onQueryMovieSuccess(movieId, true)
        verify(queryMovieListener).onQueryMovieFailed("Failed to query database")
        verifyNoMoreInteractions(queryMovieListener)
    }

    @Test
    fun testQueryMovieFoundMovieFailsWithEmptyCursor() {
        val movieId = 35764
        val strMovieId = movieId.toString()
        val uri = CONTENT_URI.buildUpon().appendPath(strMovieId).build()
        val selectionArgs = arrayOf(strMovieId)
        val cursor = createEmptyCursor()

        whenever(contentResolver.query(uri, null, MOVIE_ID + "=?", selectionArgs, null))
                .thenReturn(cursor)

        movieFavouriteModelImp.queryMovie(movieId, queryMovieListener)

        verify(contentResolver).query(uri, null, MOVIE_ID + "=?", selectionArgs, null)
        verifyNoMoreInteractions(contentResolver)
        verify(queryMovieListener).onQueryMovieSuccess(movieId, false)
        verify(queryMovieListener, never()).onQueryMovieFailed("Failed to query database")
        verifyNoMoreInteractions(queryMovieListener)
    }

    @Test
    fun testGetMovieFavourite() {
        val movieId = 374649
        val strMovieId = movieId.toString()
        val uri = CONTENT_URI.buildUpon().appendPath(strMovieId).build()
        val selectionArgs = arrayOf(strMovieId)
        val cursor = createCursorWithRow()

        whenever(contentResolver.query(
                uri,
                null,
                MOVIE_ID + "=?",
                selectionArgs,
                null)).thenReturn(cursor)

        movieFavouriteModelImp.getMovieFavourite(movieId, getMovieFavouriteListener)

        verify(contentResolver).query(uri, null, MOVIE_ID + "=?", selectionArgs, null)
        verifyNoMoreInteractions(contentResolver)
        verify(getMovieFavouriteListener).onGetMovieFavouriteSuccess(ArgumentMatchers.any())
        verify(getMovieFavouriteListener, never()).onGetMovieFavouriteFailure("Movie was not found")
        verifyNoMoreInteractions(getMovieFavouriteListener)
    }

    @Test
    fun testGetMovieFavouriteFailureIfCursorNull() {
        val movieId = 374649
        val strMovieId = movieId.toString()
        val uri = CONTENT_URI.buildUpon().appendPath(strMovieId).build()
        val selectionArgs = arrayOf(strMovieId)

        whenever(contentResolver.query(
                uri,
                null,
                MOVIE_ID + "=?",
                selectionArgs,
                null)).thenReturn(null)

        movieFavouriteModelImp.getMovieFavourite(movieId, getMovieFavouriteListener)

        verify(contentResolver).query(uri, null, MOVIE_ID + "=?", selectionArgs, null)
        verifyNoMoreInteractions(contentResolver)
        verify(getMovieFavouriteListener).onGetMovieFavouriteFailure("Failed to query database")
        verify(getMovieFavouriteListener, never()).onGetMovieFavouriteSuccess(ArgumentMatchers.any())
        verifyNoMoreInteractions(getMovieFavouriteListener)
    }

    @Test
    fun testGetMovieFavouriteFailureToFindFavouriteMovie() {
        val movieId = 374649
        val strMovieId = movieId.toString()
        val uri = CONTENT_URI.buildUpon().appendPath(strMovieId).build()
        val selectionArgs = arrayOf(strMovieId)
        val cursor = createEmptyCursor()

        whenever(contentResolver.query(
                uri,
                null,
                MOVIE_ID + "=?",
                selectionArgs,
                null)).thenReturn(cursor)

        movieFavouriteModelImp.getMovieFavourite(movieId, getMovieFavouriteListener)

        verify(contentResolver).query(uri, null, MOVIE_ID + "=?", selectionArgs, null)
        verifyNoMoreInteractions(contentResolver)
        verify(getMovieFavouriteListener).onGetMovieFavouriteFailure("Movie was not found")
        verify(getMovieFavouriteListener, never()).onGetMovieFavouriteSuccess(ArgumentMatchers.any())
        verifyNoMoreInteractions(getMovieFavouriteListener)
    }

    private fun createCursorWithRow(): Cursor {
        val movie = createFavouriteMovie()

        val cursor = MatrixCursor(arrayOf(
                MOVIE_ID,
                POSTER_PATH,
                OVERVIEW,
                RELEASE_DATE,
                TITLE,
                BACKDROP_PATH,
                VOTE_AVERAGE,
                VOTE_COUNT,
                TAGLINE,
                HOMEPATH,
                RUNTIME))

        cursor.addRow(arrayOf(
                movie.id,
                movie.poster_path,
                movie.overview,
                movie.release_date,
                movie.title,
                movie.backdrop_path,
                movie.vote_average,
                movie.vote_count,
                movie.tagline,
                movie.homepage,
                movie.runtime))

        return cursor
    }

    private fun createEmptyCursor(): Cursor {
        return MatrixCursor(arrayOf(
                MOVIE_ID,
                POSTER_PATH,
                OVERVIEW,
                RELEASE_DATE,
                TITLE,
                BACKDROP_PATH,
                VOTE_AVERAGE,
                VOTE_COUNT,
                TAGLINE,
                HOMEPATH,
                RUNTIME))
    }

    private fun createFavouriteMovieContentValues(favourite: Movie): ContentValues {
        val contentValues = ContentValues()

        contentValues.put(MOVIE_ID, favourite.id)
        contentValues.put(POSTER_PATH, favourite.poster_path)
        contentValues.put(OVERVIEW, favourite.overview)
        contentValues.put(RELEASE_DATE, favourite.release_date)
        contentValues.put(TITLE, favourite.title)
        contentValues.put(BACKDROP_PATH, favourite.backdrop_path)
        contentValues.put(VOTE_AVERAGE, favourite.vote_average)
        contentValues.put(VOTE_COUNT, favourite.vote_count)
        contentValues.put(TAGLINE, favourite.tagline)
        contentValues.put(HOMEPATH, favourite.homepage)
        contentValues.put(RUNTIME, favourite.runtime)

        return contentValues
    }

    private fun createFavouriteMovie(): Movie {
        return Movie(
                1234,
                "poster_path",
                "overview",
                "release_date",
                "title",
                "backdrop_path",
                7.3F,
                4.4F,
                "tagline",
                "homepage",
                120)
    }
}