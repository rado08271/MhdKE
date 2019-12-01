package sk.rafig.mhdke.api.local

import androidx.lifecycle.LiveData
import sk.rafig.mhdke.model.Ticket

class TicketRepository(private val ticketDao: TicketDao) {

    fun getTicket(userId: String, id: String): LiveData<Ticket> {
        return ticketDao.getTicket(userId, id)
    }

    fun addTicket(ticket: Ticket) {
        ticketDao.addTicket(ticket)
    }

}