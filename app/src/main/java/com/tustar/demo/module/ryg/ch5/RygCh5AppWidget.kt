package com.tustar.demo.module.ryg.ch5

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.SystemClock
import android.text.TextUtils
import android.widget.RemoteViews
import com.tustar.common.util.Logger
import com.tustar.common.util.ToastUtils
import com.tustar.demo.R

/**
 * Implementation of App Widget functionality.
 */
class RygCh5AppWidget : AppWidgetProvider() {

    companion object {

        private val TAG = "RygCh5AppWidget"
        private val ACTION_CLICK = "com.tustar.demo.module.ryg.ch5.action.CLICK"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val views = RemoteViews(context.packageName, R.layout.ryg_ch5_app_widget)
            // "窗口小部件"点击事件发送的Intent广播
            var intent = Intent(ACTION_CLICK)
            var pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            views.setOnClickPendingIntent(R.id.ryg_ch5_image, pendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        Logger.i(TAG, "onReceive ::")
        super.onReceive(context, intent)
        if (intent == null) {
            return
        }

        val action = intent.action
        if (TextUtils.isEmpty(action)) {
            return
        }

        when (action) {
            ACTION_CLICK -> {
                ToastUtils.showLong(context, "Click it")
                Thread({
                    var srcBmp = BitmapFactory.decodeResource(context.resources,
                            R.drawable.ryg_ch5_icon)
                    var awm = AppWidgetManager.getInstance(context)
                    for (i in 1..37) {
                        var degree = (i * 10) % 360.0f
                        var remoteViews = RemoteViews(context.packageName,
                                R.layout.ryg_ch5_app_widget)
                        remoteViews.setImageViewBitmap(R.id.ryg_ch5_image, rotateBitmap(context,
                                srcBmp, degree))
                        var intent = Intent(ACTION_CLICK)
                        var pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                        remoteViews.setOnClickPendingIntent(R.id.ryg_ch5_image, pendingIntent)
                        awm.updateAppWidget(ComponentName(context, RygCh5AppWidget::class.java),
                                remoteViews)
                        SystemClock.sleep(30)
                    }
                }).start()
            }
            else -> {

            }
        }
    }

    /**
     * 每次窗口小部件被点击更新都调用一次该方法
     */
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {
        Logger.i(TAG, "onUpdate")
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        Logger.i(TAG, "onEnabled ::")
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        Logger.i(TAG, "onDisabled ::")
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        Logger.i(TAG, "onDeleted ::")
        super.onDeleted(context, appWidgetIds)
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        Logger.i(TAG, "onRestored ::")
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }

    private fun rotateBitmap(context: Context, srcBmp: Bitmap, degree: Float): Bitmap {
        var matrix = Matrix()
        matrix.reset()
        matrix.setRotate(degree)
        var bitmap = Bitmap.createBitmap(srcBmp, 0, 0, srcBmp.width, srcBmp.height, matrix, true)
        return bitmap
    }
}

