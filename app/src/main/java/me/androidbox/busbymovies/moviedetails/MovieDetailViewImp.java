package me.androidbox.busbymovies.moviedetails;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.MultiTransformation;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieActorsAdapter;
import me.androidbox.busbymovies.adapters.MovieTrailerAdapter;
import me.androidbox.busbymovies.data.MovieFavouritesPresenterContract;
import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.models.Cast;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.moviereviews.MovieReviewsDialog;
import me.androidbox.busbymovies.utils.BlurTransformation;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.GlideApp;
import me.androidbox.busbymovies.utils.MovieImage;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailViewImp extends Fragment implements
        MovieDetailViewContract,
        StartMovieTrailerListener,
        MovieFavouritesPresenterContract.DbOperationsListener {

    public static final String TAG = MovieDetailViewImp.class.getSimpleName();
    public static final String MOVIE_ID_KEY = "movie_id_key";

    private Unbinder mUnbinder;
    private MovieTrailerAdapter mMovieTrailerAdapter;
    private Results<Review> mReviewList;
    private Results<Trailer> mTrailerList;
    private Movie mMovie;
    private BottomSheetBehavior<FrameLayout> mBottomSheetBehavior;
    private int mMovieId;
    private MovieActorsAdapter movieActorsAdapter;

    @Inject MovieDetailPresenterContract<MovieDetailViewContract> mMovieDetailPresenterImp;
    @Inject MovieFavouritesPresenterContract mMovieFavouritePresenterContact;

    @BindView(R.id.ivBackdropPoster) ImageView mIvBackdropPoster;
    @BindView(R.id.tvTagLine) TextView mTvTagLine;
    @BindView(R.id.ivThumbnail) ImageView mIvThumbnail;
    @BindView(R.id.tvTitle) TextView mTvTitle;
    @BindView(R.id.tvRelease) TextView mTvRelease;
    @BindView(R.id.rbVoteAverage) RatingBar mRbVoteAverage;
    @BindView(R.id.tvSynopsis) TextView mTvSynopsis;
    @BindView(R.id.content) FrameLayout mContent;
    @BindView(R.id.tvHomepage) TextView mTvHomepage;
    @BindView(R.id.tvRuntime) TextView mTvRuntime;
    @BindView(R.id.svMovieFooter) ScrollView mSvMovieFooter;
    @BindView(R.id.tvVoteAverage) TextView mTvVoteAverage;
    @BindView(R.id.tool_bar) Toolbar mToolBar;
    @BindView(R.id.tvTrailers) TextView mTvTrailers;
    @BindView(R.id.tvReviews) TextView mTvReviews;
    @BindView(R.id.rvMovieActors) RecyclerView rvMovieActors;
    @BindView(R.id.youtubeFragmentContainer) FrameLayout mYoutubeFragmentContainer;
    @Nullable @BindView(R.id.ivPlayTrailer) ImageView mIvPlayTrailer;
    @Nullable @BindView(R.id.fabMovieFavourite) FloatingActionButton mFabMovieFavourite;
    @Nullable @BindView(R.id.bottomSheet) FrameLayout mBottomSheet;
    @Nullable @BindView(R.id.rvTrailerList) RecyclerView mRvTrailerList;

    public MovieDetailViewImp() {
        // Required empty public constructor
    }

    /* Factory Method */
    public static MovieDetailViewImp newInstance(int movieId) {
        final Bundle args = new Bundle();
        args.putInt(MOVIE_ID_KEY, movieId);

        MovieDetailViewImp movieDetailViewImp = new MovieDetailViewImp();
        movieDetailViewImp.setArguments(args);

        return movieDetailViewImp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_detail_view, container, false);

        mUnbinder = ButterKnife.bind(MovieDetailViewImp.this, view);

        setupToolBar();
        setupBottomSheet();
        setupActorAdapter();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Bundle args = getArguments();
        if(args != null) {
            mMovieId = args.getInt(MOVIE_ID_KEY, -1);
            Timber.d("onActivityCreated %d", mMovieId);

            DaggerInjector.getApplicationComponent().inject(MovieDetailViewImp.this);
            if(mMovieDetailPresenterImp != null) {
                if(mMovieId != -1) {
                    mMovieDetailPresenterImp.attachView(MovieDetailViewImp.this);
                    mMovieDetailPresenterImp.requestMovieActors(mMovieId);

                    /* Check if we are getting a movie favourite */
                    mMovieFavouritePresenterContact.hasMovieAsFavourite(mMovieId, MovieDetailViewImp.this);
                }
                else {
                    Timber.e("Invalid movie id '-1'");
                }
            }
            else {
                Timber.e("mMovieDetailPresenterIm == null");
            }
        }
    }

    private void setupActorAdapter() {
        movieActorsAdapter = new MovieActorsAdapter();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvMovieActors.setLayoutManager(linearLayoutManager);
        rvMovieActors.setHasFixedSize(true);
        rvMovieActors.setAdapter(movieActorsAdapter);
    }

    private void setupBottomSheet() {
        if(mBottomSheet != null) {
            mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onStartMovieTrailer(String key, FrameLayout youtubeFragmentContainer, ImageView ivPlayerTrailerItem) {
//        setupYoutubePlayerTrailer(key, youtubeFragmentContainer, ivPlayerTrailerItem);
    }

    private void loadMovieTrailers(Results<Trailer> trailerList) {
        if(trailerList.getResults().size() > 0) {
            String trailerCount = trailerList.getResults().size() + " Trailer(s)";
            mTvTrailers.setText(trailerCount);

            mRvTrailerList.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            mRvTrailerList.setLayoutManager(linearLayoutManager);

            mMovieTrailerAdapter = new MovieTrailerAdapter(trailerList, MovieDetailViewImp.this);
            mRvTrailerList.setAdapter(mMovieTrailerAdapter);

            mTrailerList = trailerList;
        }
        else {
            mTrailerList = trailerList;
            String trailerCount = trailerList.getResults().size() + " Trailer(s)";
            mTvTrailers.setText(trailerCount);

            Timber.d("There are no trailers to load");
        }
    }

    @SuppressWarnings("unused")
    @Optional
    @OnClick(R.id.ivPlayTrailer)
    public void playIntoMovieTrailer() {
        Timber.d("requestStartMovieTrailer");
        if(mTrailerList.getResults().size() > 0) {
        /* Only play the header movie trailer in portrait mode as the landscape version has not room */
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                setupYoutubePlayer(mMovieTrailerAdapter.getTrailerFromPosition(0).getKey());
            }
        }
        else {
            Toast.makeText(getActivity(), "There are not trailers for this movie yet", Toast.LENGTH_LONG).show();
        }
    }

    private void isAlreadyBeenFavourited() {
        /* Check that the movie is not already in the database */
        mMovieFavouritePresenterContact.hasMovieAsFavourite(mMovie.getId(), MovieDetailViewImp.this);
    }

    private boolean isFlaggedAsFavourite(FloatingActionButton floatingActionButton) {
        Timber.d("isFlaggedAsFavourite %s", floatingActionButton.getTag().toString());
        return floatingActionButton.getTag().toString().equals("true");
    }

    private void addMovieAsFavourite(FloatingActionButton floatingActionButton) {
        floatingActionButton.setTag("true");
        Timber.d("AddMovieAsFavourite %s", floatingActionButton.getTag().toString());
    }

    private void removeMovieAsFavourite(FloatingActionButton floatingActionButton) {
        Timber.d("removeMovieAsFavourite %s", floatingActionButton.getTag().toString());
        floatingActionButton.setTag("false");
    }

    private void animateAddingFavourite() {
        final Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.save_favourite_movie);
        animator.setTarget(mFabMovieFavourite);
        mFabMovieFavourite.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.primary)));
        animator.start();

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabMovieFavourite)
    public void addFavouriteMovie(FloatingActionButton floatingActionButton) {
        Timber.d("addFavouriteMovie");

        Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.save_favourite_movie);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Timber.d("onAnimationStart");
                if(isFlaggedAsFavourite(floatingActionButton)) {
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.accent)));
                }
                else {
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.primary)));
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Timber.d("onAnimationEnd");
                /* Either save or remove the movie from the database */
                if(isFlaggedAsFavourite(floatingActionButton)) {
                    /* remove from database */
                    removeMovieAsFavourite(floatingActionButton);
                    mMovieFavouritePresenterContact.deleteFavouriteMovie(mMovie.getId(), MovieDetailViewImp.this);
                }
                else {
                    /* Add to database */
                    addMovieAsFavourite(floatingActionButton);
                    final Movie favouriteMovie = new Movie(
                            mMovie.getId(),
                            mMovie.getPoster_path(),
                            mMovie.getOverview(),
                            mMovie.getRelease_date(),
                            mMovie.getTitle(),
                            mMovie.getBackdrop_path(),
                            mMovie.getVote_average(),
                            mMovie.getTagline(),
                            mMovie.getHomepage(),
                            mMovie.getRuntime());

                    mMovieFavouritePresenterContact.insertFavouriteMovie(favouriteMovie, MovieDetailViewImp.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.setTarget(floatingActionButton);
        animator.start();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabTrailers)
    public void openTrailers() {
        if(mTrailerList != null) {
            if (mTrailerList.getResults().size() > 0) {
                if (mBottomSheet != null) {
                    if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        Timber.d("STATE_EXPANDED");
                    }
                    else {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        Timber.d("STATE_COLLAPSED");
                    }
                }
            }
            else {
                Toast.makeText(getActivity(), "There are not trailers for this movie yet", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Service not available please try again later", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabReviews)
    public void openReviews() {
        if(mReviewList == null) {
            Timber.e(TAG, "mReviewList == null");
            Toast.makeText(getActivity(), "Service unavailable. Try again", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mReviewList.getResults().size() > 0) {
            FragmentManager fragmentManager = getFragmentManager();
            MovieReviewsDialog movieReviewsDialog = MovieReviewsDialog.newInstance(mReviewList);
            movieReviewsDialog.show(fragmentManager, MovieReviewsDialog.class.getSimpleName());
        }
        else {
            Toast.makeText(getActivity(), "There are no reviews for this movie yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failedToGetMovieTrailers(String errorMessage) {
        Timber.e("failedToGetMovieTrailers %s", errorMessage);
    }

    @Override
    public void receivedMovieTrailers(Results<Trailer> trailerList) {
        loadMovieTrailers(trailerList);
    }

    private void setupYoutubePlayer(String key) {
        final YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.youtubeFragmentContainer, youTubePlayerFragment)
                .commit();

        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Timber.d("onInitializationSuccess");

                youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                    @Override
                    public void onPlaying() {
                        mYoutubeFragmentContainer.setVisibility(View.VISIBLE);
                        mToolBar.setVisibility(View.INVISIBLE);
                        mIvPlayTrailer.setVisibility(View.INVISIBLE);

                        Timber.d("onPlaying");
                    }

                    @Override
                    public void onPaused() {
                        Timber.d("onPaused");
                    }

                    @Override
                    public void onStopped() {
                        Timber.d("onStopped");
                        mToolBar.setVisibility(View.VISIBLE);
                        mIvPlayTrailer.setVisibility(View.VISIBLE);
                        mYoutubeFragmentContainer.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onBuffering(boolean b) {
                        Timber.d("onBuffering %b", b);
                    }

                    @Override
                    public void onSeekTo(int i) {
                        Timber.d("onSeekTo %d", i);
                    }
                });

                /* Start playing the youtube video */
                youTubePlayer.loadVideo(key);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Timber.e("Failed to initialize %s", youTubeInitializationResult.toString());
            }
        });
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void setupToolBar() {
        final AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mToolBar);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if(mMovieDetailPresenterImp != null) {
            mMovieDetailPresenterImp.detachView();
        }
    }

    @Override
    public void displayMovieDetails(Movie movie) {
        Timber.d("displayMovieDetails");
        mMovie = movie;

        mTvTagLine.setText(movie.getTagline());
        mTvTitle.setText(movie.getTitle());

        final String movieDate = mMovieDetailPresenterImp.getMovieFormattedDate(movie.getRelease_date(), Constants.FORMAT_MOVIE_DATE);
        mTvRelease.setText(movieDate);

        mTvSynopsis.setText(movie.getOverview());
        mRbVoteAverage.setRating(mMovieDetailPresenterImp.getVoteAverage(movie.getVote_average()));
        mTvHomepage.setText(movie.getHomepage());

        final String runningTime = "Running time " + movie.getRuntime() + " minutes";
        mTvRuntime.setText(runningTime);

        mTvVoteAverage.setText(String.valueOf(movie.getVote_average()));
        Timber.d("movie.getVote_average %f", movie.getVote_average());

        Glide.with(MovieDetailViewImp.this)
                .load(MovieImage.build(movie.getPoster_path(), MovieImage.ImageSize.w185))
    //            .bitmapTransform(new RoundedCornersTransformation(getActivity(), 16, 4, RoundedCornersTransformation.CornerType.ALL))
                .into(mIvThumbnail);

        /* Bind the data */
        Glide.with(MovieDetailViewImp.this)
                .load(MovieImage.build(movie.getBackdrop_path(), MovieImage.ImageSize.w500))
                .into(mIvBackdropPoster);
    }

    @Override
    public void displayErrorFailedToGetMovie(String errMessage) {
        Toast.makeText(getActivity(), "Failed to get Movie " + errMessage, Toast.LENGTH_LONG).show();
    }

    /* Open the movie homepage using chrome custom tabs */
    @SuppressWarnings("unused")
    @OnClick(R.id.tvHomepage)
    public void openHomePage() {
        if(URLUtil.isValidUrl(mTvHomepage.getText().toString())) {
            final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            final Bitmap backButton = BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp);
            builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.primary));
            builder.setCloseButtonIcon(backButton);
            builder.setShowTitle(true);

            builder.setStartAnimations(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
            builder.setExitAnimations(getActivity(), R.anim.slide_in_left, R.anim.slide_out_right);

            final CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(getActivity(), Uri.parse(mTvHomepage.getText().toString()));
        }
        else {
            Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_LONG).show();
            Timber.e("Invalid URL %s", mTvHomepage.getText().toString());
        }
    }

    @Override
    public void receivedMovieReviews(Results<Review> reviews) {
        Timber.d("receiveMovieReviews: %d", reviews.getResults().size());
        String reviewCount = reviews.getResults().size() + " Review(s)";
        mTvReviews.setText(reviewCount);
        mReviewList = reviews;
    }

    @Override
    public void onGetMovieFavouriteSuccess(Movie favourite) {
        Timber.d("onGetMovieFavouriteSuccess %d", favourite.getId());
        mMovie = favourite;

        mTvTagLine.setText(favourite.getTagline());
        mTvTitle.setText(favourite.getTitle());

        final String movieDate = mMovieDetailPresenterImp.getMovieFormattedDate(favourite.getRelease_date(), Constants.FORMAT_MOVIE_DATE);
        mTvRelease.setText(movieDate);

        mTvSynopsis.setText(favourite.getOverview());
        mRbVoteAverage.setRating(mMovieDetailPresenterImp.getVoteAverage(favourite.getVote_average()));
        mTvHomepage.setText(favourite.getHomepage());

        final String runningTime = "Running time " + favourite.getRuntime() + " minutes";
        mTvRuntime.setText(runningTime);

        mTvVoteAverage.setText(String.valueOf(favourite.getVote_average()));
        Timber.d("movie.getVote_average %f", favourite.getVote_average());

        GlideApp.with(MovieDetailViewImp.this)
                .load(MovieImage.build(favourite.getPoster_path(), MovieImage.ImageSize.w185))
                .transform(new MultiTransformation<>(
                        new RoundedCornersTransformation(16, 4, RoundedCornersTransformation.CornerType.ALL),
                        new jp.wasabeef.glide.transformations.BlurTransformation(5, 2)))
                .priority(Priority.NORMAL)
                .into(mIvThumbnail);

        /* Bind the data */
        GlideApp.with(MovieDetailViewImp.this)
                .load(MovieImage.build(favourite.getBackdrop_path(), MovieImage.ImageSize.w500))
                .priority(Priority.HIGH)
                .into(mIvBackdropPoster);

    }

    @Override
    public void onGetMovieFavouriteFailure(String errorMessage) {
        Timber.d("onGetMovieFavouriteFailure %s", errorMessage);
    }

    @Override
    public void failedToReceiveMovieReviews(String errorMessage) {
        Timber.e("failedToReceiveMovieReviews %s", errorMessage);
    }

    @Override
    public void onGetFavouriteMoviesSuccess(Results<Movie> favouriteList) {
        /* no-op */
    }

    @Override
    public void onGetFavouriteMoviesFailure(String errorMessage) {
        /* no-op */
    }

    @Override
    public void onInsertFavouriteSuccess() {
        Timber.d("onInsertFavouriteSuccess");
    }

    @Override
    public void onInsertFavouriteFailure(String errorMessage) {
        Timber.e("onInsertFavouriteFailure %s", errorMessage);
    }

    @Override
    public void onDeleteFavouriteMovieSuccess(int rowDeletedId) {
        Timber.d("onDeleteFavouriteMovieSuccess %d", rowDeletedId);
    }

    @Override
    public void onDeleteFavouriteMovieFailure(String errorMessage) {
        Timber.e("onDeleteFavouriteMovieFailure: %s", errorMessage);
    }

    @Override
    public void onHasMovieFavouriteSuccess(int movieId, boolean isFavourite) {
        if(isFavourite) {
            Timber.d("onMovieFavouriteSuccess %d", movieId);
            addMovieAsFavourite(mFabMovieFavourite);
            animateAddingFavourite();
            /* Populate the views from the database */
            mMovieFavouritePresenterContact.getMovieFavourite(movieId, MovieDetailViewImp.this);
            mMovieDetailPresenterImp.requestMovieTrailer(movieId);
            mMovieDetailPresenterImp.requestMovieReviews(movieId);
        }
        else {
            Timber.d("onMovieFavouriteSuccess %d", movieId);
            mMovieDetailPresenterImp.getMovieDetail(movieId);
            mMovieDetailPresenterImp.requestMovieTrailer(movieId);
            mMovieDetailPresenterImp.requestMovieReviews(movieId);
        }
    }

    @Override
    public void onHasMovieFavouriteFailure(String errorMessage) {
        Timber.d("onHasMovieFavouriteFailure %s", errorMessage);
    }

    @Override
    public void failedToReceiveMovieActors(String errorMessage) {
        Timber.d(errorMessage);
        Toast.makeText(getActivity(), "Failed to get movie actors", Toast.LENGTH_LONG).show();
    }

    @Override
    public void successToReceiveMovieActors(Cast<Actor> actorList) {
        movieActorsAdapter.populateActors(actorList);
    }


    @Override
    public void failedToGetSimilarMovies(String errorMessage) {
        Timber.e("FailedToGetSimilarMovies: %s", errorMessage);
    }

    @Override
    public void successToGetSimilarMovies(Results<Movies> similarMovies) {
        Timber.d("successToGetSimilarMovies: %d", similarMovies.getResults().size());
    }
}