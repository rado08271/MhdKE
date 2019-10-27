package sk.rafig.mhdke.api

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import sk.rafig.mhdke.model.Ticket

interface TicketDao {

    @Query("SELECT * FROM Tickets WHERE userId= :userId")
    fun getTicket(userId: String): Flowable<List<Ticket>>

    @Query("SELECT * FROM Tickets WHERE userId= :userId AND ticketId= :id")
    fun getTicket(userId: String, id: String): Single<Ticket>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addTicket(ticket: Ticket): Completable
}