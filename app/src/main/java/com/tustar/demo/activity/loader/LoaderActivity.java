package com.tustar.demo.activity.loader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.tustar.demo.activity.BaseActivity;

public class LoaderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentById(android.R.id.content);
        if (null == fragment) {
            AppListFragment listFragment = new AppListFragment();
            ft.add(android.R.id.content, listFragment);
            ft.commit();
        }
    }

    public static class  AppListFragment extends ListFragment {
        private static final String TAG = "AppListFragment";
    }
}
