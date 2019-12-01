package sk.rafig.mhdke.util

import android.app.Application
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import sk.rafig.mhdke.api.local.Cache

object TimeUtil {
    @RequiresApi(Build.VERSION_CODES.N)
    fun getTime(application: Application): Long {
//        return MutableLiveData<Long>(Cache.getInt(ContextTags.TICKET_ENDS, application) - Calendar.getInstance().time.time)
        val until = Cache.getLong(ContextTags.TICKET_ENDS, application)
        val current = Calendar.getInstance().timeInMillis

//        return ((until - current) /1000)
        return ((until - current))/1000
    }

    fun formatText(time: Long): String {
        Log.d("TIME", "" + (time))
        if ( time <= 0) {
            return "00:00"
        }

        val minutes = time/60

        val seconds = time - minutes*60

        val sb = StringBuffer()

        sb.append(if (minutes < 10){ ("0$minutes") } else {minutes})
        sb.append(":")
        sb.append(if (seconds < 10){ ("0$seconds") } else {seconds})

        return sb.toString()
    }
}