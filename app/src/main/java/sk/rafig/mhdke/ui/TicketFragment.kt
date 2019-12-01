package sk.rafig.mhdke.ui

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_ticket.*
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.util.Barcoder


class TicketFragment{

    companion object {
        fun createFragmentLight(view: AppCompatActivity, ticket: Ticket){
            view.id_ticket_fragment_received_ticket_message.text = ticket.columnBody
            view.id_ticket_fragment_received_ticket_number.text = ticket.id
            view.id_ticket_fragment_received_ticket_code.setImageBitmap(Barcoder.createBarcodeFromString(ticket.id))
        }
    }
}
