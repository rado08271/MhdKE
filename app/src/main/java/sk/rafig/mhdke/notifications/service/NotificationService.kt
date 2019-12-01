package sk.rafig.mhdke.notifications.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import sk.rafig.mhdke.notifications.NotificationCreate
import sk.rafig.mhdke.util.TimeUtil
import java.util.*

class NotificationService(): Service(){

    private val CHANNEL_ID = "TiketNotificationChannel"
//    private val notification = NotificationCreate(applicationContext, CHANNEL_ID)

    companion object {
        fun startService(context: Context, message: String) {
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
        Timer().scheduleAtFixedRate( object: TimerTask(){
            @RequiresApi(Build.VERSION_CODES.N)
            override fun run() {
                val timeLeft = TimeUtil.getTime(application)
                if ( timeLeft.toInt() == 0) {
                    Log.d("WOW", "HERE")
                    startForeground(startId, notification.createNotification("RUN OUT", "NO TIME LEFT", startId))
                } else if (timeLeft.toInt() == 10) {
                    Log.d("WOW", "wwwwwwHERE")
                    startForeground(startId, notification.createNotification("Running Out", "You have 10 seconds left", startId))
                } else if (timeLeft.toInt() > 0) {
                    startForeground(666, notification.countDownNotification(TimeUtil.formatText(timeLeft), 666))
                }
            }
        }, 0, 1000)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}