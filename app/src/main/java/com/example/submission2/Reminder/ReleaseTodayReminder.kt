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
import com.example.submission2.movie.ResultsItemMovie
import java.util.*


class ReleaseTodayReminder : BroadcastReceiver() {

    companion object {

        private var notifId: Int = 0
        private fun getPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, DailyAlarm::class.java)
            return PendingIntent.getBroadcast(
                context,
                101,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
    }

    override fun onReceive(context: Context, intent: Intent) {

        notifId = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("movieTitle")

        showAlarmNotification(context, title, notifId)
    }

    private fun showAlarmNotification(context: Context, title: String, notifId: Int) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "Job scheduler channel"
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        Log.d("alarm", "showAlarmNotification: $notifId")

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText("Today $title release")
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


    fun setRepeatingAlarm(context: Context, movieResults: List<ResultsItemMovie>) {

        Log.d("alarm", "setRepeatingAlarm: " + movieResults.size)

        var notifDelay = 0

        for (i in movieResults.indices) {
            cancelAlarm(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, ReleaseTodayReminder::class.java)
            intent.putExtra("movieTitle", movieResults[i].title)
            intent.putExtra("id", notifId)

            val pendingIntent =
                PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 45)

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis + notifDelay,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

            notifId++
            notifDelay += 1000
        }

        Toast.makeText(context, "Release reminder set up", Toast.LENGTH_SHORT).show()

    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context))
    }


}
