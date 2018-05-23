package com.tustar.demo

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.module.account.AccountActivity
import com.tustar.demo.module.anim.viewanim.HideActionBarActivity
import com.tustar.demo.module.anim.viewanim.ViewAnimActivity
import com.tustar.demo.module.customwidget.CustomWidgetActivity
import com.tustar.demo.module.customwidget.HistoryActivity
import com.tustar.demo.module.deskclock.DeskClockActivity
import com.tustar.demo.module.deskclock.SubScaleViewActivity
import com.tustar.demo.module.dragsortlistview.DragSortListViewActivity
import com.tustar.demo.module.dragview.DragViewActivity
import com.tustar.demo.module.fm.FmMainActivity
import com.tustar.demo.module.loader.LoaderActivity
import com.tustar.demo.module.provider.ProviderActivity
import com.tustar.demo.module.qyz.QyzMainActivity
import com.tustar.demo.module.recycler.RecyclerViewActivity
import com.tustar.demo.module.ryg.RygMainActivity
import com.tustar.demo.module.scroller.ScrollerActivity
import com.tustar.demo.module.service.ServiceActivity
import com.tustar.demo.module.signal.FloatWindowActivity
import com.tustar.common.util.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        SimpleListItem1Adapter.OnItemClickListener {

    companion object {

        private val TAG = MainActivity::class.java.simpleName
        private val sClassList = ArrayList<Any>()

        // List
        init {
            sClassList += RecyclerViewActivity::class.java
            sClassList += CustomWidgetActivity::class.java
            sClassList += FloatWindowActivity::class.java
            sClassList += DragSortListViewActivity::class.java
            sClassList += DragViewActivity::class.java
            sClassList += ViewAnimActivity::class.java
            sClassList += LoaderActivity::class.java
            sClassList += ServiceActivity::class.java
            sClassList += HideActionBarActivity::class.java
            sClassList += AccountActivity::class.java
            sClassList += ProviderActivity::class.java
            sClassList += DeskClockActivity::class.java
            sClassList += ScrollerActivity::class.java
            sClassList += HistoryActivity::class.java
            sClassList += SubScaleViewActivity::class.java
            //
            sClassList += FmMainActivity::class.java
            sClassList += QyzMainActivity::class.java
            sClassList += RygMainActivity::class.java
            //
            sClassList += ItemClickAction(Intent.ACTION_VIEW, Intent.CATEGORY_BROWSABLE,
                    Uri.parse("acc://tustar.com/main"))
            sClassList.reverse()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate :: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ushare_activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // Init view
        initView()

        showProxyNotification()
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
        content_main_rv.addItemDecoration(com.tustar.common.widget.Decoration(this, com.tustar.common.widget.Decoration.VERTICAL))
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
        when(clazz) {
            is Class<*> -> intent.setClass(this, clazz)
            is ItemClickAction -> {
                intent.action = clazz.action
                intent.addCategory(clazz.category)
                intent.data = clazz.data

            }
            else -> intent.setClass(this, clazz as Class<*>?)

        }

        startActivity(intent)
    }

    fun showProxyNotification() {
        val intent = Intent()
        var notification = NotificationCompat.Builder(MainActivity@this)
                .setContentTitle("测试通知")
                .setContentText("测试通知内容")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentIntent(PendingIntent.getService(MainActivity@this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build()
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify((System.currentTimeMillis() / 1000L).toInt(), notification)
    }

    data class ItemClickAction(var action: String,
                               var category: String,
                               val data: Uri)
}
