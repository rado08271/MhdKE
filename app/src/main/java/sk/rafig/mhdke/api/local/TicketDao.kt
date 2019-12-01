package sk.rafig.mhdke.api.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import sk.rafig.mhdke.model.Ticket

@Dao
interface TicketDao {
    @Query("SELECT * FROM ticket WHERE userId = :userId AND ticketId = :id")
    fun getTicket(userId: String, id: String): LiveData<Ticket>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addTicket(ticket: Ticket)
}