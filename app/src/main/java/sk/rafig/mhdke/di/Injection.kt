package sk.rafig.mhdke.di

import android.content.Context
import sk.rafig.mhdke.api.TicketDao
import sk.rafig.mhdke.api.TicketDatabase
import sk.rafig.mhdke.api.UserDao
import sk.rafig.mhdke.api.UsersDatabase

object Injection {
    fun proviceUserDataSource(context: Context): UserDao {
        val database = UsersDatabase.getInstance(context)
        return database.userDao()
    }

    fun proviceTicketDataSource(context: Context): TicketDao {
        val database = TicketDatabase.getInstance(context)
        return database.ticketDao()
    }

}