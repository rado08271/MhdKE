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
    private val receiver = SmsReciever(phoneNumber, smsBody)

    init {
        application.registerReceiver(receiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
    }

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
        if (Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)) {

            receiver.setListener {
                if (it.equals(smsBody)) {
                    Log.d("WOW", "HERE I AM")
                    string = it
                    Cache.addValueToCache(ContextTags.TICKET_RECEIVED, true, application)
                }
            }
            return string
        }

        return string;
    }
}