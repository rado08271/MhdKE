package sk.rafig.mhdke.viewmodel

import android.app.Application
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Handler
import android.telephony.SmsManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
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
import sk.rafig.mhdke.util.TicketParser
import sk.rafig.mhdke.util.TimeUtil
import kotlin.coroutines.CoroutineContext

class TicketViewModel(private val application: Application) : ViewModel() {
    private var ticketRepository: TicketRepository =
        TicketRepository(Injection.provideTicketDataSource(application.applicationContext))
    private var userRepository: UserRepository =
        UserRepository(Injection.proviceUserDataSource(application.applicationContext))
    private var boughtTime = -1L

    @RequiresApi(Build.VERSION_CODES.N)
    fun addTicet(body: String){
        viewModelScope.launch {
            val newBody = "DPMK, a.s.\nSMS prestupny CL 0.80 EUR\nPlatnost od 12-12-2018 15:57 do 16:57 hod.\n87xzhygz"
            val ticket = TicketParser.parseTicket(newBody, Cache.getString(ContextTags.USER_ID, application), boughtTime)

            Cache.addValueToCache(ContextTags.TICKET_ID, ticket.id, application)
            Cache.addValueToCache(ContextTags.TICKET_RECEIVED, true, application)

            val validUntill = (System.currentTimeMillis() + SmsSpecs.length * 1000)
            Cache.addValueToCache(ContextTags.TICKET_ENDS, validUntill, application)

//            ticketRepository.addTicket(ticket)
            UserServiceFirebase.addTicket(Cache.getString(ContextTags.USER_ID, application), ticket)

            Handler().postDelayed({
                application.startActivity(Intent(application.applicationContext,
                    ActiveTicketActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }, 750)
         }
    }

    fun sendSms(){
        boughtTime = System.currentTimeMillis()
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(SmsSpecs.serviceProviderNumber, null, SmsSpecs.serviceProviderSmsCondition, null, null)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun ticketWaiting(): LiveData<Boolean> {
        return if (Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)
            && (TimeUtil.getTime(application)) > 0 ) {
            MutableLiveData<Boolean>(true)
        } else {
            Cache.addValueToCache(ContextTags.TICKET_RECEIVED, false, application)
            Cache.addValueToCache(ContextTags.TICKET_ENDS, 0L, application)

            MutableLiveData<Boolean>(false)

        }
    }
}