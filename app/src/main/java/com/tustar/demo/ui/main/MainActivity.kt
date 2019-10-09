package com.tustar.demo.ui.main

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.tustar.common.util.Logger
import com.tustar.common.widget.Decoration
import com.tustar.demo.R
import com.tustar.demo.ui.calculator.HistoryActivity
import com.tustar.demo.ui.deskclock.DeskClockActivity
import com.tustar.demo.ui.fl.FlMainActivity
import com.tustar.demo.ui.fm.FmMainActivity
import com.tustar.demo.ui.jet.pagingroom.BookActivity
import com.tustar.demo.ui.qyz.QyzMainActivity
import com.tustar.demo.ui.ryg.RygMainActivity
import com.tustar.demo.ui.signal.SignalActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        MainAdapter.OnItemClickListener {

    companion object {

        private val TAG = MainActivity::class.java.simpleName
        //
        private val BOOK_DEMOS = listOf(
                ContentItem(FlMainActivity::class.java, R.string.fl_main_title),
                ContentItem(RygMainActivity::class.java, R.string.ryg_main_title),
                ContentItem(QyzMainActivity::class.java, R.string.qyz_main_title)
        )
        private val BOOK_SECTION = SectionItem(R.string.section_books, BOOK_DEMOS)
        //
        private val PROJECT_DEMOS = listOf(
                ContentItem(FmMainActivity::class.java, R.string.fm_main_title),
                ContentItem(DeskClockActivity::class.java, R.string.deskclock_title),
                ContentItem(HistoryActivity::class.java, R.string.calculator_title),
                ContentItem(SignalActivity::class.java, R.string.rf_signal_title)
        )
        private val PROJECT_SECTION = SectionItem(R.string.section_project, PROJECT_DEMOS)
        //
        private val JETPACK_DEMOS = listOf(
                ContentItem(BookActivity::class.java, R.string.jet_main_title)
        )
        private val JETPACK_SECTION = SectionItem(R.string.section_jetpack, JETPACK_DEMOS)
        //
        val ITEMS = arrayListOf<MainItem>().apply {
            add(BOOK_SECTION)
            addAll(BOOK_DEMOS)
            //
            add(PROJECT_SECTION)
            addAll(PROJECT_DEMOS)
            //
            add(JETPACK_SECTION)
            addAll(JETPACK_DEMOS)
        }

//        .run {
//
//
//        }
//        private val sClassList = arrayOf(
//                RecyclerViewActivity::class.java,
//                CustomWidgetActivity::class.java,
//                DragSortListViewActivity::class.java,
//                DragViewActivity::class.java,
//                ViewAnimActivity::class.java,
//                LoaderActivity::class.java,
//                ServiceActivity::class.java,
//                HideActionBarActivity::class.java,
//                AccountActivity::class.java,
//                ProviderActivity::class.java,
//                ScrollerActivity::class.java,

    }

    private lateinit var mContext: Context
    private var items = mutableListOf<MainItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate :: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
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

        content_main_rv.layoutManager = LinearLayoutManager(this)
        val adapter = MainAdapter(ITEMS, this)
        content_main_rv.adapter = adapter
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

    override fun onItemClick(item: ContentItem) {
        item.startActivity(mContext)
    }

    private fun showProxyNotification() {
        val intent = Intent()
        var notification = NotificationCompat.Builder(mContext)
                .setContentTitle("测试通知")
                .setContentText("测试通知内容")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentIntent(PendingIntent.getService(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build()
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify((System.currentTimeMillis() / 1000L).toInt(), notification)
    }
}
