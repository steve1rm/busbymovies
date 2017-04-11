package me.androidbox.busbymovies.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.busbymovies.R;

/**
 * Created by steve on 4/11/17.
 */

public class MovieReviewsDialog extends DialogFragment {
    @BindView(R.id.tvAuthor) TextView mTvAuthor;
    @BindView(R.id.tvContent) TextView mTvContent;
    @BindView(R.id.tvUrl) TextView mTvUrl;
//    @BindView(R.id.rvMovieReviews) RecyclerView mRvMovieReviews;

    public static final String AUTHOR_KEY = "author";
    public static final String CONTENT_KEY = "content";
    public static final String URL_KEY = "url";

    private Unbinder mUnbinder;

    public MovieReviewsDialog() {
        /* no-op */
    }

    public static MovieReviewsDialog newInstance(String author, String content, String url) {
        final Bundle args = new Bundle();
        args.putString(AUTHOR_KEY, author);
        args.putString(CONTENT_KEY, content);
        args.putString(URL_KEY, content);

        final MovieReviewsDialog movieReviewsDialog = new MovieReviewsDialog();
        movieReviewsDialog.setArguments(args);

        return movieReviewsDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.movie_review_item, container, false);

        mUnbinder = ButterKnife.bind(MovieReviewsDialog.this, view);

        //mRvMovieReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);

        return view;
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
