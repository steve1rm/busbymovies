package me.androidbox.busbymovies.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by steve on 9/17/17.
 */

public class BlurTransformation extends BitmapTransformation {
    private RenderScript renderScript;

    public BlurTransformation(final Context context) {
        super();
        renderScript = RenderScript.create(context);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        final Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);

        /* Allocate memory for RenderScript to work with */
        final Allocation input = Allocation.createFromBitmap(
                renderScript,
                blurredBitmap,
                Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SHARED);

        final Allocation output = Allocation.createTyped(renderScript, input.getType());

        /* Load up an instance of the specific script that we want to use */
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        script.setInput(input);

        /* Set the blur radius */
        script.setRadius(50);

        /* Start the script Intrinisic Blur */
        script.forEach(output);

        /* Copy the output to the blurred bitmap */
        output.copyTo(blurredBitmap);

        return blurredBitmap;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update("blur transformation".getBytes());
    }
}
