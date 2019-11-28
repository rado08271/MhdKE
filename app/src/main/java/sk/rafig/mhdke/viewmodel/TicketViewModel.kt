package org.hotovo.mhdke.viewmodel

import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.icu.util.Calendar
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsManager
import android.text.method.TimeKeyListener
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.api.UserRepository
import sk.rafig.mhdke.api.UserServiceFirebase
import sk.rafig.mhdke.api.sms.SmsReciever
import sk.rafig.mhdke.di.Injection
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.util.ContextTags

class TicketViewModel(private val application: Application) : ViewModel() {


    private val phoneNumber = "0908266949"
    private val smsBody = "hi"
    private var userRepository: UserRepository = UserRepository(Injection.proviceUserDataSource(application.applicationContext))
    private lateinit var user: LiveData<User>
    private var string = "ERROR"
    private val isReceived = false

    fun getUser(): LiveData<User> {
        return userRepository.getPerson(Cache.getString(ContextTags.USER_ID, application))
    }

    fun getTicket(): LiveData<Ticket> {
        return UserServiceFirebase.getTicket(Cache.getString(ContextTags.USER_ID, application), Cache.getString(ContextTags.TICKET_ID, application))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addTicet(id: String, body: String){
        Cache.addValueToCache(ContextTags.TICKET_ID, id, application)
        Cache.addValueToCache(ContextTags.TICKET_RECEIVED, true, application)
        Cache.addValueToCache(ContextTags.TICKET_ENDS, (java.util.Calendar.getInstance().time.time + 3600).toInt(), application)

        return UserServiceFirebase.addTicket(Cache.getString(ContextTags.USER_ID, application),
            Ticket(id = id, boughtOn = Calendar.getInstance().time.time.toString()))

    }

    fun sendSms(){
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null)
    }

    fun receiveSms(): String {

        return string;
    }

    fun formatText(time: Long): String {
        if ( time <= 0) {
            return "00:00"
        }

        val minutes = time/60

        val seconds = time - minutes*60

        val sb = StringBuffer()

        sb.append(if (minutes < 10){ ("0"+minutes) } else {minutes})
        sb.append(":")
        sb.append(if (seconds < 10){ ("0"+seconds) } else {seconds})

        return sb.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTime(): Long {
//        return MutableLiveData<Long>(Cache.getInt(ContextTags.TICKET_ENDS, application) - Calendar.getInstance().time.time)
        return (Cache.getInt(ContextTags.TICKET_ENDS, application) - Calendar.getInstance().time.time)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun ticketWaiting(): LiveData<Boolean> {
        if (Cache.getBoolean(ContextTags.TICKET_RECEIVED, application) && getTime() > 0) {
            return MutableLiveData<Boolean>(true)
        } else {
            Cache.addValueToCache(ContextTags.TICKET_RECEIVED, false, application)
            Cache.addValueToCache(ContextTags.TICKET_ENDS, 0, application)

            return MutableLiveData<Boolean>(false)

        }
    }
}