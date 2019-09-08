package com.example.submission2.Widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.example.submission2.R

class ImageBannerWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //Kode di bawah akan dijalankan ketika widget ditekan. Seperti pada latihan sebelumnya,
    // percabangan digunakan untuk membedakan action yang terjadi.
    // Kita dapat mengambil data action tersebut dengan memanfaatkan extra dari sebuah intent.
    override fun onReceive(context: Context, intent: Intent) {
        val mgr = AppWidgetManager.getInstance(context)
        if (intent.action == TOAST_ACTION) {
            val appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
            val viewTitle = intent.getStringExtra(EXTRA_ITEM)
            Toast.makeText(context, viewTitle, Toast.LENGTH_SHORT).show()
        }

        if (intent.action == UPDATE_WIDGET) {
            val gm = AppWidgetManager.getInstance(context)
            val ids = gm.getAppWidgetIds(ComponentName(context, ImageBannerWidget::class.java))

            gm.notifyAppWidgetViewDataChanged(ids, R.id.stack_view)
        }

        super.onReceive(context, intent)
    }

    companion object {

        val TOAST_ACTION = "com.example.submission2.TOAST_ACTION"
        val EXTRA_ITEM = "com.example.submission2.EXTRA_ITEM"
        val UPDATE_WIDGET = "com.example.submission2.UPDATE_WIDGET"

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.favorite_app_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val toastIntent = Intent(context, ImageBannerWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

}


