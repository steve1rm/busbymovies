package me.androidbox.busbymovies.moviereviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowDrawable;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Review;
import support.BaseRobolectricTestRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 1/5/18.
 */
public class MovieReviewViewHolderTest extends BaseRobolectricTestRunner {
    private MovieReviewViewHolder movieReviewViewHolder;
    private Context context;

    @Before
    public void setup() {
        context = ShadowApplication.getInstance().getApplicationContext();
        final View itemView = LayoutInflater.from(context).inflate(R.layout.movie_review_item, new LinearLayout(context));

        movieReviewViewHolder = new MovieReviewViewHolder(itemView);
    }

    @Test
    public void testMovieReviewViewHolder_isNotNullValue() {
        assertThat(movieReviewViewHolder, is(notNullValue()));
    }

    @Test
    public void testPopulateReviewData_setImageViewWithDrawableOfAuthorsInitial() {
        final Review review = createReview();
        final Drawable drawable = context.getResources().getDrawable(R.drawable.avatar_s, null);

        movieReviewViewHolder.populateReviewData(review);

        final ShadowDrawable shadowDrawable = Shadows.shadowOf(movieReviewViewHolder.mInitialReviewer.getDrawable());
        assertThat(shadowDrawable, is(drawable));
    }

    @Test
    public void testPopulateReviewData_setTextViewWithContent() {
        final Review review = createReview();

        movieReviewViewHolder.populateReviewData(review);

        assertThat(movieReviewViewHolder.mTvContent.getText().toString(),
                is(review.getContent()));
    }
    
    private Review createReview() {
        return new Review(
                "id number",
                "steve",
                "content",
                "url");
    }
}