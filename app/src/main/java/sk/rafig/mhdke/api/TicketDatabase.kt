package sk.rafig.mhdke.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.model.User

@Database(entities = arrayOf(Ticket::class), version = 1)
abstract class TicketDatabase: RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        private var INSTANCE: TicketDatabase? = null

        fun getInstance(context: Context): TicketDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?:  buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, TicketDatabase::class.java, "Tickets.db"
        ).build()
    }
}