package sk.rafig.mhdke.ui.adapter

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.history_row.view.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.ui.TicketPreviewActivity
import sk.rafig.mhdke.util.Animator
import sk.rafig.mhdke.util.ContextTags
import java.sql.Timestamp
import java.util.*

class HistoryRecyclerView(private val context: Context, private val tickets:List<Ticket>):
    RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.history_row, parent, false))
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.history_row_ticket_id.text = tickets[position].ticketCode

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.history_row_time.text = SimpleDateFormat("dd.MM.yyyy - HH:mm:ss").format(Date(tickets[position].boughtOn.toLong()))
        } else {
            holder.history_row_time.text = Timestamp(tickets[position].boughtOn.toLong()).time.toString()
        }

        holder.view.setOnClickListener{
            val intent = Intent(context, TicketPreviewActivity::class.java)

            intent.putExtra(ContextTags.HISTORY_TICKET_ID, tickets[position].id)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

}

class HistoryViewHolder(val view: View): RecyclerView.ViewHolder(view){
    val history_row_ticket_id = view.history_row_ticket_id
    val history_row_time = view.history_row_time
    val history_row_ticket_icon = view.history_row_ticket_icon
}