package com.github.diaglyonok.contactsapi.ui.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import com.squareup.picasso.Transformation;

/**
 * Created by diaglyonok on 03.03.18.
 * https://github.com/Diaglyonok
 * diaglyonok@yandex.ru
 */

//Class for Cropping image as Star or as Heart
public class MaskTransformation implements Transformation {

    private static Paint mMaskingPaint = new Paint();
    private Context mContext;
    private int mMaskId;

    static {
        mMaskingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    //Constructor
    MaskTransformation(Context context, int maskId) {
        mContext = context.getApplicationContext(); // Init context
        mMaskId = maskId;   // R.drawable.heart or R.drawable.star
    }

    //Called from picasso objects, source is image that need to be cropped
    @Override public Bitmap transform(Bitmap source) {
        int width = source.getWidth();      //Width of source
        int height = source.getHeight();    //Height of source

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Drawable mask = getMaskDrawable(mContext, mMaskId); //Init mask from resources

        //Using mask
        Canvas canvas = new Canvas(result);
        mask.setBounds(0, 0, width, height);
        mask.draw(canvas);
        canvas.drawBitmap(source, 0, 0, mMaskingPaint);

        source.recycle();

        //Return bitmap of cropped image
        return result;
    }

    @Override public String key() {
        return "MaskTransformation(maskId=" + mContext.getResources().getResourceEntryName(mMaskId)
                + ")";
    }

    //Method returned mask as Drawable
    private Drawable getMaskDrawable(Context context, int maskId) {
        Drawable drawable = ContextCompat.getDrawable(context, maskId);

        if (drawable == null) {
            throw new IllegalArgumentException("maskId is invalid");
        }

        return drawable;
    }
}
