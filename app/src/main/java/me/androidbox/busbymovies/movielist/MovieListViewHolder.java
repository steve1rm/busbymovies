package me.androidbox.busbymovies.movielist;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.auto.factory.AutoFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.utils.ImageLoader;

/**
 * Created by steve on 2/26/17.
 */
@AutoFactory(implementing = IMovieListViewHolderFactory.class)
public class MovieListViewHolder
        extends RecyclerView.ViewHolder {

    @BindView(R.id.ivPosterImage) ImageView mIvPosterImage;
    @BindView(R.id.tvTagLine) TextView mTvTagLine;
    @BindView(R.id.palette) View mPalette;

    private MovieAdapter movieAdapter;
    private ImageLoader imageLoader;
    private MovieListItemClickedListener movieListItemClickedListener;

    public MovieListViewHolder(final View itemView,
                               final MovieAdapter movieAdapter,
                               final ImageLoader imageLoader,
                               final MovieListItemClickedListener movieListItemClickedListener) {
        super(itemView);

        ButterKnife.bind(MovieListViewHolder.this, itemView);

        this.movieAdapter = movieAdapter;
        this.imageLoader = imageLoader;
        this.movieListItemClickedListener = movieListItemClickedListener;
    }

    public void bindViewData(String tagline, String posterPath) {
        mTvTagLine.setText(tagline);

        imageLoader.load(
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

        movieListItemClickedListener.onMovieListItemClickedListener(movieId);
    }
}
