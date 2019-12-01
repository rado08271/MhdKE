package org.hotovo.mhdke.viewmodel

import android.app.Application
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.telephony.SmsManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.api.UserServiceFirebase
import sk.rafig.mhdke.api.local.TicketRepository
import sk.rafig.mhdke.api.local.UserRepository
import sk.rafig.mhdke.di.Injection
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.ui.ActiveTicketActivity
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.util.SmsSpecs
import sk.rafig.mhdke.util.TimeUtil

class TicketViewModel(private val application: Application) : ViewModel() {


    private val phoneNumber = "0908266949"
    private val smsBody = "hi"
    private var ticketRepository: TicketRepository =
        TicketRepository(Injection.provideTicketDataSource(application.applicationContext))
    private var userRepository: UserRepository =
        UserRepository(Injection.proviceUserDataSource(application.applicationContext))
    private lateinit var ticket: LiveData<Ticket>

    @RequiresApi(Build.VERSION_CODES.N)
    fun addTicet(id: String, body: String){
        viewModelScope.launch {
            Cache.addValueToCache(ContextTags.TICKET_ID, id, application)
            Cache.addValueToCache(ContextTags.TICKET_RECEIVED, true, application)
            val well = (System.currentTimeMillis() + SmsSpecs.length * 1000)
            Cache.addValueToCache(ContextTags.TICKET_ENDS, well, application)

            val ticket = Ticket( columnBody = body, id = id,
                boughtOn = Calendar.getInstance().time.time.toString(), userId = Cache.getString(ContextTags.USER_ID, application))

//            ticketRepository.addTicket(ticket)
            UserServiceFirebase.addTicket(Cache.getString(ContextTags.USER_ID, application), ticket)
            application.startActivity(Intent(application.applicationContext,
                ActiveTicketActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    fun sendSms(){
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun ticketWaiting(): LiveData<Boolean> {
        if (Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)
            && (TimeUtil.getTime(application)) > 0 ) {
            return MutableLiveData<Boolean>(true)
        } else {
            Cache.addValueToCache(ContextTags.TICKET_RECEIVED, false, application)
            Cache.addValueToCache(ContextTags.TICKET_ENDS, 0L, application)

            return MutableLiveData<Boolean>(false)

        }
    }
}