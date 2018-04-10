package com.tustar.demo.module.deskclock;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.module.deskclock.subscaleview.ImageSource;
import com.tustar.demo.module.deskclock.subscaleview.PhotoView;
import com.tustar.common.util.Logger;
import com.tustar.common.util.ToastUtils;

import java.io.File;

public class SubScaleViewActivity extends BaseActivity {

    private static final String TAG = "SubScaleViewActivity";
    private PhotoView mPhotoView;

    /**
     * 最大缓存图片张数
     */
    private static final int MAX_MEMORY_CACHE_SIZE = 50 * 1024 * 1024;
    private static final int DEFAULT_MAX_BITMAP_DIMENSION = 2048;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i(TAG, "onCreate :: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscale_view);
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .threadPoolSize(3)
                .memoryCacheSize(MAX_MEMORY_CACHE_SIZE)
                .build();
        imageLoader.init(config);

        mPhotoView = (PhotoView) findViewById(R.id.photo_view);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // resource or drawable
                .showImageForEmptyUri(R.mipmap.ic_launcher) // resource or drawable
                .showImageOnFail(R.mipmap.ic_launcher) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .cacheInMemory(true) // default
                .cacheOnDisk(false) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .build();
//        String uri = "assets://weibo_long_image.png";
        String uri = "file:///storage/emulated/0/Screenshot_2017-05-24-15-13-56-1692626583.png";
        if (!new File(uri).exists()) {
            ToastUtils.showLong(this, R.string.image_no_exist);
            return;
        }
        ImageSize targetSize = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        String pathName = "/storage/emulated/0/Screenshot_2017-05-24-15-13-56-1692626583.png";
        if (!new File(pathName).exists()) {
            ToastUtils.showLong(this, R.string.image_no_exist);
            return;
        }
        BitmapFactory.decodeFile(pathName, opts);
        int imageWidth = opts.outWidth;
        int imageHeight = opts.outHeight;
        Logger.d(TAG, "onCreate :: (" + imageWidth + ", " + imageHeight + ")");
        if (imageWidth > DEFAULT_MAX_BITMAP_DIMENSION || imageHeight > DEFAULT_MAX_BITMAP_DIMENSION) {
            mPhotoView.setImage(ImageSource.uri(uri));
        } else {
            imageLoader.displayImage(uri, new PhotoViewAware(mPhotoView), options,
                    targetSize, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            Logger.d(TAG, "onLoadingComplete :: (" + loadedImage.getWidth() + ", "
                                    + loadedImage.getHeight() + ")");

                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    }, null);
        }
    }

    @Override
    protected void onStart() {
        Logger.i(TAG, "onStart :: ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Logger.i(TAG, "onResume :: ");
        super.onResume();
        imageLoader.resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        imageLoader.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageLoader.destroy();
    }
}
