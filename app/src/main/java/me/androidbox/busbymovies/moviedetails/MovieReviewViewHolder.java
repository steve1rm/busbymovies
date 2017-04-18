package me.androidbox.busbymovies.moviedetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Review;

/**
 * Created by steve on 4/12/17.
 */

public class MovieReviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvAuthor) TextView mTvAuthor;
    @BindView(R.id.tvContent) TextView mTvContent;
    @BindView(R.id.tvUrl) TextView mTvUrl;

    public MovieReviewViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(MovieReviewViewHolder.this, itemView);
    }

    public void populateReviewData(Review review) {
        mTvAuthor.setText(review.getAuthor());
        mTvContent.setText(review.getContent());
        mTvUrl.setText(review.getUrl());
    }
}
