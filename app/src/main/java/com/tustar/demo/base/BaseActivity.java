package com.tustar.demo.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.tustar.util.Logger;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by tustar on 4/10/16.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    protected static final int REQUEST_EXTERNAL_STORAGE = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestStoragePermissions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Logger.d(TAG, "onOptionsItemSelected :: id = " + id);
        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean hasStoragePermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (PackageManager.PERMISSION_GRANTED ==
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                PackageManager.PERMISSION_GRANTED ==
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // You have permission
            return true;
        }

        return false;
    }

    private void requestStoragePermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (PackageManager.PERMISSION_GRANTED ==
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                PackageManager.PERMISSION_GRANTED ==
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // You have permission
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
    }
}
