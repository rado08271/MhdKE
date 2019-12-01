package sk.rafig.mhdke.viewmodel

import android.app.Application
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.api.local.UserRepository
import sk.rafig.mhdke.api.UserServiceFirebase
import sk.rafig.mhdke.di.Injection
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.ui.ActiveTicketActivity
import sk.rafig.mhdke.ui.TicketActivity
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.util.SmsSpecs
import sk.rafig.mhdke.util.TimeUtil

class ActiveTicketViewModel(private val application: Application): ViewModel(){
    private var userRepository: UserRepository =
        UserRepository(Injection.proviceUserDataSource(application.applicationContext))

    fun getUser(): LiveData<User> {
        return userRepository.getPerson(Cache.getString(ContextTags.USER_ID, application))
    }

    fun getTicket(): LiveData<Ticket> {
        return UserServiceFirebase.getTicket(
            Cache.getString(ContextTags.USER_ID, application),
            Cache.getString(ContextTags.TICKET_ID, application))
    }

    fun ticketExpired() {
        viewModelScope.launch {
            Cache.addValueToCache(ContextTags.TICKET_ID, "EXPIRED", application)
            Cache.addValueToCache(ContextTags.TICKET_RECEIVED, false, application)

            application.startActivity(
                Intent(application.applicationContext,
                    TicketActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTime(): LiveData<Long>{
//        return MutableLiveData<Long>(TimeUtil.getTime(application))
        return MutableLiveData<Long>(TimeUtil.getTime(application))
    }
}