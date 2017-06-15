package com.tustar.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tustar.demo.module.account.AccountActivity;
import com.tustar.demo.module.anim.viewanim.HideActionBarActivity;
import com.tustar.demo.module.anim.viewanim.ViewAnimActivity;
import com.tustar.demo.module.customwidget.CustomWidgetActivity;
import com.tustar.demo.module.customwidget.HistoryActivity;
import com.tustar.demo.module.deskclock.DeskClockActivity;
import com.tustar.demo.module.deskclock.SubScaleViewActivity;
import com.tustar.demo.module.dragsortlistview.DragSortListViewActivity;
import com.tustar.demo.module.dragview.DragViewActivity;
import com.tustar.demo.module.loaderdemo.LoaderActivity;
import com.tustar.demo.module.providerdemo.ProviderActivity;
import com.tustar.demo.module.qyz.AnimMainActivity;
import com.tustar.demo.module.qyz.ClippingActivity;
import com.tustar.demo.module.qyz.ElevationActivity;
import com.tustar.demo.module.qyz.FlexibleListViewActivity;
import com.tustar.demo.module.qyz.OverScrollGridViewActivity;
import com.tustar.demo.module.qyz.PaletteActivity;
import com.tustar.demo.module.qyz.RotationActivity;
import com.tustar.demo.module.qyz.SurfaceViewActivity;
import com.tustar.demo.module.qyz.SvgActivity;
import com.tustar.demo.module.qyz.TintingActivity;
import com.tustar.demo.module.recyclerviewdemo.RecyclerViewActivity;
import com.tustar.demo.module.ryg.ch2.RygMainActivity;
import com.tustar.demo.module.scrollerdemo.ScrollerActivity;
import com.tustar.demo.module.servicedemo.ServiceActivity;
import com.tustar.demo.module.signal.FloatWindowActivity;
import com.tustar.demo.util.ArrayUtils;
import com.tustar.demo.util.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static ArrayList<Class> sClassList = new ArrayList<>();

    // List
    static {
        sClassList.add(RecyclerViewActivity.class);
        sClassList.add(RotationActivity.class);
        sClassList.add(PaletteActivity.class);
        sClassList.add(TintingActivity.class);
        sClassList.add(ElevationActivity.class);
        sClassList.add(ClippingActivity.class);
        sClassList.add(AnimMainActivity.class);
        sClassList.add(CustomWidgetActivity.class);
        sClassList.add(FloatWindowActivity.class);
        sClassList.add(DragSortListViewActivity.class);
        sClassList.add(SurfaceViewActivity.class);
        sClassList.add(DragViewActivity.class);
        sClassList.add(ViewAnimActivity.class);
        sClassList.add(LoaderActivity.class);
        sClassList.add(SvgActivity.class);
        sClassList.add(ServiceActivity.class);
        sClassList.add(HideActionBarActivity.class);
        sClassList.add(AccountActivity.class);
        sClassList.add(ProviderActivity.class);
        sClassList.add(DeskClockActivity.class);
        sClassList.add(HistoryActivity.class);
        sClassList.add(ScrollerActivity.class);
        sClassList.add(FlexibleListViewActivity.class);
        sClassList.add(OverScrollGridViewActivity.class);
        sClassList.add(SubScaleViewActivity.class);
        sClassList.add(RygMainActivity.class);
        Collections.reverse(sClassList);
    }

    // Listview
    private ListView mContentMainListView;
    private ArrayAdapter<String> mAdatpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i(TAG, "onCreate :: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Init view
        initView();
    }

    @Override
    protected void onPause() {
        Logger.i(TAG, "onPause :: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logger.i(TAG, "onStop :: ");
        super.onStop();
    }

    private void initView() {

        mContentMainListView = (ListView) findViewById(R.id.content_main_listview);
        String[] contents = getResources().getStringArray(R.array.content_data);
        ArrayUtils.reverse(contents);
        mAdatpter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contents);
        mContentMainListView.setAdapter(mAdatpter);
        mContentMainListView.setOnItemClickListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        Class<?> clazz = sClassList.get(position);
        if (null != clazz) {
            intent.setClass(this, clazz);
            startActivity(intent);
        }
    }
}
