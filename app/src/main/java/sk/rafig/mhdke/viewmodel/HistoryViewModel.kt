package sk.rafig.mhdke.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.api.UserServiceFirebase
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.util.ContextTags

class HistoryViewModel(private val application: Application) : ViewModel() {
    private val tickets = MutableLiveData<List<Ticket>>()

    fun getTickets(): LiveData<List<Ticket>>{
        return UserServiceFirebase.getAllTickets(Cache.getString(ContextTags.USER_ID, application))
    }

}