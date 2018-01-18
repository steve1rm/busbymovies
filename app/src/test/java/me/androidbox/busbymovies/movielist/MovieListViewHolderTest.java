package me.androidbox.busbymovies.movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.ShadowApplication;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.utils.ImageLoader;
import support.BaseRobolectricTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class MovieListViewHolderTest extends BaseRobolectricTestRunner {

    private MovieAdapter movieAdapter;
    private ImageLoader imageLoader;
    private MovieListViewHolder movieListViewHolder;
    private MovieListItemClickedListener movieListItemClickedListener;

    @Before
    public void setup() {
        final Context context = ShadowApplication.getInstance().getApplicationContext();
        final View itemView = LayoutInflater.from(context)
                .inflate(R.layout.movielist_item, new RelativeLayout(context));

        setupMocks();
        movieListViewHolder = new MovieListViewHolder(
                itemView,
                movieAdapter,
                imageLoader,
                movieListItemClickedListener);
    }

    @Test
    public void testMovieListViewHolder_isNotNullValue() {
        assertThat(movieListViewHolder, is(notNullValue()));
    }

    @Test
    public void testBindViewData_imageLoader_load() {
        final String TAG_LINE = "Movie tag line";
        final String POSTER_PATH = "poster_path";

        movieListViewHolder.bindViewData(TAG_LINE, POSTER_PATH);

        verify(imageLoader).load(
                200,
                300,
                16,
                R.drawable.peopleplaceholder,
                movieListViewHolder.mIvPosterImage,
                POSTER_PATH,
                movieListViewHolder.mPalette,
                movieListViewHolder.mTvTagLine);
        verifyNoMoreInteractions(imageLoader);

        assertThat(movieListViewHolder.mTvTagLine.getText().toString(), is("Movie tag line"));
    }

    @Test
    public void testOnMovieClicked_startActivity() {
        when(movieAdapter.getMovieId(0)).thenReturn(1234);

        movieListViewHolder.onMovieClicked();

        verify(movieListItemClickedListener).onMovieListItemClickedListener(0);
        verifyNoMoreInteractions(movieListItemClickedListener);

/*
        final ShadowActivity shadowActivity = shadowOf(new MovieDetailActivity());
        final Intent startedIntent = shadowActivity.getNextStartedActivity();
        startedIntent.putExtra(MovieDetailViewImp.MOVIE_ID_KEY, 0);
        final ShadowIntent shadowIntent = shadowOf(startedIntent);

        assertThat(shadowIntent.getIntentClass().getName(), is(MovieDetailActivity.class.getName()));
*/
    }

    private void setupMocks() {
        movieAdapter = mock(MovieAdapter.class);
        imageLoader = mock(ImageLoader.class);
        movieListItemClickedListener = mock(MovieListItemClickedListener.class);
    }
}