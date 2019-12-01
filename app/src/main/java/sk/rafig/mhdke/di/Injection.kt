package sk.rafig.mhdke.di

import android.content.Context
import sk.rafig.mhdke.api.local.TicketDao
import sk.rafig.mhdke.api.local.TicketDatabase
import sk.rafig.mhdke.api.local.UserDao
import sk.rafig.mhdke.api.local.UsersDatabase

object Injection {
    //instead use dagger!!!
    fun proviceUserDataSource(context: Context): UserDao {
        val database = UsersDatabase.getInstance(context)
        return database.userDao()
    }

    fun provideTicketDataSource(context: Context): TicketDao {
        val database = TicketDatabase.getInstance(context)
        return database.ticketDao()
    }

}