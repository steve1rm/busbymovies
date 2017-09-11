package me.androidbox.busbymovies.moviereviews;

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
    @BindView(R.id.tvContent) TextView mTvContent;

    public MovieReviewViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(MovieReviewViewHolder.this, itemView);
    }

    public void populateReviewData(Review review) {
        mTvContent.setText(review.getContent());
    }
}
