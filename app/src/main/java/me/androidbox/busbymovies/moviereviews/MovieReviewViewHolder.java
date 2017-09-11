package me.androidbox.busbymovies.moviereviews;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
    @BindView(R.id.tvInitialReviewer) ImageView mInitialReviewer;
    private Context context;

    public MovieReviewViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        ButterKnife.bind(MovieReviewViewHolder.this, itemView);
    }

    public void populateReviewData(Review review) {
        mTvContent.setText(review.getContent());

        String avatar = appendAvatarWithAuthorsInitial(review.getAuthor());

        final int drawableResourceId = context.getResources().getIdentifier(avatar, "drawable", context.getPackageName());
        mInitialReviewer.setImageResource(drawableResourceId);
    }

    private String appendAvatarWithAuthorsInitial(final String author) {
        final String initial = String.valueOf(author.charAt(0));
        String avatar = "avatar_";
        return avatar + initial.toLowerCase();
    }
}

    // Android Studio 3.0 Beta 5