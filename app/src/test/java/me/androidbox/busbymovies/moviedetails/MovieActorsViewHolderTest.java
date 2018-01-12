package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.shadows.ShadowApplication;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Actor;
import support.BaseRobolectricTestRunner;
import me.androidbox.busbymovies.utils.ImageLoader;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 9/16/17.
 */
public class MovieActorsViewHolderTest extends BaseRobolectricTestRunner {
    private MovieActorsViewHolder movieActorsViewHolder;

    @Before
    public void setup() {
        final Context context = ShadowApplication.getInstance().getApplicationContext();
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.movie_actors_item, new LinearLayout(context));

        final ImageLoader imageLoader = Mockito.mock(ImageLoader.class);

        movieActorsViewHolder = new MovieActorsViewHolder(view, imageLoader);
    }

    @Test
    public void testShouldPopulateActorWithValidData() {
        final Actor actor = getActor();

        movieActorsViewHolder.populateActor(actor);

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


/*
        final ShadowDrawable shadowDrawable = Shadows.shadowOf(movieActorsViewHolder.actorPicture.getDrawable());
        final Drawable drawable = Drawable.createFromPath(actor.getProfile_path());
*/
        /* assertThat(drawable, is(shadowDrawable.getCreatedFromResId())); */
