package sk.rafig.mhdke.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_row.view.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.ui.TicketPreviewActivity
import sk.rafig.mhdke.util.ContextTags

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
        holder.history_row_ticket_id.text = tickets[position].id
        holder.history_row_time.text = tickets[position].boughtOn

        holder.view.setOnClickListener{
            val intent = Intent(context, TicketPreviewActivity::class.java)

            intent.putExtra(ContextTags.HISTORY_TICKET_ID, tickets[position].id)

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}

class HistoryViewHolder(val view: View): RecyclerView.ViewHolder(view){
    val history_row_ticket_id = view.history_row_ticket_id
    val history_row_time = view.history_row_time
}