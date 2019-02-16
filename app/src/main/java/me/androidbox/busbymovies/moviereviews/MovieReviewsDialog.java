package me.androidbox.busbymovies.moviereviews;

import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieReviewAdapter;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;

/**
 * Created by steve on 4/11/17.
 */

public class MovieReviewsDialog extends DialogFragment {
    @BindView(R.id.rvMovieReviews) RecyclerView mRvMovieReviews;

    private Results<Review> mReviewList;
    private static final String MOVIE_REVIEW_KEY = "movie_review";
    private Unbinder mUnbinder;

    public MovieReviewsDialog() {
        /* no-op */
    }

    public static MovieReviewsDialog newInstance(Results<Review> reviews) {
        final Bundle args = new Bundle();
        args.putParcelable(MOVIE_REVIEW_KEY, reviews);

        final MovieReviewsDialog movieReviewsDialog = new MovieReviewsDialog();
        movieReviewsDialog.setArguments(args);

        return movieReviewsDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.movie_reviews, container, false);

        mUnbinder = ButterKnife.bind(MovieReviewsDialog.this, view);

        final Bundle args = getArguments();
        if(args != null) {
            if(args.containsKey(MOVIE_REVIEW_KEY)) {
                mReviewList = args.getParcelable(MOVIE_REVIEW_KEY);
            }
        }

        setupRecyclerView();

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);

        return view;
    }

    private void setupRecyclerView() {
        mRvMovieReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvMovieReviews.setHasFixedSize(true);
        mRvMovieReviews.setAdapter(MovieReviewAdapter.newInstance(mReviewList));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
