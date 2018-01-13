package me.androidbox.busbymovies.movielist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.utils.ImageLoader;
import me.androidbox.busbymovies.utils.MovieImage;
import me.androidbox.busbymovies.utils.MovieImage.ImageSize;
import timber.log.Timber;

/**
 * Created by steve on 2/26/17.
 */
public class MovieListViewHolder
        extends RecyclerView.ViewHolder {

    @BindView(R.id.ivPosterImage) ImageView mIvPosterImage;
    @BindView(R.id.tvTagLine) TextView mTvTagLine;
    @BindView(R.id.palette) View mPalette;

    private ImageLoader imageLoader;
    private MovieAdapter movieAdapter;
    private MovieListActivity movieListActivity;

    /* Ensure the context is garbage collected so using a weak reference */
    private WeakReference<Context> mContext;

    public MovieListViewHolder(View itemView, final MovieAdapter movieAdapter, final ImageLoader imageLoader, final MovieListActivity movieListActivity) {
        super(itemView);

        ButterKnife.bind(MovieListViewHolder.this, itemView);
        mContext = new WeakReference<>(itemView.getContext());
        this.imageLoader = imageLoader;
        this.movieAdapter = movieAdapter;
        this.movieListActivity = movieListActivity;
    }

    public void bindViewData(String tagline, String posterPath) {
        mTvTagLine.setText(tagline);

        imageLoader.load(
                mContext.get(),
                200,
                300,
                16,
                R.drawable.peopleplaceholder,
                mIvPosterImage,
                posterPath,
                mPalette,
                mTvTagLine);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.movieListItem)
    public void onMovieClicked() {
        int movieId = movieAdapter.getMovieId(getAdapterPosition());
        Timber.d("Movie ID: %d", movieId);

        movieListActivity.onMovieClicked(movieId);

/*
        final Intent intent = new Intent(mContext.get(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailViewImp.MOVIE_ID_KEY, movieId);
        mContext.get().startActivity(intent);

        final Activity activity = (Activity)mContext.get();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
*/

    }
}
