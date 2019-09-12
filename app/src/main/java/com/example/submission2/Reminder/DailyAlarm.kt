package com.example.submission2.Reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.example.submission2.R
import java.util.*


class DailyAlarm : BroadcastReceiver() {

    companion object {

        private val NOTIF_ID_REPEATING = 101

        private fun getPendingIntent(context: Context): PendingIntent {

            val intent = Intent(context, DailyAlarm::class.java)
            return PendingIntent.getBroadcast(
                context,
                NOTIF_ID_REPEATING,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val message = "kembali segera ke APP movie"
        val title = "Dicoding Movie App"
        showAlarmNotification(context, title, message, NOTIF_ID_REPEATING)
    }

    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int
    ) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "Job scheduler channel"
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)
    }

    fun setRepeatingAlarm(context: Context) {
        cancelAlarm(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 14)
        Log.d("calendar=", calendar.toString())
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            getPendingIntent(context)
        )
        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyAlarm::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 101, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        alarmManager.cancel(pendingIntent)
    }



}
