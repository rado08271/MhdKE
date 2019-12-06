package sk.rafig.mhdke.notifications.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import sk.rafig.mhdke.R
import sk.rafig.mhdke.notifications.NotificationCreate
import sk.rafig.mhdke.util.TimeUtil
import java.util.*

class NotificationService : Service() {

    private val CHANNEL_ID = "TiketNotificationChannel"
    private val CHANNEL_ID_QUICK_UPDATES = "TiketNotificationChannel"
//    private val notification = NotificationCreate(applicationContext, CHANNEL_ID)

    companion object {
        fun startService(context: Context) {
            val startIntent = Intent(context, NotificationService::class.java)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, NotificationService::class.java)
            context.stopService(stopIntent)
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCreate(applicationContext, CHANNEL_ID)

//        startForeground(startId, NotificationCreate(this, CHANNEL_ID).createNotification("blaaah", "fadsffs", startId))
        Timer().scheduleAtFixedRate(object : TimerTask() {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun run() {
                val timeLeft = TimeUtil.getTime(application)
                when {
                    timeLeft.toInt() == 0 -> {
                        startForeground(
                            startId,
                            notification.createNotification(
                                applicationContext.getString(R.string.string_notification_expired_titlte),
                                applicationContext.getString(R.string.string_notification_expired_text),
                                startId)
                        )
                    }

                    timeLeft.toInt() == 300 -> {
                        //CONSIDER or 10% till finish?
                        notification.createNotification(
                            applicationContext.getString(R.string.string_notification_to_expire_title),
                            applicationContext.getString(R.string.string_notification_to_expire_text),
                            startId)
                    }

                    timeLeft.toInt() > 0 -> {
                        startForeground(
                            666,
                            notification.countDownNotification(
                                applicationContext.getString(R.string.string_notification_to_expire_title),
                                TimeUtil.formatText(timeLeft), 666)
                        )
                    }
                }
            }
        }, 0, 1000)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}