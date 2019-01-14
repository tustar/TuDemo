package com.tustar.demo

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import com.tustar.common.util.Logger
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.ui.account.AccountActivity
import com.tustar.demo.ui.anim.viewanim.HideActionBarActivity
import com.tustar.demo.ui.anim.viewanim.ViewAnimActivity
import com.tustar.demo.ui.customwidget.CustomWidgetActivity
import com.tustar.demo.ui.customwidget.HistoryActivity
import com.tustar.demo.ui.deskclock.DeskClockActivity
import com.tustar.demo.ui.deskclock.SubScaleViewActivity
import com.tustar.demo.ui.dragsortlistview.DragSortListViewActivity
import com.tustar.demo.ui.dragview.DragViewActivity
import com.tustar.demo.ui.fl.FlMainActivity
import com.tustar.demo.ui.fm.FmMainActivity
import com.tustar.demo.ui.jet.JetMainActivity
import com.tustar.demo.ui.loader.LoaderActivity
import com.tustar.demo.ui.provider.ProviderActivity
import com.tustar.demo.ui.qyz.QyzMainActivity
import com.tustar.demo.ui.recycler.RecyclerViewActivity
import com.tustar.demo.ui.ryg.RygMainActivity
import com.tustar.demo.ui.scroller.ScrollerActivity
import com.tustar.demo.ui.service.ServiceActivity
import com.tustar.demo.ui.signal.FloatWindowActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener,
        SimpleListItem1Adapter.OnItemClickListener {

    companion object {

        private val TAG = MainActivity::class.java.simpleName
        private val sClassList = ArrayList<Any>()

        // List
        init {
            with(sClassList) {
                this += RecyclerViewActivity::class.java
                this += CustomWidgetActivity::class.java
                this += FloatWindowActivity::class.java
                this += DragSortListViewActivity::class.java
                this += DragViewActivity::class.java
                this += ViewAnimActivity::class.java
                this += LoaderActivity::class.java
                this += ServiceActivity::class.java
                this += HideActionBarActivity::class.java
                this += AccountActivity::class.java
                this += ProviderActivity::class.java
                this += DeskClockActivity::class.java
                this += ScrollerActivity::class.java
                this += HistoryActivity::class.java
                this += SubScaleViewActivity::class.java
                //
                this += FmMainActivity::class.java
                this += QyzMainActivity::class.java
                this += RygMainActivity::class.java
                //
                this += JetMainActivity::class.java
                this += FlMainActivity::class.java
                reverse()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate :: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            com.google.android.material.snackbar.Snackbar.make(view, "Replace with your own action", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
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
        content_main_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
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
        when (clazz) {
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
        var notification = NotificationCompat.Builder(MainActivity@ this)
                .setContentTitle("测试通知")
                .setContentText("测试通知内容")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentIntent(PendingIntent.getService(MainActivity@ this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build()
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify((System.currentTimeMillis() / 1000L).toInt(), notification)
    }

    data class ItemClickAction(var action: String,
                               var category: String,
                               val data: Uri)
}
