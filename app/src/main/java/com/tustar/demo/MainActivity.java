package com.tustar.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tustar.demo.floatwindow.FloatWindowActivity;
import com.tustar.demo.recyclerview.RecyclerViewActivity;
import com.tustar.demo.rotation.RotationActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    // Listview
    private ListView mContentMainListView;
    private ArrayAdapter<String> mAdatpter;

    // List
    private static final int INDEX_RECYCLER_VIEW = 0;
    private static final int INDEX_ROTATION_SCREEN = 1;
    private static final int INDEX_FLOAT_WINDOW = 2;
    private static final int INDEX_DSLV = 3;
    private static final int INDEX_CUSTOM_SURFACEVIEW = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    private void initView() {

        mContentMainListView = (ListView)findViewById(R.id.content_main_listview);
        mAdatpter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.content_data));
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
        switch (position) {
            case INDEX_RECYCLER_VIEW:
                Intent recycleIntent = new Intent();
                recycleIntent.setClass(this, RecyclerViewActivity.class);
                startActivity(recycleIntent);
                break;
            case INDEX_ROTATION_SCREEN:
                Intent rotationIntent = new Intent();
                rotationIntent.setClass(this, RotationActivity.class);
                startActivity(rotationIntent);
                break;
            case INDEX_FLOAT_WINDOW:
                Intent floatIntent = new Intent();
                floatIntent.setClass(this, FloatWindowActivity.class);
                startActivity(floatIntent);
                break;
            case INDEX_DSLV:
                Intent dslvIntent = new Intent();
                dslvIntent.setClass(this, DragSortListViewActivity.class);
                startActivity(dslvIntent);
                break;
            case INDEX_CUSTOM_SURFACEVIEW:
                Intent surfaceIntent = new Intent();
                surfaceIntent.setClass(this, SurfaceViewActivity.class);
                startActivity(surfaceIntent);
            default:
                break;
        }
    }
}
