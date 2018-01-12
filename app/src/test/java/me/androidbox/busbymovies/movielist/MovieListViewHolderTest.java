package me.androidbox.busbymovies.movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.shadows.ShadowApplication;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.utils.ImageLoader;
import me.androidbox.busbymovies.utils.MovieImage;
import me.androidbox.busbymovies.utils.MovieImage.ImageSize;
import support.BaseRobolectricTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MovieListViewHolderTest extends BaseRobolectricTestRunner {

    private MovieAdapter movieAdapter;
    private ImageLoader imageLoader;
    private MovieListViewHolder movieListViewHolder;
    private Context context;

    @Before
    public void setup() {
        context = ShadowApplication.getInstance().getApplicationContext();
        final View itemView = LayoutInflater.from(context)
                .inflate(R.layout.movielist_item, new RelativeLayout(context));

        setupMocks();
        movieListViewHolder = new MovieListViewHolder(
                itemView,
                movieAdapter,
                imageLoader);
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
                context,
                200,
                300,
                16,
                R.drawable.peopleplaceholder,
                movieListViewHolder.mIvPosterImage,
                MovieImage.build(POSTER_PATH, ImageSize.w185),
                movieListViewHolder.mPalette,
                movieListViewHolder.mTvTagLine);
        verifyNoMoreInteractions(imageLoader);

        assertThat(movieListViewHolder.mTvTagLine.getText().toString(), is("Movie tag line"));
    }

    private void setupMocks() {
        movieAdapter = Mockito.mock(MovieAdapter.class);
        imageLoader = Mockito.mock(ImageLoader.class);
    }
}