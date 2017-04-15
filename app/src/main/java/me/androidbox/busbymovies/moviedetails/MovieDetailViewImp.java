package me.androidbox.busbymovies.moviedetails;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieTrailerAdapter;
import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.MovieImage;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailViewImp extends Fragment implements
        MovieDetailViewContract,
        StartMovieTrailerListener {
    public static final String TAG = MovieDetailViewImp.class.getSimpleName();
    public static final String MOVIE_ID_KEY = "movie_id_key";
    private Unbinder mUnbinder;
    private MovieTrailerAdapter mMovieTrailerAdapter;
    private int mMovieId;

    @Inject MovieDetailPresenterContract<MovieDetailViewContract> mMovieDetailPresenterImp;

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
    @BindView(R.id.youtubeFragmentContainer) FrameLayout mYoutubeFragmentContainer;
    @BindView(R.id.tvTrailers) TextView mTvTrailers;
    @BindView(R.id.tvReviews) TextView mTvReviews;
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

    /**
     * Set the text has link clickable
     */
    private void setupTextViewAsLinkClickable() {
 /*       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            mTvReviews.setText(Html.fromHtml("Contains 3 reviews", Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            mTvReviews.setMovementMethod(LinkMovementMethod.getInstance());
            mTvReviews.setText(Html.fromHtml("Contains 3 reviews"));
        }
 */
/*
        String url = "Contains 3 reviews";
        Pattern pattern = Pattern.compile(url);
        Linkify.addLinks(mTvReviews, pattern, "http://");
        mTvReviews.setText(Html.fromHtml("<a href='http://\"+url+\"'>http://\"+url+\"</a>"));
*/
        mTvReviews.setText("<a>Contains 3 reviews</a>");
        mTvReviews.setMovementMethod(LinkMovementMethod.getInstance());
    }


    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Timber.d("clickableSpan");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            Timber.d("updateDrawState");
            ds.setUnderlineText(false);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_detail_view, container, false);

        mUnbinder = ButterKnife.bind(MovieDetailViewImp.this, view);

        SpannableString spannableString = new SpannableString("Contains 3 reviews");
        spannableString.setSpan(clickableSpan, 0, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvReviews.setText(spannableString);
        mTvReviews.setMovementMethod(LinkMovementMethod.getInstance());
        mTvReviews.setTextColor(ColorStateList.valueOf(R.color.primary_dark));


    //    setupTextViewAsLinkClickable();
        setupToolBar();
        setupFavourite();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupBottomSheet();
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Bundle args = getArguments();
        if(args != null) {
            final int movieId = args.getInt(MOVIE_ID_KEY, -1);
            Timber.d("onActivityCreated %d", movieId);

            DaggerInjector.getApplicationComponent().inject(MovieDetailViewImp.this);
            if(mMovieDetailPresenterImp != null) {
                if(movieId != -1) {
                    mMovieDetailPresenterImp.attachView(MovieDetailViewImp.this);
                    mMovieDetailPresenterImp.getMovieDetail(movieId);
                    mMovieDetailPresenterImp.requestMovieTrailer(movieId);
                    mMovieId = movieId;
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

    private void setupBottomSheet() {
        if(mBottomSheet != null) {
            BottomSheetBehavior<FrameLayout> bottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onStartMovieTrailer(String key, FrameLayout youtubeFragmentContainer, ImageView ivPlayerTrailerItem) {
//        setupYoutubePlayerTrailer(key, youtubeFragmentContainer, ivPlayerTrailerItem);
    }

    private void loadMovieTrailers(Results<Trailer> trailerList) {
        if(mRvTrailerList != null) {
            mRvTrailerList.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            mRvTrailerList.setLayoutManager(linearLayoutManager);
            mMovieTrailerAdapter = new MovieTrailerAdapter(trailerList, MovieDetailViewImp.this);
            mRvTrailerList.setAdapter(mMovieTrailerAdapter);
        }
        else {
            Timber.e("mRvTrailerList == null");
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tvReviews)
    public void openMovieReviews() {
        /* Open fragment dialog box */
        mMovieDetailPresenterImp.requestMovieReviews(mMovieId);
    }

    @SuppressWarnings("unused")
    @Optional
    @OnClick(R.id.ivPlayTrailer)
    public void playIntoMovieTrailer() {
        Timber.d("requestStartMovieTrailer");
        /* Only play the header movie trailer in portrait mode as the landscape version has not room */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setupYoutubePlayer(mMovieTrailerAdapter.getTrailerFromPosition(0).getKey());
        }
    }

    private boolean isActive = false;

    private void setupFavourite() {
        @ColorInt final int colorActive = ContextCompat.getColor(getActivity(), R.color.fb_color_active);
        @ColorInt final int colorPassive = ContextCompat.getColor(getActivity(), R.color.fb_color_passive);

        final float from = 1.0f;
        final float to = 1.3f;

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFabMovieFavourite, View.SCALE_X, from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFabMovieFavourite, View.SCALE_Y,  from, to);
        ObjectAnimator translationZ = ObjectAnimator.ofFloat(mFabMovieFavourite, View.TRANSLATION_Z, from, to);

        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(scaleX, scaleY, translationZ);
        set1.setDuration(100);
        set1.setInterpolator(new AccelerateInterpolator());

        set1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFabMovieFavourite.setImageResource(isActive ? R.drawable.heart_active : R.drawable.heart_passive);
                mFabMovieFavourite.setBackgroundTintList(ColorStateList.valueOf(isActive ? colorActive : colorPassive));
                isActive = !isActive;
            }
        });

        ObjectAnimator scaleXBack = ObjectAnimator.ofFloat(mFabMovieFavourite, View.SCALE_X, to, from);
        ObjectAnimator scaleYBack = ObjectAnimator.ofFloat(mFabMovieFavourite, View.SCALE_Y, to, from);
        ObjectAnimator translationZBack = ObjectAnimator.ofFloat(mFabMovieFavourite, View.TRANSLATION_Z, to, from);

        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0.5f, 1.3f);
        path.lineTo(0.75f, 0.8f);
        path.lineTo(1.0f, 1.0f);
        PathInterpolator pathInterpolator = new PathInterpolator(path);

        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(scaleXBack, scaleYBack, translationZBack);
        set2.setDuration(300);
        set2.setInterpolator(pathInterpolator);

        final AnimatorSet set = new AnimatorSet();
        set.playSequentially(set1, set2);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFabMovieFavourite.setClickable(true);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mFabMovieFavourite.setClickable(false);
            }
        });


        mFabMovieFavourite.setOnClickListener(v -> set.start());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabMovieFavourite)
    public void addFavouriteMovie() {

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

/*
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Picasso.with(getActivity())
                        .load(MovieImage.build(movie.getPoster_path(), MovieImage.ImageSize.w185))
                        .resize(120, 180)
                        .centerCrop()
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                mIvThumbnail.setImageBitmap(bitmap);
                                Palette.from(bitmap)
                                        .maximumColorCount(16)
                                        .generate(new Palette.PaletteAsyncListener() {
                                            @Override
                                            public void onGenerated(Palette palette) {
                                                Palette.Swatch swatch = palette.getLightVibrantSwatch();

                                                if(swatch != null) {
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            mSvMovieFooter.setBackgroundColor(swatch.getRgb());
                                                            mTvSynopsis.setTextColor(swatch.getBodyTextColor());
                                                            mRbVoteAverage.setBackgroundColor(swatch.getTitleTextColor());
                                                            mTvHomepage.setTextColor(swatch.getTitleTextColor());
                                                            mTvRuntime.setTextColor(swatch.getTitleTextColor());
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Timber.e("onBitmapFailed");
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                Timber.d("onPrepareLoad");
                            }
                        });
            }
        };
*/

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
                .bitmapTransform(new RoundedCornersTransformation(getActivity(), 16, 4, RoundedCornersTransformation.CornerType.ALL))
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
        /* Open movie reviews dialog fragment */
        FragmentManager fragmentManager = getFragmentManager();
        MovieReviewsDialog movieReviewsDialog = MovieReviewsDialog.newInstance(reviews);
        movieReviewsDialog.show(fragmentManager, MovieReviewsDialog.class.getSimpleName());
    }

    @Override
    public void failedToReceiveMovieReviews(String errorMessage) {
        Timber.e("failedToReceiveMovieReviews %s", errorMessage);
    }


}
