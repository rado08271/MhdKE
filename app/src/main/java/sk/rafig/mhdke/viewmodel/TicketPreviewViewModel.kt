package org.hotovo.mhdke.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sk.rafig.mhdke.api.UserServiceFirebase
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.util.ContextTags

class TicketPreviewViewModel(private val application: Application) : ViewModel() {
    fun getTicket(ticketId: String): LiveData<Ticket> {
        return UserServiceFirebase.getTicket(
            Cache.getString(ContextTags.USER_ID, application),
            ticketId)
    }
}