package sk.rafig.mhdke.api

import android.app.Activity
import android.app.Application
import android.content.Context

object Cache {
    private val TIKET_VALUE = "TICKET_VALUE"
    fun addValueToCache(id: String, value: Boolean, application: Application): Boolean{
        val sp = application.getSharedPreferences(TIKET_VALUE, Context.MODE_PRIVATE) ?: return false

        with(sp.edit()) {
            putBoolean(id, value)
            apply()
        }

        return true
    }

    fun addValueToCache(id: String, value: Int, application: Application): Boolean{
        val sp = application.getSharedPreferences(TIKET_VALUE, Context.MODE_PRIVATE) ?: return false

        with(sp.edit()) {
            putInt(id, value)
            apply()
        }

        return true
    }

    fun getBoolean(id: String, application: Application): Boolean {
        return application.getSharedPreferences(TIKET_VALUE, Context.MODE_PRIVATE).getBoolean(id, false)
    }

    fun getInt(id: String, application: Application): Int {
        return application.getSharedPreferences(TIKET_VALUE, Context.MODE_PRIVATE).getInt(id, -1)
    }
}