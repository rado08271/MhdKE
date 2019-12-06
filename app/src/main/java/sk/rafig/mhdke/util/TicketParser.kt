package sk.rafig.mhdke.util

import sk.rafig.mhdke.model.Ticket
import java.util.*

object TicketParser {
    fun parseTicket(smsBody: String, userId: String, boughtTime: Long): Ticket {
//        val id = /////TODOO
        if (smsBody.length != 88) {
            return Ticket(
                boughtOn = boughtTime.toString(),
                columnBody = smsBody,
                price = "--------",
                ticketCode = "ERROR",       //get strig from resources
                userId = userId,
                validFrom = Calendar.getInstance().time.time,
                validTill = (Calendar.getInstance().time.time + SmsSpecs.length * 1000)
            )
        }


        val ticket = Ticket(
            boughtOn = boughtTime.toString(),
            columnBody = smsBody,
            price = smsBody.substring(28,36),
            ticketCode = smsBody.substring(80, 88),
            userId = userId,
            validFrom = Calendar.getInstance().time.time,
            validTill = (Calendar.getInstance().time.time + SmsSpecs.length * 1000)
        )


        return ticket
    }
}