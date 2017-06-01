package com.tustar.demo.module.deskclock;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ViewAware;
import com.tustar.demo.module.deskclock.subscaleview.ImageSource;
import com.tustar.demo.module.deskclock.subscaleview.PhotoView;
import com.tustar.demo.util.Logger;

import java.lang.reflect.Field;

public class PhotoViewAware extends ViewAware {
    private static final String TAG = "PhotoViewAware";

    public PhotoViewAware(PhotoView photoView) {
        super(photoView);
    }

    public PhotoViewAware(PhotoView photoView, boolean checkActualViewSize) {
        super(photoView, checkActualViewSize);
    }

    public int getWidth() {
        int width = super.getWidth();
        if (width <= 0) {
            PhotoView photoView = (PhotoView) this.viewRef.get();
            if (photoView != null) {
                width = getPhotoViewFieldValue(photoView, "mMaxWidth");
            }
        }

        return width;
    }

    public int getHeight() {
        int height = super.getHeight();
        if (height <= 0) {
            PhotoView photoView = (PhotoView) this.viewRef.get();
            if (photoView != null) {
                height = getPhotoViewFieldValue(photoView, "mMaxHeight");
            }
        }

        return height;
    }

    public ViewScaleType getScaleType() {
        return super.getScaleType();
    }

    public PhotoView getWrappedView() {
        return (PhotoView) super.getWrappedView();
    }

    protected void setImageDrawableInto(Drawable drawable, View view) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        ((PhotoView) view).setImage(ImageSource.bitmap(bitmapDrawable.getBitmap()));
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
        }

    }

    protected void setImageBitmapInto(Bitmap bitmap, View view) {
        ((PhotoView) view).setImage(ImageSource.bitmap(bitmap));
    }

    private static int getPhotoViewFieldValue(Object object, String fieldName) {
        int value = 0;

        try {
            Field e = PhotoView.class.getDeclaredField(fieldName);
            e.setAccessible(true);
            int fieldValue = ((Integer) e.get(object)).intValue();
            if (fieldValue > 0 && fieldValue < 2147483647) {
                value = fieldValue;
            }
        } catch (Exception var5) {
            Logger.e(TAG, var5.getMessage());
        }

        return value;
    }
}

