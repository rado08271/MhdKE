package sk.rafig.mhdke.ui

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_ticket.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.util.Barcoder


class TicketFragment{

    companion object {
        fun createFragmentLight(view: AppCompatActivity, ticket: Ticket){

            view.id_ticket_fragment_type.text = view.applicationContext.getString(R.string.string_activity_preview_expired_ticket)
            view.id_ticket_fragment_price.text = ticket.price
            view.id_ticket_fragment_code.text = ticket.ticketCode

//            view.id_ticket_fragment_valid_till.text = ticket.validTill
//
//            view.id_ticket_fragment_valid_from.text = ticket.validFrom

            view.id_ticket_fragment_received_ticket_QR.setImageBitmap(Barcoder.createQrCodeFromString(ticket.id))
        }
    }
}
