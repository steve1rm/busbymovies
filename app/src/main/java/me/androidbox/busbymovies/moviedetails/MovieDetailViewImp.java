package me.androidbox.busbymovies.moviedetails;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.ResultsTrailer;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.Misc;
import me.androidbox.busbymovies.utils.MovieImage;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailViewImp extends Fragment implements MovieDetailViewContract {
    public static final String TAG = MovieDetailViewImp.class.getSimpleName();
    public static final String MOVIE_ID_KEY = "movie_id_key";
    private Unbinder mUnbinder;
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
    @BindView(R.id.ivPlayTrailer) ImageView mIvPlayTrailer;
    @BindView(R.id.youtubeFragmentContainer) FrameLayout mYoutubeFragmentContainer;
    @Nullable @BindView(R.id.fabMovieFavourite) FloatingActionButton mFabMovieFavourite;

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

        return view;
    }

    @SuppressWarnings("unsued")
    @OnClick(R.id.ivPlayTrailer)
    public void requestStartMovieTrailer() {
        Timber.d("requestStartMovieTrailer");
        mMovieDetailPresenterImp.requestMovieTrailer(mMovieId);
    }

    @Override
    public void failedToGetMovieTrailers(String errorMessage) {
        Timber.e("failedToGetMovieTrailers %s", errorMessage);
    }

    @Override
    public void startPlayingMovieTrailer(ResultsTrailer<Trailer> trailerList) {
        String key = trailerList.getMovieTrailers().get(0).getKey();
        setupYoutubePlayer(key);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if(mMovieDetailPresenterImp != null) {
            mMovieDetailPresenterImp.detachView();
        }
    }

    private boolean mhasFavourited = false;

    @SuppressWarnings("unused")
    @OnClick(R.id.fabFavourites)
    public void addFavourite(View view) {

        final Animator animator;
        if(mhasFavourited) {
            animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.rotate_remove_favourite);

            Animator removeFavouriteMovie = AnimatorInflater.loadAnimator(getActivity(), R.animator.remove_favourite_movie);
            removeFavouriteMovie.setTarget(mFabMovieFavourite);
            removeFavouriteMovie.start();

            Timber.d("Height %f Width %f", Misc.getHeightInDp(getResources()), Misc.getWidthInDp(getResources()));


            mhasFavourited = false;
        }
        else {
            animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.rotate_add_favourite);

            /* Add the favourite button to the image */
            Animator moveFavourite = AnimatorInflater.loadAnimator(getActivity(), R.animator.add_favourite_movie);
            moveFavourite.setTarget(mFabMovieFavourite);
            moveFavourite.start();

            mhasFavourited = true;
        }

        animator.setTarget(view);
        animator.start();
/*
        Snackbar.make(view, R.string.add_favourite_movies, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, View -> Timber.d("onClick snackbar"))
                .show();
*/


 /*       Animator animator;
        if(mhasFavourited) {
            animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.rotate_add_favourite);
            mhasFavourited = false;
        }
        else {
            animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.rotate_remove_favourite);
            mhasFavourited = true;
        }

        animator.setTarget(view);
        animator.start();
*/
        /*
        Snackbar.make(view, R.string.add_favourite_movies, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Timber.d("onClick snackbar");
                    }
                }).show();
*/
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


}
