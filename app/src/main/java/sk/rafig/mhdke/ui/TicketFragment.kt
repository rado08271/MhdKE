package sk.rafig.mhdke.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_ticket.*
import kotlinx.android.synthetic.main.fragment_ticket.view.*
import org.hotovo.mhdke.viewmodel.TicketViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.UserServiceFirebase
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.util.Barcoder
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.viewmodel.ActiveTicketViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory


class TicketFragment{

    companion object {
        fun createFragmentLight(view: AppCompatActivity, ticket: Ticket){
            view.id_ticket_fragment_received_ticket_message.text = ticket.columnBody
            view.id_ticket_fragment_received_ticket_number.text = ticket.id
            view.id_ticket_fragment_received_ticket_code.setImageBitmap(Barcoder.createBarcodeFromString(ticket.id))
        }
    }
}
