package me.androidbox.busbymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.moviedetails.MovieReviewViewHolder;

/**
 * Created by steve on 4/12/17.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewViewHolder> {
    private Results<Review> mReviewList;

    private MovieReviewAdapter(Results<Review> reviews) {
        mReviewList = reviews;
    }

    public static MovieReviewAdapter newInstance(Results<Review> reviews) {
        return new MovieReviewAdapter(reviews);
    }

    @Override
    public MovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_review_item, parent, false);

        return new MovieReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewViewHolder holder, int position) {
        holder.populateReviewData(mReviewList.getResults().get(position));
    }

    @Override
    public int getItemCount() {
        if(mReviewList != null) {
            return mReviewList.getResults().size();
        }
        else {
            return 0;
        }
    }
}
