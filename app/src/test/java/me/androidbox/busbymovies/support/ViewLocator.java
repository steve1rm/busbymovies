package me.androidbox.busbymovies.support;

import android.app.Activity;
import androidx.annotation.IdRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by steve on 3/28/17.
 */

public final class ViewLocator {

    public static View getView(Activity activity, int viewId) {
        return activity.findViewById(viewId);
    }

    public static FloatingActionButton getFab(Activity activity, int viewId) {
        return (FloatingActionButton)activity.findViewById(viewId);
    }

    public static RecyclerView getRecyclerView(Activity activity, int viewId) {
        return (RecyclerView)activity.findViewById(viewId);
    }

    public static ProgressBar getProgressBar(final Activity activity, final @IdRes int viewId) {
        return (ProgressBar)activity.findViewById(viewId);
    }

    public static TextView getTextView(final Activity activity, final @IdRes int viewId) {
        return (TextView)activity.findViewById(viewId);
    }
}
