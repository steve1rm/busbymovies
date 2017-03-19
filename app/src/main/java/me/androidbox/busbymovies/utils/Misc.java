package me.androidbox.busbymovies.utils;

import android.content.res.Resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by steve on 3/4/17.
 */

public final class Misc {

    /* Format date */
    public static String formatDate(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        final Date tempDate;

        try {
            tempDate = simpleDateFormat.parse(date);
            simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            Timber.d("%s", simpleDateFormat.format(tempDate));
        }
        catch(ParseException e) {
            Timber.e(e, e.getMessage());
            return date;
        }

        return simpleDateFormat.format(tempDate);
    }

    /* Get the height of the status bar as every device is different to set the marginTop for the toolbar */
    public static int getStatusBarHeight(Resources resources) {
        int height = 24;
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        Timber.d("getStatusBarHeight: resourceId %d", resourceId);
        if(resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId);
            Timber.d("getStatusBarHeight: height %d", height);
        }

        return height;
    }
}
