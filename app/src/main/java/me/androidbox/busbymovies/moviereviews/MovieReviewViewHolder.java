package me.androidbox.busbymovies.moviereviews;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
    @NonNull
    private final Context context;

    public MovieReviewViewHolder(final View itemView) {
        super(itemView);

        ButterKnife.bind(MovieReviewViewHolder.this, itemView);
        context = itemView.getContext();
    }

    public void populateReviewData(@NonNull final Review review) {
        final String avatar = appendAvatarWithAuthorsInitial(review.getAuthor());

        final int drawableResourceId = context.getResources()
                .getIdentifier(avatar, "drawable", context.getPackageName());

        mInitialReviewer.setImageResource(drawableResourceId);
        mTvContent.setText(review.getContent());
    }

    private String appendAvatarWithAuthorsInitial(@NonNull final String author) {
        final String initial = String.valueOf(author.charAt(0));
        final String avatar = "avatar_";

        return avatar + initial.toLowerCase();
    }
}
