package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowDrawable;

import me.androidbox.busbymovies.BuildConfig;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.di.BusbyMoviesMainApplication;
import me.androidbox.busbymovies.models.Actor;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 9/16/17.
 */
@Config(packageName = "me.androidbox.busbymovies",
        sdk = Build.VERSION_CODES.LOLLIPOP,
        application = BusbyMoviesMainApplication.class,
        constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class MovieActorsViewHolderTest {
    private MovieActorsViewHolder movieActorsViewHolder;

    @Before
    public void setup() {
        final Context context = ShadowApplication.getInstance().getApplicationContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.movie_actors_item, new LinearLayout(context));

        movieActorsViewHolder = new MovieActorsViewHolder(view);
    }

    @Test
    public void testShouldPopulateActorWithValidData() {
        final Actor actor = getActor();
        movieActorsViewHolder.populateActor(actor);

        final ShadowDrawable shadowDrawable = Shadows.shadowOf(movieActorsViewHolder.actorPicture.getDrawable());
        final Drawable drawable = Drawable.createFromPath(actor.getProfile_path());
        /* assertThat(drawable, is(shadowDrawable.getCreatedFromResId())); */

        assertThat(movieActorsViewHolder.name.getText(), is(actor.getName()));
        assertThat(movieActorsViewHolder.character.getText(), is(actor.getCharacter()));
    }

    private Actor getActor() {
        return new Actor(
                "https://image.tmdb.org/t/p/w92/dRLSoufWtc16F5fliK4ECIVs56p.jpg",
                "Robert Danny Junior",
                "Iron Man");
    }
}