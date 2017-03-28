package me.androidbox.busbymovies.support;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by steve on 3/28/17.
 */

public class ViewLocator {

    public static View getView(Activity activity, int viewId) {
        return activity.findViewById(viewId);
    }

    public static FloatingActionButton getFab(Activity activity, int viewId) {
        return (FloatingActionButton)activity.findViewById(viewId);
    }

    public static RecyclerView getRecyclerView(Activity activity, int viewId) {
        return (RecyclerView)activity.findViewById(viewId);
    }
}
