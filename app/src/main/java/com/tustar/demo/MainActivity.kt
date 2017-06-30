package com.tustar.demo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.common.CommonDefine
import com.tustar.demo.module.account.AccountActivity
import com.tustar.demo.module.anim.viewanim.HideActionBarActivity
import com.tustar.demo.module.anim.viewanim.ViewAnimActivity
import com.tustar.demo.module.customwidget.CustomWidgetActivity
import com.tustar.demo.module.customwidget.HistoryActivity
import com.tustar.demo.module.deskclock.DeskClockActivity
import com.tustar.demo.module.deskclock.SubScaleViewActivity
import com.tustar.demo.module.dragsortlistview.DragSortListViewActivity
import com.tustar.demo.module.dragview.DragViewActivity
import com.tustar.demo.module.filemanager.AndroidGifDrawableActivity
import com.tustar.demo.module.filemanager.GifPlayerActivity
import com.tustar.demo.module.loaderdemo.LoaderActivity
import com.tustar.demo.module.providerdemo.ProviderActivity
import com.tustar.demo.module.qyz.*
import com.tustar.demo.module.recyclerviewdemo.RecyclerViewActivity
import com.tustar.demo.module.ryg.RygArtMainActivity
import com.tustar.demo.module.scrollerdemo.ScrollerActivity
import com.tustar.demo.module.servicedemo.ServiceActivity
import com.tustar.demo.module.signal.FloatWindowActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        SimpleListItem1Adapter.OnItemClickListener {

    private val TAG = MainActivity::class.java.simpleName
    private val sClassList = ArrayList<Class<*>>()

    // List
    init {
        sClassList.add(RecyclerViewActivity::class.java)
        sClassList.add(RotationActivity::class.java)
        sClassList.add(PaletteActivity::class.java)
        sClassList.add(TintingActivity::class.java)
        sClassList.add(ElevationActivity::class.java)
        sClassList.add(ClippingActivity::class.java)
        sClassList.add(AnimMainActivity::class.java)
        sClassList.add(CustomWidgetActivity::class.java)
        sClassList.add(FloatWindowActivity::class.java)
        sClassList.add(DragSortListViewActivity::class.java)
        sClassList.add(SurfaceViewActivity::class.java)
        sClassList.add(DragViewActivity::class.java)
        sClassList.add(ViewAnimActivity::class.java)
        sClassList.add(LoaderActivity::class.java)
        sClassList.add(SvgActivity::class.java)
        sClassList.add(ServiceActivity::class.java)
        sClassList.add(HideActionBarActivity::class.java)
        sClassList.add(AccountActivity::class.java)
        sClassList.add(ProviderActivity::class.java)
        sClassList.add(DeskClockActivity::class.java)
        sClassList.add(HistoryActivity::class.java)
        sClassList.add(ScrollerActivity::class.java)
        sClassList.add(FlexibleListViewActivity::class.java)
        sClassList.add(OverScrollGridViewActivity::class.java)
        sClassList.add(SubScaleViewActivity::class.java)
        sClassList.add(RygArtMainActivity::class.java)
        sClassList.add(GifPlayerActivity::class.java)
        sClassList.add(AndroidGifDrawableActivity::class.java)
        Collections.reverse(sClassList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate :: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // Init view
        initView()
    }

    override fun onPause() {
        Logger.i(TAG, "onPause :: ")
        super.onPause()
    }

    override fun onStop() {
        Logger.i(TAG, "onStop :: ")
        super.onStop()
    }

    private fun initView() {

        val contents = resources.getStringArray(R.array.content_data)
        content_main_rv.layoutManager = LinearLayoutManager(this)
        var adapter = SimpleListItem1Adapter(contents.filterNotNull().asReversed())
        content_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        content_main_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
            }
            R.id.nav_gallery -> {
            }
            R.id.nav_slideshow -> {
            }
            R.id.nav_manage -> {
            }
            R.id.nav_share -> {
            }
            R.id.nav_send -> {
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent()
        val clazz = sClassList[position]
        if (null != clazz) {
            intent.setClass(this, clazz)
            if (clazz == GifPlayerActivity::class.java) {
                intent.putExtra(GifPlayerActivity.GIF_FILE_PATH,
                        CommonDefine.TEST_GIF)
            }
            startActivity(intent)
        }
    }
}
