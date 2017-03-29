package me.androidbox.busbymovies.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by steve on 3/4/17.
 */

public final class Misc {
    /* Private constructor prevents creating object of class */
    private Misc() {}

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

    // heightPixels 1776 density 480 =
    // heightPixels 1184 density 320

    // 592 = 1776 * (160 / 480)
    // 592 = 1184 * (160 / 320)

    // Height 592.000000 Width 384.000000

    public static float getHeightInDp(Resources resources) {
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Timber.d("heightPixels %d density %f", displayMetrics.heightPixels, displayMetrics.density);
        return displayMetrics.heightPixels / displayMetrics.density;
    }

    public static float getWidthInDp(Resources resources) {
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density;
    }
}
