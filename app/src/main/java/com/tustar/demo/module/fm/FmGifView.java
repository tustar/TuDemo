package com.tustar.demo.module.fm;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.tustar.demo.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tustar on 17-6-28.
 */

public class FmGifView extends View {

    private static final String TAG = "GifView";
    private static final int DEFAULT_MOVIE_VIEW_DURATION = 1000;
    private static final float M_SCALE_RATIO = 1.0f;
    private static final int M_GIF_WIDTH = 190;

    private Movie mMovie;
    private long mMovieStart;

    private int mWidth;
    private int mHeight;
    private int mGifWidth;
    private int mGifHeight;
    private int mGifDuration;

    // Listener
    private DecodeListener mDecodeListener;

    public FmGifView(Context context) {
        super(context);
        init();
    }

    public FmGifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FmGifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FmGifView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    public void onDraw(Canvas canvas) {
        if (mMovie == null) {
            return;
        }

        long now = SystemClock.uptimeMillis();
        if (mMovieStart == 0) { // first time
            mMovieStart = now;
        }

        if (mGifDuration == 0) {
            mGifDuration = DEFAULT_MOVIE_VIEW_DURATION;
        }
        int relTime = (int) ((now - mMovieStart) % mGifDuration);
        mMovie.setTime(relTime);

        float scaleRate = M_SCALE_RATIO;
        float wRate;
        float hRate;

        if (mGifWidth > M_GIF_WIDTH) {
            wRate = mWidth * M_SCALE_RATIO / mGifWidth;
            hRate = mHeight * M_SCALE_RATIO / mGifHeight;
        } else {
            scaleRate = 0.8f;
            wRate = mWidth * scaleRate / mGifWidth;
            hRate = mHeight * scaleRate / mGifHeight;
        }

        if (wRate >= hRate) {
            scaleRate = hRate;
        } else {
            scaleRate = wRate;
        }

        float scaleW = scaleRate * mGifWidth;
        float scaleH = scaleRate * mGifHeight;

        float dx = (mWidth * M_SCALE_RATIO - scaleW) / 2.0f;
        float dy = (mHeight * M_SCALE_RATIO - scaleH) / 2.0f;


        canvas.translate(dx, dy);
        canvas.scale(scaleRate, scaleRate);
        mMovie.draw(canvas, 0, 0);
        invalidate();
    }

    public void setGifSource(final String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return;
        }

        long start = System.currentTimeMillis();
        mMovie = Movie.decodeFile(filePath);
        long end = System.currentTimeMillis();
        Toast.makeText(getContext(), "" + (end - start) + "ms", Toast.LENGTH_LONG).show();

        if (mDecodeListener != null) {
            mDecodeListener.onDecodeEnd(mMovie != null);
        }
        if (mMovie != null) {
            mGifWidth = mMovie.width();
            mGifHeight = mMovie.height();
            mGifDuration = mMovie.duration();
        }

//        new AsyncTask<Void, Void, Movie>() {
//
//            @Override
//            protected void onPreExecute() {
//                if (mDecodeListener != null) {
//                    mDecodeListener.onDecodeStart();
//                }
//            }
//
//            @Override
//            protected Movie doInBackground(Void... params) {
//                return Movie.decodeFile(filePath);
//            }
//
//            @Override
//            protected void onPostExecute(Movie movie) {
//                mMovie = movie;
//                if (mDecodeListener != null) {
//                    mDecodeListener.onDecodeEnd(mMovie != null);
//                }
//                if (mMovie != null) {
//                    mGifWidth = mMovie.width();
//                    mGifHeight = mMovie.height();
//                    mGifDuration = mMovie.duration();
//                }
//            }
//        }.execute();
    }

    public void setGifSource(InputStream is) {

        if (mDecodeListener != null) {
            mDecodeListener.onDecodeStart();
        }

        mMovie = Movie.decodeStream(is);

        if (mDecodeListener != null) {
            mDecodeListener.onDecodeEnd(mMovie != null);
        }
        if (mMovie != null) {
            mGifWidth = mMovie.width();
            mGifHeight = mMovie.height();
            mGifDuration = mMovie.duration();
        }
    }


    public void setGifSourceUri(final String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        if (mDecodeListener != null) {
            mDecodeListener.onDecodeStart();
        }

        Uri uri = FileUtils.getShareUri(file);
        try (InputStream is = getContext().getContentResolver().openInputStream(uri)) {
            if (is != null) {
                mMovie = Movie.decodeStream(is);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mDecodeListener != null) {
            mDecodeListener.onDecodeEnd(mMovie != null);
        }

        if (mMovie != null) {
            mGifWidth = mMovie.width();
            mGifHeight = mMovie.height();
            mGifDuration = mMovie.duration();

            if (mGifWidth == 0 && mGifHeight == 0) {
                String[] projection = new String[]{
                        MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT
                };
                try (Cursor cursor = getContext().getContentResolver()
                        .query(uri, projection, null, null, null)) {
                    if (cursor != null && cursor.moveToNext()) {
                        mGifWidth = cursor.getInt(0);
                        mGifHeight = cursor.getInt(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setDecodeListener(DecodeListener decodeListener) {
        mDecodeListener = decodeListener;
    }

    public interface DecodeListener {
        void onDecodeStart();

        void onDecodeEnd(boolean result);
    }
}
