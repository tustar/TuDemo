package com.tustar.demo.ui.loader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.tustar.common.util.Logger;
import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class LoaderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.loader_title);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentById(android.R.id.content);
        if (null == fragment) {
            AppListFragment listFragment = new AppListFragment();
            ft.add(android.R.id.content, listFragment);
            ft.commit();
        }
    }

    public static class AppListFragment extends ListFragment
            implements LoaderManager.LoaderCallbacks<List<AppEntry>>, AdapterView.OnItemClickListener {
        private static final String TAG = "AppListFragment";

        // We use a custom ArrayAdapter to bind application info to the ListView.
        private AppListAdapter mAdapter;

        // The Loader's id (this id is specific to the ListFragment's LoaderManager)
        private static final int LOADER_ID = 1;

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setHasOptionsMenu(true);

            mAdapter = new AppListAdapter(getActivity());
            setEmptyText("No Applications");
            setListAdapter(mAdapter);
            setListShown(false);
            getListView().setOnItemClickListener(this);

            if (null == getLoaderManager().getLoader(LOADER_ID)) {
                Logger.d(TAG, "onActivityCreated: Initializing the new Loader");
            } else {
                Logger.d(TAG, "onActivityCreated: Reconnecting with existing Loader " + LOADER_ID);
            }

            // Initialize a Loader with id '1'. If the Loader with this id already
            // exists, then the LoaderManager will reuse the existing Loader.
            getLoaderManager().initLoader(LOADER_ID, null, this);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.activity_loader, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            Logger.i(TAG, "onOptionsItemSelected: ");
            switch (item.getItemId()) {
                case R.id.menu_configure_locale:
                    configureLocale();
                    return true;
            }

            return false;
        }

        private void configureLocale() {
            Logger.i(TAG, "configureLocale: ");
            Loader<AppEntry> loader = getLoaderManager().getLoader(LOADER_ID);
            Logger.d(TAG, "configureLocale: loader = " + loader);
            if (null != loader) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }
        }

        @Override
        public Loader<List<AppEntry>> onCreateLoader(int id, Bundle args) {
            Logger.i(TAG, "onCreateLoader: id = " + id + ", args = " + args);
            return new AppListLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<AppEntry>> loader, List<AppEntry> data) {
            Logger.i(TAG, "onLoadFinished: loader = " + loader + ", DATAS = " + data);
            mAdapter.setData(data);
            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<AppEntry>> loader) {
            Logger.i(TAG, "onLoaderReset: loader = " + loader);
            mAdapter.setData(null);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Logger.i(TAG, "onItemClick :: " + "parent = [" + parent + "], " +
                    "view = [" + view + "], position = [" + position + "], id = [" + id + "]");
            AppEntry entry = mAdapter.getItem(position);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(entry.getApkFile()));
            startActivity(intent);
        }
    }
}
