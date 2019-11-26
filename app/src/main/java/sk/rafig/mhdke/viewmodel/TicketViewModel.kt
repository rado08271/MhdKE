package org.hotovo.mhdke.viewmodel

import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.provider.Telephony
import android.telephony.SmsManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.api.UserRepository
import sk.rafig.mhdke.api.sms.SmsReciever
import sk.rafig.mhdke.di.Injection
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
        return userRepository.getPerson(Cache.getInt(ContextTags.USER_ID, application))
    }

    fun getTicket(){

    }

    fun addTicet(){

    }

    fun sendSms(){
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null)
    }

    fun receiveSms(): String {

        return string;
    }

    fun formatText(time: Int): String {
        if ( time <= 0) {
            return "00:00"
        }

        val minutes = time/60 as Int

        val seconds = time - minutes*60

        val sb = StringBuffer()

        sb.append(if (minutes < 10){ ("0"+minutes) } else {minutes})
        sb.append(":")
        sb.append(if (seconds < 10){ ("0"+seconds) } else {seconds})

        return sb.toString()
    }
}