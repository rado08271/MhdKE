package org.hotovo.mhdke.viewmodel

import android.app.PendingIntent
import android.telephony.SmsManager
import androidx.lifecycle.ViewModel

class TicketViewModel : ViewModel() {

    private val phoneNumber = "0908266949"
    private val smsBody = "hi"

    fun sendSms(){
//        val scAddress: String ?= null
//        val sentIntent: PendingIntent ?= null
//        val deliveryIntent: PendingIntent ?= null

        val smsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null)
    }
}