package sk.rafig.mhdke.api

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import sk.rafig.mhdke.model.Ticket

interface TicketService {
    fun getTicket(userId: String): List<Ticket>
    fun getTicket(userId: String, id: String): Ticket
    fun addTicket(userId: String, ticket: Ticket)
}