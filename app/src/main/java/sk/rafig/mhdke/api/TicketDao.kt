package sk.rafig.mhdke.api

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import sk.rafig.mhdke.model.Ticket

interface TicketDao {
    fun getTicket(userId: String): Flowable<List<Ticket>>
    fun getTicket(userId: String, id: String): Single<Ticket>
    fun addTicket(ticket: Ticket): Completable
}