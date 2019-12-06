package sk.rafig.mhdke.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import sk.rafig.mhdke.R
import sk.rafig.mhdke.ui.SplashActivity


class NotificationCreate(private val context: Context, private val CHANNEL_ID: String) {

    fun createNotification(title: String, content: String, id: Int): Notification{
        val builder = baseBuilder(title, content)

        showNotification(builder, id)
        return builder.build()
    }

    fun countDownNotification(title: String, timeString: String, id: Int): Notification {
        val builder = baseBuilder(title, timeString)
            .setOnlyAlertOnce(true)

        showNotification(builder, id)
        return builder.build()

    }

    private fun baseBuilder(title: String, content: String): NotificationCompat.Builder {
        createChannel(title, content)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo_white)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(clickable())
//            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    }

    private fun createChannel(name: String, content: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = content
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun clickable(): PendingIntent{
        val intent = Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(context, 0, intent, 0)
    }

    private fun showNotification(builder: NotificationCompat.Builder, id: Int){
        with( NotificationManagerCompat.from(context)) {
            notify(id, builder.build())
        }
    }
}